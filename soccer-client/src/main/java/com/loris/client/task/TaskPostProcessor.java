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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEventProducer;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.plugin.TaskPostProcessPlugin;
import com.loris.client.task.util.ThreadUtil;


/**   
 * @ClassName:  TaskPostProcessor  
 * @Description: 任务完成之后的后处理类，完成数据的分析、数据的存储等动作  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TaskPostProcessor extends TaskEventProducer implements Runnable
{
	private static Logger logger = Logger.getLogger(TaskPostProcessor.class);
	
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
	
	/** 任务中断时间 */
	private int taskSleepTime = 10;
	
	/** 无任务处理过程中闲置的时间  */
	private int idleSleepTime = 1000;
	
	/** 是否完成任务 */
	private boolean finished = false;
	
	/** 是否正在运行过程中 */
	private boolean isRunning = false;
		
	/** 任务队列数据 */
	private Queue<Task> tasks = new LinkedList<Task>();
	
	/** 任务后处理的插件 */
	private List<TaskPostProcessPlugin> plugins = new ArrayList<>();
	
	/**
	 * 获得任务间隔的时间
	 * @return
	 */
	public int getTaskSleepTime()
	{
		return taskSleepTime;
	}

	public void setTaskSleepTime(int taskSleepTime)
	{
		this.taskSleepTime = taskSleepTime;
	}

	public int getIdleSleepTime()
	{
		return idleSleepTime;
	}

	public void setIdleSleepTime(int idleSleepTime)
	{
		this.idleSleepTime = idleSleepTime;
	}
	
	/**
	 * 添加任务处理的插件工具
	 * @param plugin 插件
	 */
	public void addTaskPostProcessPlugin(TaskPostProcessPlugin plugin)
	{
		plugins.add(plugin);
	}

	public boolean isRunning()
	{
		return isRunning;
	}

	protected void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}

	public boolean isFinished()
	{
		return finished;
	}

	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	
	/**
	 * 结束任务的处理
	 */
	public void finish()
	{
		this.finished = true;
	}

	/**
	 * 添加任务队列
	 * @param task 任务
	 */
	public void add(Task task)
	{
		tasks.add(task);
	}
	
	/**
	 * 删除任务
	 * 
	 * @param task 任务
	 */
	public void remove(Task task)
	{
		tasks.remove(task);
	}
	
	public void execute(TaskPostProcessPlugin plugin, Task task)
	{
		try
		{
			notifyTaskEvent(new TaskEvent(task, TaskEventType.PostProcess));
			plugin.execute(task);
			notifyTaskEvent(new TaskEvent(task, TaskEventType.PostProcessed));
		}
		catch(Exception e)
		{
			notifyTaskEvent(new TaskEvent(task, TaskEventType.PostError, e));
		}
	}

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		logger.info("Start TaskPostProcessor...");
		isRunning = true;
		while(true)
		{
			//logger.info("TaskPostProcessor Post process " + tasks.size() + "...");
			if(tasks.size() > 0)
			{
				Task task = tasks.poll();
				try
				{
					TaskPostProcessPlugin plugin = getBestFitProcessPlugin(task);
					if(plugin == null)
					{
						notifyTaskEvent(new TaskEvent(task, TaskEventType.PostError,
								new Exception("No TaskPostProcessPlugin to process the Task[" + task.getName() + "]")));
					}
					else
					{
						//开启任务处理的线程
						new TaskPostProcessPluginThread(plugin, task).start();
					}
				}
				catch(Exception e)
				{
					notifyTaskEvent(new TaskEvent(task, TaskEventType.PostError, e));
				}				
				ThreadUtil.sleep(taskSleepTime);
			}
			else
			{
				//任务后处理，已经结束了，停止该线程处理，
				if(finished)
				{
					break;
				}
				//线程停止一个闲置的时间
				ThreadUtil.sleep(idleSleepTime);
			}
		}
		
		//设置没有运行中的标志
		isRunning = false;		
		//logger.info("End TaskPostProcessor...");
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
}
