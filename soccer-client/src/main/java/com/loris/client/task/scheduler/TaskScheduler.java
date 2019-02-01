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
package com.loris.client.task.scheduler;

import java.util.List;

import org.apache.log4j.Logger;

import com.loris.client.task.Task;
import com.loris.client.task.TasksProducer;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEventListener;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.soccer.util.RandomUtil;

import java.util.ArrayList;

/**
 * @ClassName: TaskSchedulerThread
 * @Description: 任务调度管理器
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class TaskScheduler extends Thread implements TaskEventListener
{
	/** */
	private static Logger logger = Logger.getLogger(TaskScheduler.class);

	/** 运行中的线程 */
	private List<Task> runningTaskThreads = new ArrayList<>();

	/** 最大的在运行状态的线程数 */
	private int maxActiveTaskThread = 5;

	/** The thread number. */
	protected int threadIndex = 1;

	/** 任务生成器 */
	TasksProducer tasksProducer;
	
	/**
	 * Create a new instance of AbstractTaskScheduler
	 */
	public TaskScheduler()
	{
		this.setDaemon(false);
	}

	/**
	 * 构建一个任务调度器，带一个参数任务生成器
	 * 
	 * @param producer
	 */
	public TaskScheduler(TasksProducer producer)
	{
		this();
		this.tasksProducer = producer;
	}

	/**
	 * 加入运行中的线程
	 * 
	 * @param thread
	 */
	public void addRunningTask(Task task)
	{
		runningTaskThreads.add(task);
	}

	/**
	 * 从线程中移除
	 * 
	 * @param thread
	 */
	public void removeRunningTask(Task task)
	{
		runningTaskThreads.remove(task);
	}

	/**
	 * 最大的激活的线程数
	 * 
	 * @return
	 */
	public int getMaxActiveTaskThread()
	{
		return maxActiveTaskThread;
	}

	/**
	 * 设置最大的在运行的线程数
	 * 
	 * @param maxActiveTaskThread
	 */
	public void setMaxActiveTaskThread(int maxActiveTaskThread)
	{
		this.maxActiveTaskThread = maxActiveTaskThread;
	}

	/**
	 * 开始一个新的运务运行
	 * 
	 * @param task
	 *            任务
	 */
	public void startNewTaskThread(Task task)
	{
		int curRunningThreadNum = runningTaskThreads.size();
		if (curRunningThreadNum >= maxActiveTaskThread)
		{
			logger.info("The running theads number is " + curRunningThreadNum + " and is upto max thread number "
					+ maxActiveTaskThread + ", waiting for next idle thread.");
			return;
		}

		// 启动线程
		Thread thread = new Thread(task);
		thread.setDaemon(false);

		// 设置监听器
		task.addTaskEventListener(this);
		thread.start();
	}

	/**
	 * 任务处理监听器 (non-Javadoc)
	 * 
	 * @see com.loris.client.task.event.TaskEventListener#notify(com.loris.client.task.event.TaskEvent)
	 */
	@Override
	public void notify(TaskEvent event)
	{
		TaskEvent.TaskEventType taskType = event.getType();
		if (taskType == TaskEventType.Start)
		{
			logger.info("Starting to excute " + event.getTask().getName());
			addRunningTask(event.getTask());
		}
		else if (taskType == TaskEventType.Finished)
		{
			removeRunningTask(event.getTask());
			logger.info("Finished to excute " + event.getTask().getName());
		}
		else if(taskType == TaskEventType.Error)
		{
			tasksProducer.push(event.getTask());
			logger.info("Error occured when excuting " + event.getTask().getName() + ", push back to TaskProducer.");
		}
	}

	/**
	 * 运行线程
	 */
	@Override
	public void run()
	{
		if (tasksProducer == null)
		{
			logger.info("The TaskProducer is null, please set the TaskProducer first, TaskScheduler exit.");
			return;
		}

		try
		{
			if (!tasksProducer.isInitialized())
			{
				logger.info("Start prepare '" + tasksProducer.getName() + "'... ");
				
				if(tasksProducer.init())
				{
					logger.info("TaskProducer " + tasksProducer.getName() + " has " + tasksProducer.total() + " task to be processed.");
				}
				else
				{
					logger.info("TaskProducer " + tasksProducer.getName() + " has not parepared, TaskScheduler exit.");
					return;
				}
			}
			else 
			{				
				logger.info("TaskProducer " + tasksProducer.getName() + " has " + tasksProducer.total() + " task and left " + tasksProducer.leftSize() + " to be processed.");
			}
			
			while(tasksProducer.hasMoreTask())
			{
				if(hasIdleThread())
				{
					Task task = tasksProducer.popup();
					if(task != null)
					{
						startNewTaskThread(task);
					}
				}
				else 
				{
					int curRunningThreadNum = runningTaskThreads.size();
					logger.info("The running theads number is " + curRunningThreadNum + " and is upto max thread number "
							+ maxActiveTaskThread + ", waiting for next idle thread.");
				}				
				
				try
				{
					int interval = tasksProducer.interval() 
							+ (tasksProducer.needRandTime() ? RandomUtil.getRandom(tasksProducer.randTimeSeed()) : 0);
					Thread.sleep(interval);
				}
				catch (Exception e)
				{
				}
				
				if(tasksProducer.isStopped())
				{
					logger.info("The TaskProcuder " + tasksProducer.getName() + " has been set to stopped, interupped now.");
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Error occured when process TaskProcuder " + tasksProducer.getName() + ", exit now.");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean hasIdleThread()
	{
		int curRunningThreadNum = runningTaskThreads.size();
		if (curRunningThreadNum >= maxActiveTaskThread)
		{
			return false;
		}
		return true;
	}
}
