/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooBasePlugin.java   
 * @Package com.loris.soccer.datacom.loris.soccer.model   
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
import org.springframework.stereotype.Component;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.impl.HtmlUnitFetcher;
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
import com.loris.common.util.DateUtil;
import com.loris.common.util.KeyMap;
import com.loris.common.util.ToolUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.conf.WebPageProperties;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.OkoooPageCreator;
import com.loris.soccer.data.okooo.OkoooPageParser;
import com.loris.soccer.data.okooo.filter.OkoooMatchFilter;
import com.loris.soccer.data.okooo.filter.OkoooPageFilter;
import com.loris.soccer.data.okooo.util.OkoooUtil;
import com.loris.soccer.filter.WebPageFilter;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.base.MatchItem;
import com.loris.soccer.service.DataService;

/**   
 * @ClassName:  OkoooBasePlugin.java   
 * @Description: 澳 客网数据下载接口
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public abstract class OkoooBasePlugin extends BasicWebPageTaskPlugin implements TaskProducePlugin, TaskProcessPlugin
{
	private static Logger logger = Logger.getLogger(OkoooBasePlugin.class);
	
	/** 网络下载器 */
	protected WebFetcher webPagefetcher = null;
	
	/** 页面的配置项 */
	protected WebPageProperties webPageConf = null;
	
	/** 澳客数据服务器 */
	@Autowired
	protected DataService okoooDataService;
	
	@Autowired
	protected WebPageService pageService;
	
	/** 过滤器 */
	protected WebPageFilter webPageFilter = null;
	
	/** 注册的过滤器 */
	private Map<String, Filter<?>> filters = new HashMap<>();
	
	/** 下载子页面时等候的时间 */
	protected int childInterval = 1000;
	
	/**
	 * Create a new instance of ZgzcwBasePlugin.
	 */
	public OkoooBasePlugin(String name, WebPageProperties webPageConf)
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
		
		FetcherSetting setting = ApplicationContextHelper.getBean("okoooSetting");
		webPagefetcher = new HtmlUnitFetcher(setting);
		try
		{
			webPagefetcher.init();
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Error occured when HtmlUnitFetcher init().");
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
		
		filters.put(SoccerConstants.SOCCER_DATA_MATCH, new OkoooMatchFilter());
	}
	
	/**
	 * 创建默认的页面过滤器
	 * @return
	 */
	protected WebPageFilter createDefaultWebPageFilter()
	{
		OkoooPageFilter filter = new OkoooPageFilter();
		filter.setSource(OkoooConstants.SOURCE_OKOOO);
		filter.setStart(DateUtil.addDayNum(new Date(), - webPageConf.getDayNumOfGetPages()));
		registerProcessPageTypes(filter);
		return filter;
	}
	
	/**
	 * 注册处理的页面类型
	 * @param types 数据的类型
	 */
	protected void registerProcessPageTypes(WebPageFilter webPageFilter)
	{
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProcessPlugin#execute(com.loris.client.task.context.TaskPluginContext, com.loris.client.task.Task)
	 */
	@Override
	public boolean execute(TaskPluginContext context, Task task)
			throws UrlFetchException, WebParserException, IOException, HostForbiddenException, SQLException
	{
		if (!(task instanceof WebPage))
		{
			return false;
		}
		return executeWebPageTask(context, (WebPage) task);
	}
	

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#produce(com.loris.client.task.context.TaskPluginContext)
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
		if(!StringUtils.equals(OkoooConstants.SOURCE_OKOOO, page.getSource()))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 产生新的下载任务
	 * @param type
	 * @param records
	 */
	@SuppressWarnings("unchecked")
	protected void produceTask(String pageType, TableRecords records)
	{
		List<OkoooIssueMatch> matchs = null;
		// 通过不同的页面进行数据处理
		switch (pageType)
		{
		case OkoooConstants.PAGE_LOTTERY_BD:
			matchs = (List<OkoooIssueMatch>)records.get(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_BD_LIST);
			break;
		case OkoooConstants.PAGE_LOTTERY_JC:
			matchs = (List<OkoooIssueMatch>)records.get(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_JC_LIST);
			break;
		default:
			break;
		}
		
		if(matchs != null)
		{
			createMatchTasks(matchs, (Filter<MatchItem>)filters.get(SoccerConstants.SOCCER_DATA_MATCH));
		}
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
			if(createWebPageTask(OkoooPageCreator.createOkoooWebPage(type, params), match, quiet))
				size ++;
		}
		return size;
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
		
		if (webPageConf.isPageBeCreated(OkoooConstants.PAGE_ODDS_OP))
		{
			types.add(OkoooConstants.PAGE_ODDS_OP);
		}
		if (webPageConf.isPageBeCreated(OkoooConstants.PAGE_ODDS_YP))
		{
			types.add(OkoooConstants.PAGE_ODDS_YP);
		}

		return types;
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
		TableRecords records = download(page);
		if (records == null || records.isEmpty())
		{
			return false;
		}
		
		//解析数据结果
		try
		{
			saveTableRecords(records);
			if (webPageConf.isPageProduceNewTask(page.getType()))
			{
				produceTask(page.getType(), records);
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
			okoooDataService.saveTableRecords(records);
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
	 * 数据下载，在下载之后将进行数据的解析
	 * @param page 网页页面
	 * @return 是否下载成功的标志
	 * @throws IOException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 */
	protected TableRecords download(WebPage page) throws IOException, HostForbiddenException, UrlFetchException, WebParserException
	{
		logger.info("Starting get the data from : " + page.getUrl() 
			+ (StringUtils.isEmpty(page.getParamstext()) ? "" : ", params:" + page.getParamstext()) );
		
		TableRecords records = null;
		switch (page.getType())
		{
		case OkoooConstants.PAGE_LOTTERY_BD:
		case OkoooConstants.PAGE_LOTTERY_JC:
			if(!webPagefetcher.download(page))
			{
				logger.warn("Error occured when HtmlUnitFetcher execute: " + page.getUrl());
				return null;
			}
			records = OkoooPageParser.parseWebPage(page);
			break;
		case OkoooConstants.PAGE_ODDS_OP:
		case OkoooConstants.PAGE_ODDS_YP:
			records = OkoooUtil.downloadOkoooOddsPage((HtmlUnitFetcher)webPagefetcher, page);
			break;
		default:
			break;
		}
		
		try
		{
			pageService.save(page);
		}
		catch (Exception e) {
			logger.info("Error occured when save the page: " + page.getUrl());
		}
		return records;
	}
	
	
	/**
	 * 配置默认的信息
	 * @return
	 */
	public static WebPageProperties getDefaultProperties()
	{
		WebPageProperties properties = new WebPageProperties();
		properties.setDayNumOfGetPages(10);
		properties.setNumDayOfHasOdds(4);

		properties.setPageProduceNewTask(OkoooConstants.PAGE_LOTTERY_JC, true);
		properties.setPageProduceNewTask(OkoooConstants.PAGE_LOTTERY_BD, true);
		return properties;
	}

}
