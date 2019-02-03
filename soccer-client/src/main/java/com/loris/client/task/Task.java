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
 * @ClassName:  Task  
 * @Description: 任务工具  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface Task extends Comparable<Task>
{	
	/**
	 * 设置名称
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * 名称
	 * @return
	 */
	String getName();
	
	/**
	 * 返回任务的类型
	 * @return
	 */
	String getType();
	
	/**
	 * 获得任务的优先等级数量，值越大，将会被优先处理
	 * @return 返回任务的优先等级
	 */
	double getPriority();
	
	/**
	 * 设置任务的优先等级值
	 * @param priority 优先等级值
	 */
	void setPriority(double priority);
	
	/**
	 * 设置优先等级的精度值,如1000表示精确到小数点后三位
	 * @param priorityAccuracy 精度值
	 */
	void setPriorityAccuracy(int priorityAccuracy);
}
