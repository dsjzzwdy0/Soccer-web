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

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;

/**   
 * @ClassName:  TaskPlugin  
 * @Description: 任务的插件类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface TaskPlugin extends Closeable
{
	/**
	 * 获得插件的名称
	 * @return 插件的名称
	 */
	String getName();
	
	/**
	 * 设置插件的名称
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * 执行任务
	 * @param task
	 */
	boolean execute(TaskPluginContext context, Task task) throws UrlFetchException, 
		WebParserException, IOException, HostForbiddenException, SQLException;
	
	/**
	 * 是否适合任务
	 * @param task
	 * @return
	 */
	boolean isFit(Task task);
	
	/**
	 * 是否已经初始化
	 * @return
	 */
	boolean isInitialized();
	
	/**
	 * 运行环境初始化
	 */
	void initialize(TaskPluginContext context) throws IOException;
}
