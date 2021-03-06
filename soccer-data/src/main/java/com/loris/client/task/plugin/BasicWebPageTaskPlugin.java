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
package com.loris.client.task.plugin;

import java.util.List;

import com.loris.client.model.WebPage;
import com.loris.client.task.Task;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEvent.TaskEventType;
import com.loris.client.task.event.TaskEventListener;

/**   
 * @ClassName:  BasicTaskProducePlugin  
 * @Description: 任务产生器的基本插件工具  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class BasicWebPageTaskPlugin extends BasicTaskPlugin implements TaskPlugin
{
	/**
	 * Create a new instance of BasicWebpageTaskPlugin
	 */
	public BasicWebPageTaskPlugin(String name)
	{
		super(name);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#removeTaskEventListner(com.loris.client.task.event.TaskEventListener)
	 */
	@Override
	public void removeTaskEventListner(TaskEventListener listener)
	{
		listeners.remove(listener);		
	}

	/**
	 * 添加任务监听器 
	 * @see com.loris.client.task.plugin.TaskProducePlugin#addTaskEventListeners(java.util.List)
	 */
	@Override
	public void addTaskEventListeners(List<TaskEventListener> listeners)
	{
		this.listeners.addAll(listeners);
	}

	/**
	 * 清除任务监听器 (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#clearTaskEventListners()
	 */
	@Override
	public void clearTaskEventListners()
	{
		listeners.clear();
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#isFit(com.loris.client.task.Task)
	 */
	@Override
	public boolean isFit(Task task)
	{
		throw new UnsupportedOperationException("This is not used for TaskProducePlugin.");
	}
	
	/**
	 * 创建任务
	 * @param page 数据页面
	 * @param quiet 是否不通知插件环境
	 * @return 待处理的任务
	 */
	protected boolean createWebPageTask(WebPage page, boolean quiet)
	{
		if(!quiet)
		{
			notifyTaskEvent(new TaskEvent(page, TaskEventType.Created));
		}
		return true;
	}
	
	/**
	 * 创建任务，该任务创建之后，将会通知插件运行环境
	 * @param page 数据页面
	 * @return 待处理的任务
	 */
	protected boolean createWebPageTask(WebPage page)
	{
		return createWebPageTask(page, false);
	}
}
