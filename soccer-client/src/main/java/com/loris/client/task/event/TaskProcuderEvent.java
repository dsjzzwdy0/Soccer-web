package com.loris.client.task.event;

import com.loris.client.task.TasksProducer;

public class TaskProcuderEvent
{
	public static enum TaskProducerEventType
	{
		Add,
		Remove,
		Finished
	}
	
	protected TasksProducer producer;
	protected TaskProducerEventType type;
	
	public TaskProcuderEvent(TasksProducer producer, TaskProducerEventType type)
	{
		this.producer = producer;
		this.type = type;
	}

	public TasksProducer getProducer()
	{
		return producer;
	}

	public void setProducer(TasksProducer producer)
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
