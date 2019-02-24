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
package com.loris.soccer.data.zgzcw;

import java.io.IOException;
import java.sql.SQLException;
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
import com.loris.client.model.WebPage;
import com.loris.client.task.Task;
import com.loris.client.task.basic.WebPageTask;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.plugin.BasicTaskProducePlugin;
import com.loris.common.model.TableRecords;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.constant.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.util.ZgzcwPageCreator;
import com.loris.soccer.data.zgzcw.util.ZgzcwPageParser;
import com.loris.soccer.model.base.MatchItem;
import com.loris.soccer.processor.HttpTaskProcessor;

/**   
 * @ClassName:  League   
 * @Description: 今日足彩数据下载产生器
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public class ZgzcwIssueProducePlugin extends BasicTaskProducePlugin
{
	private static Logger logger = Logger.getLogger(ZgzcwIssueProducePlugin.class);
		
	@Autowired
	HttpTaskProcessor httpCommonPlugin;
	
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		//logger.info("Intialize the HttpCommonPlugin '" + httpCommonPlugin.getName() + "'. ");	
		super.initialize(context);	
	}
	
	/**
	 * 产生任务程序
	 * @param context 插件运行环境
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
		WebPage bdMainPage = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LOTTERY_BD);
		WebPageTask task = new WebPageTask(bdMainPage);
		
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
				List<? extends MatchItem> matchItems = (List<? extends MatchItem>)records.get(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST);
				
				logger.info("There are " + matchItems.size() + " matches in the list.");
				
				Map<String, String> params = new HashMap<>();
				for (MatchItem matchItem : matchItems)
				{
					params.put(SoccerConstants.NAME_FIELD_MID, matchItem.getMid());
					Task task0 = new WebPageTask(ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params));
					
					notifyTaskEvent(new TaskEvent(task0, TaskEventType.Created));
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
	}
}
