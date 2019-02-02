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

import org.apache.log4j.Logger;

import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEventListener;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.util.IdleThreadInfo;
import com.loris.client.task.util.TaskQueue;
import com.loris.client.task.util.ThreadUtil;

import java.io.Closeable;
import java.io.IOException;
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
public class MainTaskScheduler implements Runnable, TaskEventListener, Closeable, TaskVector
{
	/** */
	private static Logger logger = Logger.getLogger(MainTaskScheduler.class);

	/** 最大的在运行状态的线程数 */
	private int maxActiveTaskThread = 5;

	/** The thread number. */
	protected int threadIndex = 1;

	/** 任务调度管理器的名称 */
	private String name;

	/** 两次任务处理过程中的间隔时间 */
	private int interval = 200;

	/** 随机时间的种子值 */
	private int randTimeSeed;

	/** 是否停止 */
	private boolean isStopped = false;

	/** 空闲线程信息处理器 */
	IdleThreadInfo idleThreadInfo = null;

	/** 任务列表 */
	TaskQueue taskQueue = new TaskQueue();

	/** 任务生成器 */
	private TaskProducer taskProducer;

	/** 任务处理工具类 */
	private TaskProcessor taskProcessor;

	/** 任务后处理工具 */
	private TaskPostProcessor taskPostProcessor;

	/** 运行中的线程 */
	private List<Task> runningTaskThreads = new ArrayList<>();

	/**
	 * Create a new instance of AbstractTaskScheduler
	 */
	public MainTaskScheduler()
	{
		randTimeSeed = -100;
		idleThreadInfo = new IdleThreadInfo(this, maxActiveTaskThread);
	}

	/**
	 * 构建一个任务调度器，带一个参数任务生成器
	 * 
	 * @param producer
	 */
	public MainTaskScheduler(TaskProducer producer)
	{
		this();
		this.taskProducer = producer;
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
		idleThreadInfo.setMaxActiveThreadNum(maxActiveTaskThread);
	}

	/**
	 * 开始一个新的运务运行
	 * 
	 * @param task
	 *            任务
	 */
	protected void startNewTaskThread(Task task)
	{
		// 设置监听器,将会启动一个新的线程来完成任务
		if (taskProcessor != null)
		{
			taskProcessor.process(task);
		}
		else
		{
			notify(new TaskEvent(task, TaskEventType.Error, new Exception("The TaskProcess is null.")));
		}
	}

	/**
	 * 启动任务后处理线程
	 */
	protected void startTaskPostProcessThread()
	{
		if (taskPostProcessor == null)
		{
			return;
		}

		// 结束还没有
		while (taskPostProcessor.isFinished() && taskPostProcessor.isRunning())
		{
			logger.info("Waiting for TaskPostProcess to end.");
			// 等待一秒再进行下一步
			ThreadUtil.sleep(1000);
		}
		taskPostProcessor.setFinished(false);

		// 重新启动新的线程
		Thread thread = new Thread(taskPostProcessor);
		thread.setDaemon(false);
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
		Task task = event.getTask();
		if (taskType == TaskEventType.Created)
		{
			logger.debug("Create the task " + task.getName());
			taskQueue.add(task);
		}
		else if (taskType == TaskEventType.Excute)
		{
			logger.debug("Starting to excute " + task.getName());
			addRunningTask(task);
		}
		else if (taskType == TaskEventType.Finished)
		{
			removeRunningTask(task);
			if (taskPostProcessor != null)
			{
				taskPostProcessor.add(task);
			}
			logger.info("Finished to excute " + task.getName());
		}
		else if (taskType == TaskEventType.Error)
		{
			taskQueue.pushBack(task);
			removeRunningTask(task);
			logger.info("Error occured when excuting " + task.getName() + ", push back to TaskQueue.");
		}
	}

	/**
	 * 运行线程
	 */
	@Override
	public void run()
	{
		try
		{
			// 启动任务产生器
			if (taskProducer != null && !taskProducer.isInitialized())
			{
				// 执行任务产生器
				// 这里不用线程启动，而是直接等待任务产生完成之后再启动线程
				taskProducer.run();
			}

			// 输出任务总的信息
			logger.info("TaskScheduler " + getName() + " has " + taskQueue.total() + " task and left "
					+ taskQueue.left() + " to be processed.");

			// 启动任务后处理程序
			startTaskPostProcessThread();

			// 处理任务
			while (hasMoreTask())
			{
				// 如果有空闲的线程，则新建一个任务处理
				if (hasIdleThread())
				{
					Task task = popup();
					if (task != null)
					{
						startNewTaskThread(task);
					}
				}

				// 线程等待时间
				ThreadUtil.sleep(interval, randTimeSeed);

				// 如果设置停止标志，则中断执行线程
				if (isStopped())
				{
					logger.info(
							"The TaskProcuder " + taskProducer.getName() + " has been set to stopped, interupped now.");
					return;
				}
			}
			finish();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Error occured when process TaskProcuder " + taskProducer.getName() + ", exit now.");
		}
	}

	/**
	 * 结束任务处理调度工具
	 */
	protected void finish()
	{
		if (taskPostProcessor != null)
		{
			taskPostProcessor.finish();
		}

		logger.info("MainTashScheduler[" + getName() + "] has been finished, exit now");
	}

	/**
	 * 检测系统中是否有空闲的线程可用
	 * 
	 * @return
	 */
	protected boolean hasIdleThread()
	{
		int curRunningThreadNum = runningTaskThreads.size();
		return idleThreadInfo.hasIdleThread(curRunningThreadNum);
	}

	/**
	 * 是否还有需要处理的任务
	 * 
	 * @return 是否有任务的标志
	 */
	public boolean hasMoreTask()
	{
		return !taskQueue.isEmpty();
	}

	public TaskProducer getTaskProducer()
	{
		return taskProducer;
	}

	public void setTaskProducer(TaskProducer tasksProducer)
	{
		tasksProducer.addTaskEventListener(this);
		this.taskProducer = tasksProducer;
	}

	public TaskProcessor getTaskProcessor()
	{
		return taskProcessor;
	}

	public void setTaskProcessor(TaskProcessor taskProcessor)
	{
		taskProcessor.addTaskEventListener(this);
		this.taskProcessor = taskProcessor;
	}

	public TaskPostProcessor getTaskPostProcessor()
	{
		return taskPostProcessor;
	}

	public void setTaskPostProcessor(TaskPostProcessor taskPostProcessor)
	{
		taskPostProcessor.addTaskEventListener(this);
		this.taskPostProcessor = taskPostProcessor;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public int getRandTimeSeed()
	{
		return randTimeSeed;
	}

	public void setRandTimeSeed(int randTimeSeed)
	{
		this.randTimeSeed = randTimeSeed;
	}

	public int total()
	{
		return taskQueue.total();
	}

	public Task popup()
	{
		return taskQueue.poll();
	}

	public boolean isStopped()
	{
		return isStopped;
	}

	public void setStopped(boolean isStopped)
	{
		this.isStopped = isStopped;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 关闭任务调度器
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		runningTaskThreads.clear();
		runningTaskThreads = null;
		idleThreadInfo = null;
		taskQueue.clear();
		taskQueue = null;
		taskPostProcessor = null;
		taskProcessor = null;
		taskProducer = null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.client.task.TaskVector#add(com.loris.client.task.Task)
	 */
	@Override
	public void add(Task task)
	{
		taskQueue.add(task);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.client.task.TaskVector#remove(com.loris.client.task.Task)
	 */
	@Override
	public void remove(Task task)
	{
		taskQueue.remove(task);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.loris.client.task.TaskVector#clear()
	 */
	@Override
	public void clear()
	{
		// TODO Auto-generated method stub

	}
}
