/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
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
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.loris.client.fetcher.impl.HttpCommonFetcher;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.model.WebPage;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.TaskProcessPlugin;
import com.loris.client.task.plugin.TaskProducePlugin;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.filter.DateFilter;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.ZgzcwPageCreator;

import cn.hutool.core.thread.ThreadUtil;

/**
 * @ClassName: League
 * @Description: 今日足彩数据下载产生器
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class ZgzcwIssueDataPlugin extends ZgzcwBasePlugin implements TaskProducePlugin, TaskProcessPlugin
{
	private static Logger logger = Logger.getLogger(ZgzcwIssueDataPlugin.class);

	/** 日期过滤器 */
	private DateFilter dateFiter;
	
	protected long interval = 2000;
	
	/**
	 * Create a new instance of ZgzcwIssueProducePlugin
	 */
	public ZgzcwIssueDataPlugin()
	{
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
		super.initialize(context);
	}

	/**
	 * 产生任务程序
	 * 
	 * @param context 插件运行环境
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{

		List<WebPage> initPages = new ArrayList<>();
		initPages.add(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LOTTERY_BD));
		initPages.add(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LOTTERY_JC));
		
		try
		{
			for (WebPage webPage : initPages)
			{
				if (!createTaskFromWebPage(context, webPage, dateFiter))
				{
					logger.warn("No task produce from WebPage: " + webPage.getName());
				}

				ThreadUtil.sleep(interval);
			}
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			logger.warn("Warn: produce task list error info > " + e.getMessage());
		}
	}

	/**
	 * 设置日期过滤器
	 * 
	 * @param dateFiter 日期过滤器
	 */
	public void setDateFiter(DateFilter dateFiter)
	{
		this.dateFiter = dateFiter;
	}

	/**
	 * 设置初始化时线程处理初始化任务需要等待的时间
	 * @param interval
	 */
	public void setInterval(long interval)
	{
		this.interval = interval;
	}
}
