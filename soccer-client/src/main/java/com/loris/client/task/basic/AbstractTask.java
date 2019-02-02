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
package com.loris.client.task.basic;

import java.util.ArrayList;
import java.util.List;

import com.loris.client.task.Task;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEventListener;
import com.loris.client.task.event.TaskEvent.TaskEventType;

/**   
 * @ClassName:  AbstractTask  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractTask implements Task
{
	/** 等待时间 (毫秒) */
	protected long waitTime = 3000;
	
	/** 任务的名称 */
	protected String name;
	
	/** 任务的优先等级 */
	protected double priority = 1;
	
	/** 任务优先等级的精度表示 */
	protected int priorityAccuracy = 1000;
		
	/** 任务消息管理器 */
	protected List<TaskEventListener> listeners = new ArrayList<>();
	
	/**
	 * 获得等待时间
	 * @return 获得等待的时间 
	 */
	public long getWaitTime()
	{
		return waitTime;
	}

	public void setWaitTime(long waitTime)
	{
		this.waitTime = waitTime;
	}
	
	@Override
	public void addTaskEventListener(TaskEventListener listener)
	{
		if(!listeners.contains(listener))
			listeners.add(listener);
	}

	@Override
	public void removeTaskEventListener(TaskEventListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * 通知消息
	 * @param event
	 */
	protected void notify(TaskEvent event)
	{
		for (TaskEventListener taskEventListener : listeners)
		{
			taskEventListener.notify(event);
		}
	}
	
	/**
	 * 获得任务的名称
	 * @return 返回任务的名称
	 */
	@Override
	public String getName()
	{
		return name;
	}
	
	/**
	 * 设置任务的名称
	 * @param name 任务名称
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * 在执行任务之前
	 */
	@Override
	public void preExecute()
	{
		notify(new TaskEvent(this, TaskEventType.Start));
	}
	
	/**
	 * 在执行任务之后
	 */
	@Override
	public void postExecute()
	{
		notify(new TaskEvent(this, TaskEventType.Finished));
	}
	
	/**
	 * 在执行任务过程中发现有问题
	 */
	@Override
	public void errExecute(Throwable e)
	{
		notify(new TaskEvent(this, TaskEventType.Error, e));
	}
	
	/**
	 * 获得任务的优先级
	 * @return 任务优先等级
	 */
	public double getPriority()
	{
		return priority;
	}
	
	/**
	 * 设置优先级
	 * @param priority 优先等级
	 */
	public void setPriority(double priority)
	{
		this.priority = priority;
	}

	/**
	 * 执行任务
	 */
	@Override
	public void run()
	{
		try
		{
			//在任务执行之前
			preExecute();
			
			//执行任务
			execute();
		}
		catch (Throwable e) 
		{
			errExecute(e);
		}
		finally 
		{
			postExecute();
		}
	}
	
	public int getPriorityAccuracy()
	{
		return priorityAccuracy;
	}

	public void setPriorityAccuracy(int priorityAccuracy)
	{
		this.priorityAccuracy = priorityAccuracy;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Task o)
	{
		return (int) (priorityAccuracy * (priority - o.getPriority()));
	}
	
	/**
	 * 任务执行过程
	 */
	abstract public void execute();
}
