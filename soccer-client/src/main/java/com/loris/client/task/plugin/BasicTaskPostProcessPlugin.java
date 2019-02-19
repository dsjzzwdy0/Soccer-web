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
import java.sql.SQLException;

//import org.apache.log4j.Logger;

import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.event.TaskEvent;
import com.loris.client.task.event.TaskEvent.TaskEventType;

/**   
 * @ClassName:  BasicTaskPostProcessPlugin  
 * @Description: 基础任务处理插件
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class BasicTaskPostProcessPlugin extends BasicTaskPlugin implements TaskPostProcessPlugin
{
	//private static Logger logger = Logger.getLogger(BasicTaskPostProcessPlugin.class);

	/* (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPostProcessPlugin#isFit(com.loris.client.task.Task)
	 */
	@Override
	public boolean isFit(Task task)
	{
		return true;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskPostProcessPlugin#execute(com.loris.client.task.Task)
	 */
	@Override
	public boolean execute(TaskPluginContext context, Task task) throws IOException, SQLException
	{
		//logger.info("Post Process " + task.getName());
		notifyTaskEvent(new TaskEvent(task, TaskEventType.PostProcessed));
		return true;
	}

}
