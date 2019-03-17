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
package com.loris.soccer.zgzcw.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.fetcher.WebFetcher;
import com.loris.client.model.WebPage;
import com.loris.client.service.WebPageService;
import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.BasicWebPageTaskProducePlugin;
import com.loris.client.task.plugin.TaskProcessPlugin;
import com.loris.client.task.plugin.TaskProducePlugin;
import com.loris.common.filter.DateFilter;
import com.loris.common.filter.Filter;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.KeyMap;
import com.loris.common.util.ToolUtil;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.League;
import com.loris.soccer.model.base.BaseMatch;
import com.loris.soccer.service.LeagueService;
import com.loris.soccer.service.impl.SoccerDataService;
import com.loris.soccer.zgzcw.util.ZgzcwConstants;
import com.loris.soccer.zgzcw.util.ZgzcwPageCreator;
import com.loris.soccer.zgzcw.util.ZgzcwPageParser;

/**
 * @ClassName: AbstractProducePlugin
 * @Description: 下载数据创建任务插件
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public abstract class AbstractProducePlugin extends BasicWebPageTaskProducePlugin implements TaskProducePlugin, TaskProcessPlugin
{
	private static Logger logger = Logger.getLogger(AbstractProducePlugin.class);

	/** 数据下载器 */
	protected WebFetcher webPagefetcher;
	
	@Autowired
	protected SoccerDataService soccerDataService;
	
	@Autowired
	protected LeagueService leagueService;
	
	@Autowired
	protected WebPageService pageService;
	
	/**
	 * Create a new instance of AbstractProducePlugin.
	 */
	public AbstractProducePlugin()
	{
	}

	/**
	 * 产生任务程序
	 * @param context 插件运行环境
	 */
	@Override
	public abstract void produce(TaskPluginContext context) throws IOException, SQLException;
	
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
	public boolean execute(TaskPluginContext context, Task task) throws IOException, SQLException
	{
		if(!(task instanceof WebPage))
		{
			return false;
		}
		try
		{
			return (executeWebPageTask(context,(WebPage) task) != null);
		}
		catch (Exception e)
		{
			logger.warn("Warn: " + e.toString());
			return false;
		}
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
	protected TableRecords executeWebPageTask(TaskPluginContext context, WebPage page) 
			throws IOException, WebParserException, HostForbiddenException, UrlFetchException
	{
		if (StringUtils.isBlank(page.getContent()) && !download(page))
		{
			logger.info("Error when HttpCommonExecutor execute: " + page.getUrl());
			return null;
		}
		
		//解析数据结果
		TableRecords records = ZgzcwPageParser.parseWebPage(page);
		if(records != null)
		{
			saveTableRecords(records);
		}		
		return records;
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
		logger.info("Starting get the data from : " + page.getUrl());
		if(webPagefetcher.download(page))
		{
			pageService.save(page);
			return true;
		}
		return false;
	}
	
	/**
	 * 创建联赛数据下载页面
	 * @param league 联赛数据下载页面
	 */
	protected void createLeagueCenterTask(League league)
	{
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_LID, league.getLid());
		createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(league.getType(), params));
	}
	
	/**
	 * 创建联赛数据下载页面
	 * @param leagues 联赛列表
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
				createLeagueCenterTask(league);
				size ++;
			}
		}
		return size > 0;
	}

	/**
	 * 建立比赛的数据下载任务
	 * @param match 比赛数据
	 * @param hasOp 是否下载欧赔数据
	 * @param hasYp 是否下载亚盘数据
	 * @param hasNum 是否下载大小球数据
	 */
	protected void createMatchDataTask(BaseMatch match, boolean hasOp, boolean hasYp, boolean hasNum)
	{
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_MID, match.getMid());		
		params.put(SoccerConstants.NAME_FIELD_MATCHTIME, DateUtil.formatDateTime(match.getMatchtime()));
		
		// 欧赔数据下载
		if (hasOp)
		{
			createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params));
		}

		// 亚盘数据下载
		if (hasYp)
		{
			createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_YP, params));
		}

		// 大小球数据下载
		if (hasNum)
		{
			createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_NUM, params));
		}
	}

	/**
	 * 创建比赛数据下载任务
	 * @param matchItems 数据列表
	 * @param filter 过滤器
	 * @return 是否创建的数据量大小0
	 */
	protected boolean createMatchTasks(List<? extends BaseMatch> matchItems, DateFilter filter)
	{
		if(matchItems == null || matchItems.size() == 0)
		{
			logger.info("There are no MatchItems in the match item list.");
			return false;
		}
		
		logger.info("There are " + matchItems.size() + " matches in the list.");
		int size = 0;
		for (BaseMatch matchItem : matchItems)
		{
			if (filter == null || filter.accept(matchItem.getMatchtime()))
			{
				if(ToolUtil.isEmpty(matchItem.getMatchtime()))
				{
					logger.info("Match has no matchtime: " + matchItem);
					continue;
				}
				createMatchDataTask(matchItem, true, true, true);
				size ++;
			}
		}
		return size > 0;
	}
	
	/**
	 * 存储数据
	 * @param records
	 * @throws Exception
	 */
	public void saveTableRecords(TableRecords records) throws IOException
	{
		long st = System.currentTimeMillis();
		soccerDataService.saveTableRecords(records);
		long en = System.currentTimeMillis();
		logger.info("Save TableRecords " + records.toString() + " spend time is " + (en - st) + " ms.");
	}
	
	/**
	 * 从数据网页初始化下载数据任务
	 * @param context 插件运行环境
	 * @param page 网络页数据
	 * @return 是否创建成成功的标志
	 * 
	 * @throws IOException
	 * @throws WebParserException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 */
	public boolean createTaskFromWebPage(TaskPluginContext context, WebPage page)
			throws IOException, WebParserException, HostForbiddenException, UrlFetchException
	{
		return createTaskFromWebPage(context, page, null);
	}

	/**
	 * 从数据网页初始化下载数据任务
	 * @param context 插件运行环境
	 * @param filter 数据过滤器
	 * @param page 网络页数据
	 * @return 是否创建成成功的标志
	 * @throws IOException
	 * @throws WebParserException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 */
	@SuppressWarnings("unchecked")
	public boolean createTaskFromWebPage(TaskPluginContext context, WebPage page, Filter<?> filter)
			throws IOException, WebParserException, HostForbiddenException, UrlFetchException
	{
		//解析数据结果
		TableRecords records = executeWebPageTask(context, page);
		if(records == null)
		{
			return false;
		}
		
		//通过不同的页面进行数据处理
		switch (page.getType())
		{
		case ZgzcwConstants.PAGE_LOTTERY_BD:
			return createMatchTasks((MatchItemList)records.get(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST), (DateFilter)filter);
		case ZgzcwConstants.PAGE_LOTTERY_JC:
			return createMatchTasks((MatchItemList)records.get(SoccerConstants.SOCCER_DATA_MATCH_JC_LIST), (DateFilter) filter);
		case ZgzcwConstants.PAGE_CENTER:
			return createLeagueCenterTasks((LeagueList) records.get(SoccerConstants.SOCCER_DATA_LEAGUE_LIST), (Filter<League>)filter);
		default:
			return false;
		}
	}
}
