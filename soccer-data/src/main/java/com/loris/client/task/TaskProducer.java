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

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEventProducer;
import com.loris.client.task.plugin.TaskProducePlugin;


/**
 * @ClassName: TaskProducer
 * @Description: 任务生成器
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class TaskProducer extends TaskEventProducer implements Runnable, Closeable
{
	/** 运行环境 */
	TaskPluginContext context;
	
	/** 任务产生的名称 */
	private String name;
	
	/** 是否已经停止运行 */
	private boolean finished = false;
		
	/** 任务产生的插件工具 */
	List<TaskProducePlugin> plugins = new ArrayList<>();
	
	/**
	 * Create a new instance of TaskProducer.
	 * @param context
	 */
	public TaskProducer(TaskPluginContext context)
	{
		this.context = context;
	}
	
	/**
	 * 加入插件工具
	 * @param plugin 插件
	 */
	public void addTaskProducePlugin(TaskProducePlugin plugin)
	{
		this.plugins.add(plugin);
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
	 * 执行任务管理器
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{		
		for (TaskProducePlugin taskProducePlugin : plugins)
		{
			taskProducePlugin.clearTaskEventListners();
			taskProducePlugin.addTaskEventListeners(listeners);			
			try
			{
				//开始产生新的任务
				taskProducePlugin.produce(context);
			}
			catch(SQLException e)
			{
				//Do nothing
			}
			catch (IOException e)
			{
				//Do nothing
			}
		}		
		setFinished(true);
	}

	/**
	 *  (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		for (TaskProducePlugin plugin : plugins)
		{
			plugin.close();
		}
	}
	
	/**
	 * 初始化任务的处理
	 */
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		for (TaskProducePlugin plugin : plugins)
		{
			if(!plugin.isInitialized())
			{
				plugin.initialize(context);
			}
		}
	}

	public boolean isFinished()
	{
		return finished;
	}

	protected void setFinished(boolean finished)
	{
		this.finished = finished;
	}
}
