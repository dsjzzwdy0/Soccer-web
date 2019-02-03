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
package com.loris.client.task.basic;

import com.loris.client.task.Task;

/**   
 * @ClassName:  AbstractTask  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractTask implements Task
{
	/** 任务的名称 */
	protected String name;
	
	/** 任务的类型 */
	protected String type;
	
	/** 任务的优先等级 */
	protected double priority = 1;
	
	/** 任务优先等级的精度表示 */
	protected int priorityAccuracy = 1000;
		
	/**
	 * 获得任务的名称
	 * @return 返回任务的名称
	 */
	@Override
	public String getName()
	{
		return name;
	}
	
	/**
	 * 设置任务的名称
	 * @param name 任务名称
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * 获得任务的优先级
	 * @return 任务优先等级
	 */
	public double getPriority()
	{
		return priority;
	}
	
	/**
	 * 设置优先级
	 * @param priority 优先等级
	 */
	public void setPriority(double priority)
	{
		this.priority = priority;
	}
	
	public int getPriorityAccuracy()
	{
		return priorityAccuracy;
	}

	/**
	 * 设置优先等级的精度值, 精确到小数点后的位数, 默认为3位。
	 * @param priorityAccuracy 精度值
	 */
	@Override
	public void setPriorityAccuracy(int priorityAccuracy)
	{
		this.priorityAccuracy = priorityAccuracy;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Task o)
	{
		return (int) (priorityAccuracy * (o.getPriority() - priority ));
	}
}
