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
package com.loris.client.task.context;

import com.loris.client.task.MainTaskScheduler;
import com.loris.client.task.TaskPostProcessor;
import com.loris.client.task.TaskProcessor;
import com.loris.client.task.TaskProducer;

/**   
 * @ClassName:  TaskPluginContext  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface TaskPluginContext 
{
	/**
	 * 获得任务的主运行环境参数
	 * @return 主运行环境
	 */
	MainTaskScheduler getMainTaskScheduler();
	
	/**
	 * 获得任务的运行环境
	 * @return 执行环境
	 */
	TaskProcessor getTaskProcessor();
	
	/**
	 * 获得任务的后处理环境
	 * @return
	 */
	TaskPostProcessor getTaskPostProcessor();
	
	/**
	 * 获得任务的产生环境
	 * @return
	 */
	TaskProducer getTaskProducer();
}
