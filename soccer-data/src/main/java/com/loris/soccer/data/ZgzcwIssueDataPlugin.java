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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.loris.client.model.WebPage;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.TaskProcessPlugin;
import com.loris.client.task.plugin.TaskProducePlugin;
import com.loris.common.util.DateUtil;
import com.loris.soccer.data.conf.WebPageProperties;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.ZgzcwPageCreator;
import com.loris.soccer.filter.WebPageFilter;

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
	
	/** 间隔时间 */
	protected long interval = 3000;
		
	/**
	 * Create a new instance of ZgzcwIssueProducePlugin
	 */
	public ZgzcwIssueDataPlugin()
	{
		this(ZgzcwBasePlugin.getDefaultProperties());
	}
	
	/**
	 * Create a new instance of ZgzcwIssueProducePlugin
	 */
	public ZgzcwIssueDataPlugin(WebPageProperties webPageConf)
	{
		super("当日数据下载", webPageConf);
		this.updateLeagueCurrentRounds = true;
	}
	
	/**
	 * 注册处理的页面类型
	 * @param types 数据的类型
	 */
	@Override
	protected void registerProcessPageTypes(WebPageFilter webPageFilter)
	{
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_ODDS_OP);
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_ODDS_YP);
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_ODDS_NUM);
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_LEAGUE_LEAGUE);
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_LEAGUE_CUP);
		webPageFilter.addAcceptPageType(ZgzcwConstants.PAGE_LIVE_BD);
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
		initPages.add(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LIVE_BD));
		
		try
		{
			for (WebPage webPage : initPages)
			{
				if (!createTaskFromWebPage(context, webPage))
				{
					logger.warn("No task produce from WebPage: " + webPage.getName());
				}

				ThreadUtil.sleep(interval);
			}
			
			//创建数据映射
			createMappingsFromIssueMatch(DateUtil.addDayNum(new Date(), -1), null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.warn("Warn: produce task list error info > " + e.getMessage());
		}
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
