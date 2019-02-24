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
package com.loris.client.task.plugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.loris.client.model.WebPage;
import com.loris.client.task.Task;
import com.loris.client.task.basic.BasicTask;
import com.loris.client.task.basic.WebPageTask;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.event.TaskEventListener;

/**   
 * @ClassName:  BasicTaskProducePlugin  
 * @Description: 任务产生器的基本插件工具  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class BasicWebPageTaskProducePlugin extends BasicTaskPlugin implements TaskProducePlugin
{
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#removeTaskEventListner(com.loris.client.task.event.TaskEventListener)
	 */
	@Override
	public void removeTaskEventListner(TaskEventListener listener)
	{
		listeners.remove(listener);		
	}

	/**
	 * 添加任务监听器 
	 * @see com.loris.client.task.plugin.TaskProducePlugin#addTaskEventListeners(java.util.List)
	 */
	@Override
	public void addTaskEventListeners(List<TaskEventListener> listeners)
	{
		this.listeners.addAll(listeners);
	}

	/**
	 * 清除任务监听器 (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#clearTaskEventListners()
	 */
	@Override
	public void clearTaskEventListners()
	{
		listeners.clear();
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#produce()
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
		//System.out.println("产生新的任务");
		for(int i = 0; i < 30; i ++)
		{
			Task task = new BasicTask();
			task.setName("Task[" + i + "]");
			task.setPriority((i % 7));
			
			notifyTaskEvent(new TaskEvent(task, TaskEventType.Created));
		}
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#isFit(com.loris.client.task.Task)
	 */
	@Override
	public boolean isFit(Task task)
	{
		throw new UnsupportedOperationException("This is not used for TaskProducePlugin.");
	}

	/**
	 * 该方法将不会被执行
	 * @param
	 */
	@Override
	public boolean execute(TaskPluginContext context, Task task)
	{
		throw new UnsupportedOperationException("This will not be called.");
	}
	
	/**
	 * 创建任务
	 * @param page 数据页面
	 * @param quiet 是否不通知插件环境
	 * @return 待处理的任务
	 */
	protected WebPageTask createWebPageTask(WebPage page, boolean quiet)
	{
		WebPageTask task = new WebPageTask(page);
		if(!quiet)
		{
			notifyTaskEvent(new TaskEvent(task, TaskEventType.Created));
		}
		return task;
	}
	
	/**
	 * 创建任务，该任务创建之后，将会通知插件运行环境
	 * @param page 数据页面
	 * @return 待处理的任务
	 */
	protected WebPageTask createWebPageTask(WebPage page)
	{
		return createWebPageTask(page, false);
	}
}
