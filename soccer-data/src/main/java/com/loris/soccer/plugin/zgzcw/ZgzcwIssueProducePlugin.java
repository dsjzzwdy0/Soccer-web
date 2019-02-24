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
package com.loris.soccer.plugin.zgzcw;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.soccer.plugin.zgzcw.base.AbstractProducePlugin;

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
public class ZgzcwIssueProducePlugin extends AbstractProducePlugin
{
	private static Logger logger = Logger.getLogger(ZgzcwIssueProducePlugin.class);
	
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		super.initialize(context);	
	}
	
	/**
	 * 产生任务程序
	 * @param context 插件运行环境
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
		String errorinfo = "";
		try
		{
			if(!createFromBdMainPage(context))
			{
				logger.info("No task produce from BDMainPage.");
			}
		}
		catch(HostForbiddenException exception)
		{
			errorinfo = exception.getMessage() + ", no task will be produced.";
		}
		catch (UrlFetchException exception)
		{
			errorinfo = "Fetch " + exception.getMessage() + " error, no task will be produced.";
		}
		catch(WebParserException exception)
		{
			errorinfo = "Fetch " + exception.getMessage() + " error, no task will be produced.";
		}
		
		//输入信息
		if(StringUtils.isNotBlank(errorinfo))
		{
			logger.info(errorinfo);
		}
	}
}
