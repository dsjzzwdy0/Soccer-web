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

import com.loris.client.task.event.TaskEvent;

/**   
 * @ClassName:  TaskPlugin  
 * @Description: 任务的插件类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface TaskPlugin extends Runnable
{
	/**
	 * 获得插件的名称
	 * @return 插件的名称
	 */
	String getName();
	
	/**
	 * 运行插件工具
	 */
	void run();
	
	/**
	 * 通知任务任务处理的监听器
	 * @param event 任务监听器
	 */
	void notifyTaskEvent(TaskEvent event);
}
