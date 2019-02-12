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

import org.apache.log4j.Logger;

import com.loris.client.scheduler.MainTaskScheduler;

/**   
 * @ClassName:  BusyInfo  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class IdleThreadInfo
{
	private static Logger logger = Logger.getLogger(IdleThreadInfo.class);
	
	int currentBusyTimes = 0;
	int maxActiveThreadNum;
	
	MainTaskScheduler scheduler;
	
	/** 最大的繁忙线程处理器 */
	int maxBusyTimes = 10;
	
	/**
	 * 创建一个线程信息处理器
	 * @param maxActiveThreadNum
	 */
	public IdleThreadInfo(MainTaskScheduler scheduler, int maxActiveThreadNum)
	{
		this.scheduler = scheduler;
		this.maxActiveThreadNum = maxActiveThreadNum;
	}
	
	/**
	 * 检测是否有空闲的线程可用于信息处理 
	 * @param maxActiveThread 最大可同时使用的线程
	 * @param currentActiveThread 当前最大的线程
	 * @return 是否使用的标志
	 */
	public boolean hasIdleThread(int currentActiveThread)
	{
		if(currentActiveThread < maxActiveThreadNum)
		{
			currentBusyTimes = 0;
			return true;
		}
		else
		{
			currentBusyTimes ++;
			if(currentBusyTimes % maxBusyTimes == 0)
			{
				//信息输出，等待太长时间之后需要告诉用户的基本信息
				logger.info("The TaskScheduler[" + scheduler.getName() + "] running theads number is " 
						+ currentActiveThread + " and is upto max thread number "
						+ maxActiveThreadNum + ", waiting for next idle thread.");
			}
			return false;
		}
	}

	public int getMaxActiveThreadNum()
	{
		return maxActiveThreadNum;
	}

	public void setMaxActiveThreadNum(int maxActiveThreadNum)
	{
		this.maxActiveThreadNum = maxActiveThreadNum;
	}
}
