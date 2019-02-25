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
package com.loris.client.task.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import com.loris.client.task.Task;

/**   
 * @ClassName:  TaskQueue  
 * @Description: 任务队列数据，用于管理任务列表，任务优先级值越大，则优先被处理  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TaskQueue extends PriorityQueue<Task>
{
	/***/
	private static final long serialVersionUID = 1L;
	
	/** 用于统计总的任务数的类 */
	private int total = 0;
	
	/** 最大的优先级有数据  */
	private double minPriority = Double.MAX_VALUE;
	
	/** 任务出错最大处理次数 */
	public static int maxProcessTimes = 3;
	
	/** 出错的任务处理队列 */
	protected static Map<Task, Integer> errorTasks = new HashMap<>();
	
	/**
	 * 加入任务数据
	 * @param task 任务对象
	 * @return 返回是否加入成功的标志 
	 */
	@Override
	public boolean add(Task task)
	{
		synchronized(this)
		{
			if(contains(task))
			{
				return false;
			}
			
			if(super.add(task))
			{
				total ++;
				if(minPriority > task.getPriority())
				{
					minPriority = task.getPriority();
				}
				return true;
			}
			return false;
		}
	}
	
	/**
	 * 将任务推回到任务列表中，为了提高系统的性能，如果推回的次数大于某一阈值
	 * 则废弃该任务，不再进行处理
	 * @param task 任务
	 * @return 是否加入成功的标志
	 */
	public boolean pushBack(Task task)
	{
		Integer time = errorTasks.get(task);
		if(time == null)
		{
			errorTasks.put(task, 1);
		}
		else
		{
			time ++;
			if(time >= maxProcessTimes)
			{
				return false;
			}
			
			errorTasks.put(task, time);
		}		
		task.setPriority(minPriority - 1.0);
		return super.add(task);
	}
	
	/**
	 * 总的任务数
	 * @return 总数
	 */
	public int total()
	{
		return total;
	}
	
	/**
	 * 剩余的任务数
	 * @return 剩余数
	 */
	public int left()
	{
		return this.size();
	}
	
	/**
	 * 通过名称删除元素
	 * @param name
	 */
	public void removeByName(String name)
	{
		Iterator<Task> it = this.iterator();
		while (it.hasNext())
		{
			Task task = it.next();
			if (name.equals(task.getName()))
			{
				it.remove();
			}
		}
	}
	
	/**
	 * 通过名称删除元素
	 * @param name
	 */
	public void removeByType(String type)
	{
		Iterator<Task> it = this.iterator();
		while (it.hasNext())
		{
			Task task = it.next();
			if (type.equals(task.getType()))
			{
				it.remove();
			}
		}
	}
	
	/**
	 * 错误任务的清空
	 */
	public static void clearErrorTasks()
	{
		errorTasks.clear();
	}
}
