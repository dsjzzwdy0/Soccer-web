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
package com.loris.client.scheduler;

import java.io.Closeable;

import com.loris.client.scheduler.status.SchedulerStatus;
import com.loris.client.task.plugin.TaskPlugin;

/**   
 * @ClassName:  TaskScheduler  
 * @Description: 任务调度器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface Scheduler extends Runnable, Closeable
{
	/**
	 * 加入任务处理插件工具
	 * @param plugin
	 */
	void addTaskPlugin(TaskPlugin plugin);
	
	/**
	 * 查询当前的状态信息
	 * @return 状态信息
	 */
	SchedulerStatus getSchedulerStatus();
}
