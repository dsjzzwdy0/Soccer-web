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

import java.io.IOException;

import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEventProducer;

/**   
 * @ClassName:  BasicTaskPlugin  
 * @Description: 插件数据基础类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class BasicTaskPlugin extends TaskEventProducer implements TaskPlugin
{
	/** 插件的名称 */
	protected String name;

	/** 是否已经初始化的标志 */
	protected boolean initialized = false;
	
	/**
	 * Create a new instance of BasicTaskPlugin
	 */
	public BasicTaskPlugin(String name)
	{
		this.name = name;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#setName(java.lang.String)
	 */
	@Override
	public void setName(String name)
	{
		this.name = name;		
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#isFit(com.loris.client.task.Task)
	 */
	@Override
	public boolean isFit(Task task)
	{
		return false;
	}
	
	/**
	 * 关闭插件
	 */
	@Override
	public void close()
	{
		initialized = false;
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#isInitialized()
	 */
	@Override
	public boolean isInitialized()
	{
		return initialized;
	}
	
	/**
	 * 设置是否已经初始化
	 * @param initialized
	 */
	public void setInitialized(boolean initialized)
	{
		this.initialized = initialized;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#intialize()
	 */
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		initialized = true;
	}
}
