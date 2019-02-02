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
package com.loris.client.task.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**   
 * @ClassName:  TaskEventProducer  
 * @Description: 这是任务消息生成器，在进行任务处理的时候生成消息   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class TaskEventProducer
{
	/** 任务处理监听器 */
	protected List<TaskEventListener> listeners = new ArrayList<>();
	
	/**
	 * 初始化
	 */
	public void initialize() throws IOException
	{
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
}
