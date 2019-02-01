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
package com.loris.client.scheduler;

import java.util.List;

import org.apache.log4j.Logger;

import com.loris.client.scheduler.event.TaskEvent;
import com.loris.client.scheduler.event.TaskEvent.TaskEventType;
import com.loris.client.scheduler.event.TaskEventListener;
import com.loris.client.scheduler.task.Task;

import java.util.ArrayList;

/**   
 * @ClassName:  TaskSchedulerThread  
 * @Description: 任务调度管理器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class AbstractTaskScheduler extends Thread implements TaskEventListener
{
	/** */
	private static Logger logger = Logger.getLogger(AbstractTaskScheduler.class);
	
	/** 运行中的线程 */
	private List<Task> runningTaskThreads = new ArrayList<>();
	
	/** 最大的在运行状态的线程数 */
	private int maxActiveTaskThread = 5;
	
	/** The thread number. */
	protected int threadIndex = 1;
	
	/**
	 * Create a new instance of AbstractTaskScheduler
	 */
	public AbstractTaskScheduler()
	{
		this.setDaemon(false);
	}
	
	/**
	 * 加入运行中的线程
	 * @param thread
	 */
	public void addRunningTask(Task task)
	{
		runningTaskThreads.add(task);
	}
	
	/**
	 * 从线程中移除
	 * @param thread
	 */
	public void removeRunningTask(Task task)
	{
		runningTaskThreads.remove(task);
	}

	/**
	 * 最大的激活的线程数
	 * @return
	 */
	public int getMaxActiveTaskThread()
	{
		return maxActiveTaskThread;
	}

	/**
	 * 设置最大的在运行的线程数
	 * @param maxActiveTaskThread
	 */
	public void setMaxActiveTaskThread(int maxActiveTaskThread)
	{
		this.maxActiveTaskThread = maxActiveTaskThread;
	}
	
	/**
	 * 开始一个新的运务运行
	 * @param task 任务
	 */
	public void startNewTaskThread(Task task) 
	{
		int curRunningThreadNum = runningTaskThreads.size();
		if(curRunningThreadNum >= maxActiveTaskThread)
		{
			logger.info("The running theads number is " + curRunningThreadNum 
					+ " and is upto max thread number " + maxActiveTaskThread 
					+ ", waiting for next idle thread.");
			return;
		}
		
		//启动线程
		Thread thread = new Thread(task);
		thread.setDaemon(false);

		//设置监听器
		task.addTaskEventListener(this);
		thread.start();	
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.scheduler.event.TaskEventListener#notify(com.loris.client.scheduler.event.TaskEvent)
	 */
	@Override
	public void notify(TaskEvent event)
	{
		TaskEvent.TaskEventType taskType = event.getType();
		if(taskType == TaskEventType.Start)
		{
			logger.info("Starting to excute " + event.getTask().getName());
			addRunningTask(event.getTask());
		}
		else if(taskType == TaskEventType.Finished)
		{
			removeRunningTask(event.getTask());
			logger.info("Finished to excute " + event.getTask().getName());
		}
	}
	
	/**
	 * 运行线程
	 */
	@Override
	public void run()
	{
		
	}
}
