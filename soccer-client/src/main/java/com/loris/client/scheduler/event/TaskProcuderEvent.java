package com.loris.client.scheduler.event;

import com.loris.client.scheduler.TaskProducer;
import com.loris.client.scheduler.task.Task;

public class TaskProcuderEvent<T extends Task>
{
	public static enum TaskProducerEventType
	{
		Add,
		Remove,
		Finished
	}
	
	protected TaskProducer<T> producer;
	protected TaskProducerEventType type;
	
	public TaskProcuderEvent(TaskProducer<T> producer, TaskProducerEventType type)
	{
		this.producer = producer;
		this.type = type;
	}

	public TaskProducer<T> getProducer()
	{
		return producer;
	}

	public void setProducer(TaskProducer<T> producer)
	{
		this.producer = producer;
	}

	public TaskProducerEventType getType()
	{
		return type;
	}

	public void setType(TaskProducerEventType type)
	{
		this.type = type;
	}
}
