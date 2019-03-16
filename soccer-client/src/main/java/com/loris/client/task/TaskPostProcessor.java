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
package com.loris.client.task;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEventProducer;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.plugin.TaskPostProcessPlugin;


/**   
 * @ClassName:  TaskPostProcessor  
 * @Description: 任务完成之后的后处理类，完成数据的分析、数据的存储等动作  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TaskPostProcessor extends TaskEventProducer implements Closeable
{	
	/** 插件运行的环境 */
	TaskPluginContext context;
	
	/**
	 * 任务处理线程
	 */
	class TaskPostProcessPluginThread extends Thread
	{
		TaskPostProcessPlugin plugin;
		Task task;
		
		/**
		 * Create a new intance of TaskPostProcessPluginThread.
		 * @param plugin
		 * @param task
		 */
		TaskPostProcessPluginThread(TaskPostProcessPlugin plugin, Task task)
		{
			this.plugin = plugin;
			this.task = task;
		}
		
		public void run()
		{
			execute(plugin, task);
		}
	}
	
	/** 任务后处理的插件 */
	private List<TaskPostProcessPlugin> plugins = new ArrayList<>();
	
	/**
	 * 运行环境
	 * @param context
	 */
	public TaskPostProcessor(TaskPluginContext context)
	{
		this.context = context;
	}
	
	/**
	 * 添加任务处理的插件工具
	 * @param plugin 插件
	 */
	public void addTaskPostProcessPlugin(TaskPostProcessPlugin plugin)
	{
		plugins.add(plugin);
	}
	
	/**
	 * 执行任务
	 * @param plugin
	 * @param task
	 */
	protected void execute(TaskPostProcessPlugin plugin, Task task)
	{
		try
		{
			notifyTaskEvent(new TaskEvent(task, TaskEventType.PostProcess));
			if(plugin.postProcess(context, task))
			{
				notifyTaskEvent(new TaskEvent(task, TaskEventType.PostProcessed));
			}
			else {
				notifyTaskEvent(new TaskEvent(task, TaskEventType.PostError));
			}
		}
		catch(Exception e)
		{
			notifyTaskEvent(new TaskEvent(task, TaskEventType.PostError, e));
		}
	}
	
	/**
	 * 执行任务后处理
	 * @param task
	 */
	public void execute(Task task)
	{
		TaskPostProcessPlugin plugin = getBestFitProcessPlugin(task);
		if(plugin == null)
		{
			notifyTaskEvent(new TaskEvent(task, TaskEventType.PostError, new Throwable("There are no plugin to process the task '" + task.getName() + "'")));
			return;
		}
		execute(plugin, task);
	}
	
	/**
	 * 获得最佳的任务处理插件
	 * @param task 任务
	 * @return 插件工具，如果没有合适的插件，则返回null
	 */
	public TaskPostProcessPlugin getBestFitProcessPlugin(Task task)
	{
		for (TaskPostProcessPlugin taskPostProcessPlugin : plugins)
		{
			if(taskPostProcessPlugin.isFit(task))
			{
				return taskPostProcessPlugin;
			}
		}
		return null;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		for (TaskPostProcessPlugin plugin : plugins)
		{
			plugin.close();
		}		
	}
	
	/**
	 * 初始化
	 */
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		for (TaskPostProcessPlugin plugin : plugins)
		{
			plugin.initialize(context);
		}	
	}
}
