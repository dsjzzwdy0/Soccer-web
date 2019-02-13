/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  SchedulerFactory.java   
 * @Package com.loris.client.scheduler   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.loris.client.model.SchedulerInfo;
import com.loris.client.model.service.SchedulerInfoService;

/**   
 * @ClassName:  SchedulerFactory    
 * @Description: 任务调度器的工厂类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public class SchedulerFactory
{
	/** Scheduler 的列表 */
	private List<SchedulerInfo> schedulers = new ArrayList<>();
	
	@Autowired
	SchedulerInfoService schedulerInfoService;

	/**
	 * 获得数据列表
	 * @return
	 */
	public List<SchedulerInfo> getSchedulers()
	{
		return schedulers;
	}

	/**
	 * 设置数据列表
	 * @param schedulers
	 */
	public void setSchedulers(List<SchedulerInfo> schedulers)
	{
		this.schedulers = schedulers;
	}
	
	/**
	 * 获得新的配置信息
	 * @param sid Sid编号
	 * @return 
	 */
	public SchedulerInfo getNewSchedulerInfo(String sid)
	{
		for (SchedulerInfo schedulerInfo : schedulers)
		{
			if(sid.equals(schedulerInfo.getSid()))
			{
				return schedulerInfo;
			}
		}
		return null;
	}
	
	/**
	 * 保存计划数据到列表中
	 * @param info
	 */
	public void save(SchedulerInfo info)
	{
		schedulerInfoService.save(info);
	}
	
	/**
	 * 启动任务管理器
	 * @param scheduler
	 */
	public static void startTaskScheduler(Scheduler scheduler)
	{
		Thread thread = new Thread(scheduler);
		thread.start();
	}
}
