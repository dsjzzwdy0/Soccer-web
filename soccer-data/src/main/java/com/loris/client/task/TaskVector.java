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

/**   
 * @ClassName:  TaskStack  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface TaskVector
{
	/**
	 * 添加任务
	 * @param task
	 */
	void add(Task task);
	
	/**
	 * 移除任务
	 * @param task
	 */
	void remove(Task task);
	
	/**
	 * 通过名称来删除任务
	 * @param name
	 */
	void removeTask(String name);
	
	/**
	 * 删除某一类任务
	 * @param type
	 */
	void removeTaskByType(String type);
	
	/**
	 * 清除记录
	 */
	void clear();
}
