/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  AbstractProducePlugin.java   
 * @Package com.loris.soccer.data.zgzcw.plugin   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.impl.HttpCommonFetcher;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.model.WebPage;
import com.loris.client.service.WebPageService;
import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.BasicWebPageTaskPlugin;
import com.loris.client.task.plugin.TaskProcessPlugin;
import com.loris.client.task.plugin.TaskProducePlugin;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.filter.Filter;
import com.loris.common.model.TableRecords;
import com.loris.common.util.ArraysUtil;
import com.loris.common.util.DateUtil;
import com.loris.common.util.KeyMap;
import com.loris.common.util.ToolUtil;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.conf.WebPageProperties;
import com.loris.soccer.data.filter.WebPageFilter;
import com.loris.soccer.data.filter.object.LatestMatchFilter;
import com.loris.soccer.data.filter.page.ZgzcwPageFilter;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.ZgzcwPageCreator;
import com.loris.soccer.data.zgzcw.ZgzcwPageParser;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.base.MatchItem;
import com.loris.soccer.model.view.RoundInfo;
import com.loris.soccer.model.view.SeasonInfo;
import com.loris.soccer.service.LeagueService;
import com.loris.soccer.service.MatchService;
import com.loris.soccer.service.impl.SoccerDataService;

/**
 * @ClassName: AbstractProducePlugin
 * @Description: 下载数据创建任务插件
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public abstract class ZgzcwBasePlugin extends BasicWebPageTaskPlugin implements TaskProducePlugin, TaskProcessPlugin
{
	private static Logger logger = Logger.getLogger(ZgzcwBasePlugin.class);

	/** 数据下载器 */
	protected WebFetcher webPagefetcher;
	
	@Autowired
	protected SoccerDataService soccerDataService;
	
	@Autowired
	protected LeagueService leagueService;
	
	@Autowired
	protected WebPageService pageService;
	
	@Autowired
	protected MatchService matchService;
	
	/** 网络页面的过滤器 */
	protected WebPageFilter webPageFilter = null;
	
	/** 是否更新联赛中心页面 */
	protected boolean updateLeagueCurrentRounds = false;
	
	/** 注册的过滤器 */
	private Map<String, Filter<?>> filters = new HashMap<>();
	
	/** 页面的配置项 */
	protected WebPageProperties webPageConf = null;
	
	/**
	 * Create a new instance of ZgzcwBasePlugin.
	 */
	public ZgzcwBasePlugin(String name, WebPageProperties webPageConf)
	{
		super(name);
		this.webPageConf = webPageConf;
	}
	
	/**
	 * 初始化任务产生器
	 * 
	 * @param context 插件任务运行环境
	 * @throws IOException 在任务产生过程中出现异常
	 */
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		if(initialized)
		{
			return;
		}
		super.initialize(context);
		
		FetcherSetting setting = ApplicationContextHelper.getBean("defaultSetting");
		webPagefetcher = new HttpCommonFetcher(setting);
		try
		{
			webPagefetcher.init();
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Error occured when HttpCommonFetcher init().");
		}
		
		//初始化过滤器
		initFilter(context);		
		initialized = true;
	}
	
	/**
	 * 初始化过滤器 
	 */
	protected void initFilter(TaskPluginContext context)
	{
		if(webPageFilter == null)
		{
			webPageFilter = this.createDefaultWebPageFilter();
		}
		
		if(webPageFilter != null && !webPageFilter.isInitialized())
		{
			webPageFilter.initialize();
		}
		
		registSourceFilter(SoccerConstants.SOCCER_DATA_MATCH, 
				new LatestMatchFilter(webPageConf.getNumDayOfHasOdds(), webPageConf.getDayNumOfGetPages()));
	}
	
	/**
	 * 创建默认的页面过滤器
	 * @return
	 */
	protected WebPageFilter createDefaultWebPageFilter()
	{
		ZgzcwPageFilter filter = new ZgzcwPageFilter(webPageConf);
		filter.setSource(ZgzcwConstants.SOURCE_ZGZCW);
		filter.setStart(DateUtil.addDayNum(new Date(), - webPageConf.getDayNumOfGetPages()));
		registerProcessPageTypes(filter);
		return filter;
	}

	/**
	 * 产生任务程序
	 * @param context 插件运行环境
	 */
	@Override
	public abstract void produce(TaskPluginContext context) throws IOException, SQLException;
	
	/**
	 * 注册处理的页面类型
	 * @param types
	 */
	protected void registerProcessPageTypes(WebPageFilter webPageFilter)
	{
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#isFit(com.loris.client.task.Task)
	 */
	@Override
	public boolean isFit(Task task)
	{
		if(!(task instanceof WebPage))
		{
			return false;
		}
		WebPage page = (WebPage) task;
		if(!ZgzcwConstants.SOURCE_ZGZCW.equals(page.getSource()))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 后处理数据的插件
	 * 
	 * @param context 插件运行环境
	 * @param task 任务
	 * @return 是否执行成功的标志
	 */
	@Override
	public boolean execute(TaskPluginContext context, Task task) throws UrlFetchException, 
		WebParserException, IOException, HostForbiddenException, SQLException
	{
		if(!(task instanceof WebPage))
		{
			return false;
		}	
		return executeWebPageTask(context, (WebPage) task);
	}
	
	/**
	 * 从远程获取数据内容，并对数据进行解析
	 * @param context 插件运行环境
	 * @param page 网络页面
	 * @return 是否成功的标志
	 * 
	 * @throws IOException
	 * @throws WebParserException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 */
	protected boolean executeWebPageTask(TaskPluginContext context, WebPage page) 
			throws IOException, WebParserException, HostForbiddenException, UrlFetchException
	{
		if (StringUtils.isBlank(page.getContent()) && !download(page))
		{
			logger.warn("Error when HttpCommonExecutor execute: " + page.getUrl());
			return false;
		}
		
		//解析数据结果
		try
		{
			if(StringUtils.isBlank(page.getContent()))
			{
				logger.warn("The page has no content downloaded: " + page.getUrl());
			}
			else
			{
				TableRecords records = ZgzcwPageParser.parseWebPage(page);
				if(records != null)
				{		
					saveTableRecords(records);		
					if(webPageConf.isPageProduceNewTask(page.getType()))
					{
						produceTask(page.getType(), records);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.warn("Error occured when parsing and postExcute the WebPage: " + page.getUrl());
		}
		return true;
	}
	
	/**
	 * 存储数据
	 * @param records
	 * @throws Exception
	 */
	public void saveTableRecords(TableRecords records) throws IOException
	{
		try
		{
			long st = System.currentTimeMillis();
			soccerDataService.saveTableRecords(records);
			long en = System.currentTimeMillis();
			logger.info("Save TableRecords " + records.toString() + " spend time is " + (en - st) + " ms.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.warn("Error '" + e.toString() + "' occured when save records[" + records.toString() + "].");
		}
	}
	
	/**
	 * 数据下载，在下载之后将进行数据的存储
	 * @param page 网页页面
	 * @return 是否下载成功的标志
	 * @throws IOException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 */
	protected boolean download(WebPage page) throws IOException, HostForbiddenException, UrlFetchException
	{
		logger.info("Starting get the data from : " + page.getUrl() 
			+ (StringUtils.isEmpty(page.getParamstext()) ? "" : ", params:" + page.getParamstext()) );
		if(webPagefetcher.download(page))
		{
			pageService.save(page);
			return true;
		}
		return false;
	}
	
	/**
	 * 处理创建的页面
	 * @param page 网络下载页面
	 * @param quiet 是否通知处理器
	 * @return 返回创建的标志
	 */
	protected<T> boolean createWebPageTask(WebPage page, T source, boolean quiet)
	{
		if(webPageFilter != null && !webPageFilter.accept(page, source))
		{
			return false;
		}
		return super.createWebPageTask(page, quiet);
	}
	
	/**
	 * 创建联赛数据下载页面
	 * @param league 联赛数据下载页面
	 */
	protected boolean createLeagueCenterTask(League league, String season, boolean quiet)
	{
		if(webPageConf.isPageBeCreated(league.getType()))
		{
			Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_LID, league.getLid());
			if(StringUtils.isNotEmpty(season))
			{
				params.put(SoccerConstants.NAME_FIELD_SEASON, season);
			}
			return createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(league.getType(), params), league, quiet);
		}
		return false;
	}
	
	/**
	 * 创建联赛数据下载页面
	 * @param leagues 联赛列表
	 * @param filter 数据过滤器
	 * @return 是否创建成功
	 */
	protected boolean createLeagueCenterTasks(List<League> leagues, Filter<League> filter)
	{
		if(leagues == null || leagues.size() == 0)
		{
			logger.info("There are no League object in the league item list.");
			return false;
		}
		
		int size = 0;
		for (League league : leagues)
		{
			if(filter == null || filter.accept(league))
			{
				createLeagueCenterTask(league, null, false);
				size ++;
			}
		}
		return size > 0;
	}
	
	/**
	 * 创建联赛下载页面
	 * @param leagueNames
	 * @param filter
	 * @return
	 */
	protected boolean createLeagueCenterTasksFromNames(List<String> leagueNames, Filter<League> filter)
	{
		if(leagueNames != null && leagueNames.size() > 0)
		{
			LeagueList allLeagues = new LeagueList(leagueService.list());
			LeagueList leagues = new LeagueList();
			
			for (String leagueValue : leagueNames)
			{
				League l = allLeagues.getLeague(leagueValue);
				if(l != null) leagues.add(l);
			}
			
			return createLeagueCenterTasks(leagues, filter);
		}
		return false;
	}
	
	/**
	 * 创建联赛下载页面
	 * @param leagues
	 * @param filter
	 * @return
	 */
	protected boolean createLeagueCenterTasksFromMatchs(List<Match> matchList, Filter<League> filter)
	{
		if(matchList != null && matchList.size() > 0)
		{
			List<String> leagues = ArraysUtil.getObjectFieldValue(matchList, Match.class, SoccerConstants.NAME_FIELD_LID);
			return createLeagueCenterTasksFromNames(leagues, null);
		}
		return false;
	}
	
	/**
	 * 获得比赛数据的类型页面
	 * @return 比赛数据
	 */
	protected List<String> getMatchDataTypes()
	{
		List<String> types = new ArrayList<>();
		
		for (String key : webPageConf.getPageBeCreated().keySet())
		{
			if(webPageConf.isPageBeCreated(key))
			{
				types.add(key);
			}
		}
		
		if (webPageConf.isPageBeCreated(ZgzcwConstants.PAGE_ODDS_OP))
		{
			types.add(ZgzcwConstants.PAGE_ODDS_OP);
		}
		if (webPageConf.isPageBeCreated(ZgzcwConstants.PAGE_ODDS_YP))
		{
			types.add(ZgzcwConstants.PAGE_ODDS_YP);
		}
		if (webPageConf.isPageBeCreated(ZgzcwConstants.PAGE_ODDS_NUM))
		{
			types.add(ZgzcwConstants.PAGE_ODDS_NUM);
		}

		return types;
	}

	/**
	 * 建立比赛的数据下载任务
	 * @param match 比赛数据
	 * @param hasOp 是否下载欧赔数据
	 * @param hasYp 是否下载亚盘数据
	 * @param hasNum 是否下载大小球数据
	 */
	protected int createMatchDataTask(List<String> types, MatchItem match, boolean quiet)
	{
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_MID, match.getMid());		
		params.put(SoccerConstants.NAME_FIELD_MATCHTIME, DateUtil.formatDateTime(match.getMatchtime()));
		
		int size = 0;
		for (String type : types)
		{
			if(createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(type, params), match, quiet))
				size ++;
		}
		return size;
	}
	
	/**
	 * 创建联赛赛季数据下载页面
	 * @param seasonInfo 赛季信息
	 * @param quiet 是否不发送任务创建通知
	 */
	protected boolean createLeagueSeasonCenterTask(SeasonInfo seasonInfo, boolean quiet)
	{
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_LID, seasonInfo.getLid());
		params.put(SoccerConstants.NAME_FIELD_SEASON, seasonInfo.getSeason());
		return createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(seasonInfo.getLeaguetype(), params), seasonInfo, quiet);		
	}
	
	/**
	 * 创建赛事轮次数据下载页面
	 * @param roundInfo
	 * @param quiet
	 */
	protected boolean createLeagueRoundTask(RoundInfo roundInfo, boolean quiet)
	{
		//如果是非联赛，则不能创建下载页面
		if(!StringUtils.equals(roundInfo.getLeaguetype(), SoccerConstants.LEAGUE_TYPE_LEAGUE))
			return false;
		
		Map<String, String> params = new KeyMap(ZgzcwConstants.NAME_FIELD_SOURCE_LID, roundInfo.getLid());
		params.put(ZgzcwConstants.NAME_FIELD_SEASON, roundInfo.getSeason());
		params.put(ZgzcwConstants.NAME_FIELD_CUR_ROUND, roundInfo.getRound());
		return createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LEAGUE_LEAGUE_ROUND, params), roundInfo, quiet);		
	}

	/**
	 * 创建比赛数据下载任务
	 * @param matchItems 数据列表
	 * @param filter 过滤器
	 * @return 是否创建的数据量大小0
	 */
	protected <T extends MatchItem> boolean createMatchTasks(List<T> matchItems, Filter<MatchItem> filter)
	{
		if(matchItems == null || matchItems.size() == 0)
		{
			logger.warn("There are no MatchItems in the match item list.");
			return false;
		}
		
		logger.info("There are " + matchItems.size() + " matches in the list.");
		int size = 0;
		
		List<String> types = getMatchDataTypes();
		if(types.size() == 0)
		{
			logger.info("There are no match data type defined in the WebPageProperties.");
			return false;
		}
		
		for (T matchItem : matchItems)
		{
			if (filter == null || filter.accept(matchItem))
			{
				if(ToolUtil.isEmpty(matchItem.getMatchtime()))
				{
					logger.info("Match has no matchtime property: " + matchItem);
					continue;
				}
				createMatchDataTask(types, matchItem, false);
				size ++;
			}
		}
		return size > 0;
	}

	/**
	 * 从数据网页初始化下载数据任务
	 * @param context 插件运行环境
	 * @param page 网络页数据
	 * @return 是否创建成成功的标志
	 * @throws IOException
	 * @throws WebParserException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 */
	public<T> boolean createTaskFromWebPage(TaskPluginContext context, WebPage page)
			throws IOException, WebParserException, HostForbiddenException, UrlFetchException
	{
		webPageConf.setPageProduceNewTask(page.getType(), true);
		
		//解析数据结果
		return executeWebPageTask(context, page);
	}
	
	/**
	 * 产生下载的任务
	 * @param pageType 页面类型
	 * @param records 数据记录
	 */
	protected void produceTask(String pageType, TableRecords records)
	{
		// 通过不同的页面进行数据处理
		switch (pageType)
		{
		case ZgzcwConstants.PAGE_LOTTERY_BD:
		{
			if (updateLeagueCurrentRounds)
			{
				MatchList matchList = (MatchList) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
				createLeagueCenterTasksFromMatchs(matchList, null);
			}
			Filter<MatchItem> filter = getSourceFilter(SoccerConstants.SOCCER_DATA_MATCH);
			createMatchTasks((MatchItemList) records.get(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST), filter);
			break;
		}
		case ZgzcwConstants.PAGE_LOTTERY_JC:
		{
			if (updateLeagueCurrentRounds)
			{
				MatchList matchList = (MatchList) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
				createLeagueCenterTasksFromMatchs(matchList, null);
			}
			
			Filter<MatchItem> filter = getSourceFilter(SoccerConstants.SOCCER_DATA_MATCH);
			createMatchTasks((MatchItemList) records.get(SoccerConstants.SOCCER_DATA_MATCH_JC_LIST), filter);
			break;
		}
		case ZgzcwConstants.PAGE_CENTER:
		{
			Filter<League> filter = getSourceFilter(SoccerConstants.SOCCER_DATA_LEAGUE);
			createLeagueCenterTasks((LeagueList) records.get(SoccerConstants.SOCCER_DATA_LEAGUE_LIST), filter);
			break;
		}
		case ZgzcwConstants.PAGE_LEAGUE_LEAGUE:
		case ZgzcwConstants.PAGE_LEAGUE_CUP:
		{
			MatchList matchList = (MatchList) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
			Filter<MatchItem> filter = getSourceFilter(SoccerConstants.SOCCER_DATA_MATCH);
			createMatchTasks(matchList, filter);
			break;
		}
		default:
			return;
		}
	}

	/**
	 * 更新联赛当前比赛轮次的比赛数据
	 * @param updateLeagueCurrentRounds
	 */
	public void setUpdateLeagueCurrentRounds(boolean updateLeagueCurrentRounds)
	{
		this.updateLeagueCurrentRounds = updateLeagueCurrentRounds;
	}

	/**
	 * 返回WebPage的过滤器设置
	 * @param webPageFilter
	 */
	public void setWebPageFilter(WebPageFilter webPageFilter)
	{
		this.webPageFilter = webPageFilter;
	}
	
	/**
	 * 注册过滤器
	 * @param type 过滤器类型
	 * @param filter 过滤器
	 */
	public void registSourceFilter(String type, Filter<?> filter)
	{
		filters.put(type, filter);
	}
	
	/**
	 * 获得过滤器
	 * @param type 类型
	 * @return 返回过滤器
	 */
	@SuppressWarnings("unchecked")
	public<T> Filter<T> getSourceFilter(String type)
	{
		return (Filter<T>)filters.get(type);
	}

	public WebPageProperties getWebPageConf()
	{
		return webPageConf;
	}

	public void setWebPageConf(WebPageProperties webPageConf)
	{
		this.webPageConf.setWebPageProperties(webPageConf);
	}
}
