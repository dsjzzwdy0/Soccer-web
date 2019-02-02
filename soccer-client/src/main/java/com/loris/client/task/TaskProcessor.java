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

import java.util.List;
import java.util.ArrayList;

import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.event.TaskEventListener;

/**
 * @ClassName: TaskProcessor
 * @Description: 任务处理器类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class TaskProcessor 
{
	/**
	 * 任务线程
	 */
	class TaskThread extends Thread
	{
		Task task = null;
		/**
		 * 
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
			try
			{
				notifyTaskEvent(new TaskEvent(task, TaskEventType.Start));
				execute(task);
				notifyTaskEvent(new TaskEvent(task, TaskEventType.Finished));
			}
			catch (Exception e)
			{
				notifyTaskEvent(new TaskEvent(task, TaskEventType.Error, e));
			}
		}
	}
	
	/** 任务处理监听器 */
	private List<TaskEventListener> listeners = new ArrayList<>();
	
	/**
	 * 处理任务数据
	 * @param task
	 */
	public void process(Task task)
	{
		new TaskThread(task).start();
	}
	
	/**
	 * 添加任务监听器
	 * @param listener
	 */
	public void addTaskEventListener(TaskEventListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * 移除任务监听器
	 * @param listener
	 */
	public void removeTaskEventListener(TaskEventListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * 通知任务处理监听器
	 * @param task 任务
	 * @param type 类型
	 */
	public void notifyTaskEvent(TaskEvent event)
	{
		for (TaskEventListener listener : listeners)
		{
			listener.notify(event);
		}
	}
	
	/**
	 * 通知任务处理监听器
	 * @param task 任务
	 * @param type 类型
	 */
	public void notifyTaskEvent(Task task, TaskEventType type, Throwable exception)
	{
		TaskEvent event = new TaskEvent(task, type);
		for (TaskEventListener listener : listeners)
		{
			listener.notify(event);
		}
	}
	
	/**
	 * 执行任务线程
	 * @param task
	 */
	public void execute(Task task)
	{
		
	}
}
