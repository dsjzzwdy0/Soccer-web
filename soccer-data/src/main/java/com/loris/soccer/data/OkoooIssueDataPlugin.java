/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OkoooIssueDataPlugin.java   
 * @Package com.loris.soccer.data   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目的
 */
package com.loris.soccer.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.soccer.data.conf.WebPageProperties;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.OkoooPageCreator;
import com.loris.soccer.filter.WebPageFilter;

import cn.hutool.core.thread.ThreadUtil;

/**   
 * @ClassName:  OkoooIssueDataPlugin    
 * @Description: 澳客当日比赛数据下载
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@Component
public class OkoooIssueDataPlugin extends OkoooBasePlugin
{
	private static Logger logger = Logger.getLogger(OkoooIssueDataPlugin.class);
	
	/**
	 * Create a new instance of OkoooIssueDataPlugin
	 */
	public OkoooIssueDataPlugin()
	{
		this(OkoooBasePlugin.getDefaultProperties());
	}
	
	/**
	 * @param name
	 * @param webPageConf
	 */
	public OkoooIssueDataPlugin(WebPageProperties webPageConf)
	{
		super("澳客当日数据下载", webPageConf);
	}

	/* (non-Javadoc)
	 * @see com.loris.soccer.data.OkoooBasePlugin#produce(com.loris.client.task.context.TaskPluginContext)
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
		List<WebPage> initPages = new ArrayList<>();
		initPages.add(OkoooPageCreator.createOkoooWebPage(OkoooConstants.PAGE_LOTTERY_BD));
		initPages.add(OkoooPageCreator.createOkoooWebPage(OkoooConstants.PAGE_LOTTERY_JC));
		
		try
		{
			for (WebPage webPage : initPages)
			{
				if (!createTaskFromWebPage(context, webPage))
				{
					logger.warn("No task produce from WebPage: " + webPage.getName());
				}

				ThreadUtil.sleep(childInterval);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.warn("Warn: produce task list error info > " + e.getMessage());
		}
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
	 * 注册处理的页面类型
	 * @param types 数据的类型
	 */
	@Override
	protected void registerProcessPageTypes(WebPageFilter webPageFilter)
	{
		webPageFilter.addAcceptPageType(OkoooConstants.PAGE_ODDS_OP);
		webPageFilter.addAcceptPageType(OkoooConstants.PAGE_ODDS_YP);
		webPageFilter.addAcceptPageType(OkoooConstants.PAGE_ODDS_OP_CHILD);
		webPageFilter.addAcceptPageType(OkoooConstants.PAGE_ODDS_YP_CHILD);
		webPageFilter.addAcceptPageType(OkoooConstants.PAGE_LOTTERY_BD);
		webPageFilter.addAcceptPageType(OkoooConstants.PAGE_LOTTERY_JC);
	}
}
