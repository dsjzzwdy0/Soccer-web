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
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.plugin.TaskProcessPlugin;
import com.loris.client.task.event.TaskEventProducer;

/**
 * @ClassName: TaskProcessor
 * @Description: 任务处理器类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class TaskProcessor extends TaskEventProducer implements Closeable
{
	/** 执行的任务 */
	private List<TaskProcessPlugin> plugins = new ArrayList<>();

	/** 插件运运行环境 */
	private TaskPluginContext context;

	/**
	 * 任务线程
	 */
	class TaskThread extends Thread
	{
		Task task = null;

		/**
		 * Create a new instance of TaskThread.
		 */
		public TaskThread(Task task)
		{
			this.task = task;
		}

		/*
		 * 执行运务
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run()
		{
			execute(task);
		}
	}

	public TaskProcessor(TaskPluginContext context)
	{
		this.context = context;
	}

	/**
	 * 添加任务处理插件
	 * 
	 * @param plugin
	 *            插件工具
	 */
	public void addTaskProcessPlugIn(TaskProcessPlugin plugin)
	{
		this.plugins.add(plugin);
	}

	/**
	 * 处理任务数据
	 * 
	 * @param task
	 */
	public void process(Task task)
	{
		if (task == null)
		{
			return;
		}
		new TaskThread(task).start();
	}

	/**
	 * 执行任务线程
	 * 
	 * @param task
	 */
	public void execute(Task task)
	{
		try
		{
			TaskProcessPlugin plugin = getTaskProcessPlugin(task);
			if (plugin == null)
			{
				throw new Exception("There are no TaskProcessPlugin to process the task[" + task.getName() + "]");
			}
			notifyTaskEvent(new TaskEvent(task, TaskEventType.Excute));
			if(plugin.execute(context, task))
			{
				notifyTaskEvent(new TaskEvent(task, TaskEventType.Finished));
			}
			else {
				notifyTaskEvent(new TaskEvent(task, TaskEventType.Error));
			}
		}
		catch (Exception e)
		{
			notifyTaskEvent(new TaskEvent(task, TaskEventType.Error, e));
		}
	}

	/**
	 * 根据任务内容来获得任务处理插件
	 * 
	 * @param task
	 *            任务
	 * @return 插件
	 */
	protected TaskProcessPlugin getTaskProcessPlugin(Task task)
	{
		for (TaskProcessPlugin plugin : plugins)
		{
			if (plugin.isFit(task))
				return plugin;
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		for (TaskProcessPlugin plugin : plugins)
		{
			plugin.close();
		}
	}
	
	@Override
	public void initialize() throws IOException
	{
		for (TaskProcessPlugin plugin : plugins)
		{
			plugin.intialize(context);
		}
	}
}
