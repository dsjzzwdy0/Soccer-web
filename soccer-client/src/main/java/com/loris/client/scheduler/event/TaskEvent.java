package com.loris.client.scheduler.event;

import com.loris.client.scheduler.task.Task;

/**
 * Task的消息信息
 * @author deng
 *
 */
public class TaskEvent
{
	public static enum TaskEventType
	{
		Add,
		Start,
		Finished,
		Removed,
		Error
	}
	
	/** Task */
	protected Task task;
	
	/** 消息类型 */
	protected TaskEventType type;
	
	/** 异常信息 */
	protected Throwable error = null;
	
	/**
	 * TaskEvent Instance.
	 * @param task
	 */
	public TaskEvent(Task task, TaskEventType type)
	{
		this.task = task;
		this.type = type;
	}
	
	public TaskEvent(Task task, TaskEventType type, Throwable e)
	{
		this.task = task;
		this.type = type;
		this.error = e;
	}

	public Task getTask()
	{
		return task;
	}

	public void setTask(Task task)
	{
		this.task = task;
	}

	public TaskEventType getType()
	{
		return type;
	}

	public void setType(TaskEventType type)
	{
		this.type = type;
	}

	public Throwable getError()
	{
		return error;
	}

	public void setError(Throwable error)
	{
		this.error = error;
	}
}
