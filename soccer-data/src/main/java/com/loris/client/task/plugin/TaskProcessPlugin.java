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

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;

/**   
 * @ClassName:  TaskProcessPlugin  
 * @Description: 执行任务插件  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface TaskProcessPlugin extends TaskPlugin
{
	/**
	 * 执行任务
	 * @param task
	 */
	boolean execute(TaskPluginContext context, Task task) throws UrlFetchException, 
		WebParserException, IOException, HostForbiddenException, SQLException;
}
