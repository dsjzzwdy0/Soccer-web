package com.loris.client.scheduler.event;

import com.loris.client.scheduler.task.Task;

public interface TaskProducerEventListener<T extends Task>
{
	/**
	 * 消息通知管理器
	 * @param event 消息信息
	 */
	void notify(TaskProcuderEvent<T> event);
}
