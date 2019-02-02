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

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEventProducer;

/**   
 * @ClassName:  BasicTaskPlugin  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class BasicTaskPlugin extends TaskEventProducer implements TaskPlugin
{
	/** 插件的名称 */
	protected String name;

	/* (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#setName(java.lang.String)
	 */
	@Override
	public void setName(String name)
	{
		this.name = name;		
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#execute(com.loris.client.task.Task)
	 */
	@Override
	public boolean execute(TaskPluginContext context, Task task) throws UrlFetchException, WebParserException, IOException, HostForbiddenException
	{
		//Do nothing.
		return true;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#isFit(com.loris.client.task.Task)
	 */
	@Override
	public boolean isFit(Task task)
	{
		return true;
	}
	
	@Override
	public void close()
	{		
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPlugin#intialize()
	 */
	@Override
	public void intialize()  throws IOException
	{		
	}

}
