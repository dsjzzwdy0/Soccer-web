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

import org.apache.log4j.Logger;

import com.loris.client.task.Task;
import com.loris.client.task.basic.WebPageTask;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.BasicTaskPostProcessPlugin;

/**   
 * @ClassName:  League   
 * @Description: 中国足彩网网页数据处理类   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ZgzcwPostProcessPlugin extends BasicTaskPostProcessPlugin
{
	/** */
	private static Logger logger = Logger.getLogger(ZgzcwPostProcessPlugin.class);
	
	/**
	 * 执行任务处理工作
	 * @param context TaskPluginContext运行环境
	 * @param task 任务
	 * @return 任务执行成功与否的标志
	 */
	@Override
	public boolean execute(TaskPluginContext context, Task task)
	{
		if(task instanceof WebPageTask)
		{
			return execute(context, (WebPageTask)task);
		}
		return false;
	}
	
	/**
	 * 执行网页任务后处理，这里有两个步骤：一是网页内容的解析、二是网页内容存储到数据库中
	 * @param context 运行环境
	 * @param task 任务
	 * @return 任务执行成功与否的标志
	 */
	protected boolean execute(TaskPluginContext context, WebPageTask task)
	{
		logger.info("Post Execute WebPageTask: " + task.getPage().getUrl());
		return false;
	}
}
