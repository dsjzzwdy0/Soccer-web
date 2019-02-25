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
package com.loris.soccer.plugin.zgzcw.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.client.task.basic.WebPageTask;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.BasicWebPageTaskProducePlugin;
import com.loris.common.model.TableRecords;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.base.IssueMatch;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwConstants;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwPageCreator;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwPageParser;
import com.loris.soccer.processor.HttpTaskProcessor;

/**   
 * @ClassName: AbstractProducePlugin   
 * @Description: 下载数据创建任务插件  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractProducePlugin extends BasicWebPageTaskProducePlugin
{
	private static Logger logger = Logger.getLogger(AbstractProducePlugin.class);
	
	@Autowired
	protected HttpTaskProcessor httpCommonPlugin;
	
	/**
	 * 产生任务程序
	 * @param context 插件运行环境
	 */
	@Override
	public abstract void produce(TaskPluginContext context) throws IOException, SQLException;
	
	/**
	 * 建立比赛的数据下载任务
	 * @param match 比赛
	 */
	protected void createMatchDataTask(String mid)
	{
		Map<String, String> params = new HashMap<>();
		params.put(SoccerConstants.NAME_FIELD_MID, mid);
		
		//欧赔数据下载
		createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params));
		
		//亚盘数据下载
		createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_YP, params));
		
		//大小球数据下载
		createWebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_NUM, params));
	}
	
	/**
	 * 通过北单首页创建任务下载列表
	 * @return 下载数据量
	 */
	protected boolean createFromBdMainPage(TaskPluginContext context) throws IOException, SQLException, HostForbiddenException, UrlFetchException, WebParserException
	{
		WebPage bdMainPage = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LOTTERY_BD);
		WebPageTask task = this.createWebPageTask(bdMainPage, true);
		
		String errorinfo = "";
		try
		{
			logger.info("Starting preparing the data from : " + bdMainPage.getUrl());
			if(!httpCommonPlugin.execute(context, task))
			{
				errorinfo = "Failed to execute the webpage task: " + bdMainPage.getUrl() + " exit.";
			}
			else
			{
				TableRecords records = ZgzcwPageParser.parseWebPage(bdMainPage);
				@SuppressWarnings("unchecked")
				List<? extends IssueMatch> matchItems = (List<? extends IssueMatch>)records.get(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST);
				
				logger.info("There are " + matchItems.size() + " matches in the list.");				
				for (IssueMatch match : matchItems)
				{
					this.createMatchDataTask(match.getMid());
				}
				
				if(matchItems.size() > 0)
				{
					return true;
				}
			}
		}
		catch(HostForbiddenException exception)
		{
			errorinfo = "The host: " + bdMainPage.getHost() + " forbid the client to download data, no task will be produced.";
		}
		catch (UrlFetchException e)
		{
			errorinfo = "Fetch " + bdMainPage.getHost() + " error, no task will be produced.";
		}
		catch(WebParserException e)
		{
			errorinfo = "Fetch " + bdMainPage.getHost() + " error, no task will be produced.";
		}
		
		if(StringUtils.isNotBlank(errorinfo))
		{
			logger.info(errorinfo);
		}
		return false;
	}
}
