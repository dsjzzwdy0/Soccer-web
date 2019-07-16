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
import java.util.List;

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
import com.loris.client.task.util.ThreadUtil;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.model.TableRecords;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.conf.WebPageProperties;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.OkoooPageParser;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.OkoooOddsOp;
import com.loris.soccer.model.OkoooOddsYp;
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
	
	/** 下载子页面时等候的时间 */
	protected int childInterval = 1000;
	
	/**
	 * @param name
	 */
	public OkoooBasePlugin(String name)
	{
		super(name);
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
			throw new IllegalArgumentException("Error occured when HttpCommonFetcher init().");
		}
		
		//初始化过滤器
		//initFilter(context);
		initialized = true;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProcessPlugin#execute(com.loris.client.task.context.TaskPluginContext, com.loris.client.task.Task)
	 */
	@Override
	public abstract boolean execute(TaskPluginContext context, Task task)
			throws UrlFetchException, WebParserException, IOException, HostForbiddenException, SQLException;
	

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#produce(com.loris.client.task.context.TaskPluginContext)
	 */
	@Override
	public abstract void produce(TaskPluginContext context) throws IOException, SQLException;
	
	/**
	 * 产生新的下载任务
	 * @param type
	 * @param records
	 */
	protected void produceTask(String type, TableRecords records)
	{
		
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
		case OkoooConstants.PAGE_SCORE_BD:
		case OkoooConstants.PAGE_SCORE_JC:
			if(!webPagefetcher.download(page))
			{
				logger.warn("Error occured when HtmlUnitFetcher execute: " + page.getUrl());
				return null;
			}
			records = OkoooPageParser.parseWebPage(page);
			break;
		case OkoooConstants.PAGE_ODDS_OP:
			
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
	 * 下载澳客网赔率数据页面
	 * @param fetcher
	 * @param page
	 * @return
	 * @throws IOException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 * @throws WebParserException
	 */
	public static TableRecords downloadOkoooOddsPage(HtmlUnitFetcher fetcher, WebPage page) 
			throws IOException, HostForbiddenException, UrlFetchException, WebParserException
	{
		TableRecords results = null;
		int totalComps = 0;				//总的单位家数
		String type = null;
		switch (page.getType())
		{
		case OkoooConstants.PAGE_ODDS_YP:
			type = OkoooConstants.PAGE_ODDS_YP_CHILD;
			break;
		case OkoooConstants.PAGE_ODDS_OP:
			type = OkoooConstants.PAGE_ODDS_OP_CHILD;
			break;
		case OkoooConstants.PAGE_ODDS_OP_CHILD:
		case OkoooConstants.PAGE_ODDS_YP_CHILD:
			type = page.getType();
			break;
		default:
			logger.info("The page type is '" + page.getType() + "' is not supported by the downloadOkoooOddsPage method.");
			return null;
		}
		
		page.setType(type);		
		while(fetcher.download(page))
		{
			TableRecords records = OkoooPageParser.parseWebPage(page);
			
			//如果是记录数据为空，则跳出循环
			if(records == null || records.isEmpty())
			{
				break;
			}
			
			if(results == null)
			{
				results = records;
			}
			else
			{
				combineOkoooOddsTableRecords(results, records);
			}
			
			if(totalComps <= 0)
			{
				totalComps = getCompsTotalNum(records);
			}
			
			//为了模拟人为操作，需要进行等待
			ThreadUtil.sleep(1000);
		}

		return results;
	}
	
	/**
	 * 合并两个数据记录
	 * @param dest 
	 * @param newRecords
	 */
	@SuppressWarnings("unchecked")
	protected static void combineOkoooOddsTableRecords(TableRecords dest, TableRecords newRecords)
	{
		for (String key : newRecords.keySet())
		{
			Object value = newRecords.get(key);
			switch (key)
			{
			case SoccerConstants.SOCCER_DATA_CASINO_COMP_LIST:
				List<CasinoComp> newComps = (List<CasinoComp>)value;
				DataList<CasinoComp> destComps = (DataList<CasinoComp>) dest.get(key);
				
				if(destComps == null)
				{
					dest.put(key, value);
				}
				else
				{
					combineList(destComps, newComps);
				}
				break;
			case SoccerConstants.SOCCER_DATA_ODDS_OKOOO_OP_LIST:
				List<OkoooOddsOp> newOps = (List<OkoooOddsOp>)value;
				DataList<OkoooOddsOp> destOps = (DataList<OkoooOddsOp>)dest.get(key);
				if(destOps == null)
				{
					dest.put(key, value);
				}
				else
				{
					combineList(destOps, newOps);
				}
				break;
			case SoccerConstants.SOCCER_DATA_RECORD_ODDS_YP_LIST:
				List<OkoooOddsYp> newYps = (List<OkoooOddsYp>)value;
				DataList<OkoooOddsYp> destYps = (DataList<OkoooOddsYp>)dest.get(key);
				if(destYps == null)
				{
					dest.put(key, value);
				}
				else
				{
					combineList(destYps, newYps);
				}
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * 添加数据到现有的记录中
	 * @param source
	 * @param dest
	 */
	protected static <T> void combineList(DataList<T> dest, List<T> source)
	{
		for (T object : source)
		{
			dest.add(object);
		}
	}
	
	/**
	 * 获得总的单位数
	 * @param records 记录值
	 * @return 数据量
	 */
	protected static int getCompsTotalNum(TableRecords records)
	{
		Integer t = (Integer)records.get(OkoooConstants.NAME_FIELD_CORP_TOTAL_NUM);
		return t == null ? 0 : t;
	}
}
