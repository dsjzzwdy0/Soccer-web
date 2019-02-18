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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.loris.client.model.SchedulerInfo;
import com.loris.client.model.service.SchedulerInfoService;
import com.loris.common.context.ApplicationContextHelper;

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
	@Autowired
	SchedulerInfoService schedulerInfoService;
	
	/** Scheduler 的列表 */
	List<SchedulerInfo> schedulerInfos = new ArrayList<>();
	
	/** 当前的处理任务 */
	Map<SchedulerInfo, Scheduler> schedulers = new HashMap<>();
	
	/** */
	private static SchedulerFactory instance = null;
	
	/**
	 * Create a new instance of SchedulerFactory
	 */
	private SchedulerFactory()
	{
	}

	/**
	 * 获得数据列表
	 * @return
	 */
	public List<SchedulerInfo> getSchedulers()
	{
		return schedulerInfos;
	}

	/**
	 * 设置数据列表
	 * @param schedulers
	 */
	public void setSchedulers(List<SchedulerInfo> schedulers)
	{
		this.schedulerInfos = schedulers;
	}
	
	/**
	 * 获得新的配置信息
	 * @param sid Sid编号
	 * @return 
	 */
	public SchedulerInfo getInitSchedulerInfo(String sid)
	{
		for (SchedulerInfo schedulerInfo : schedulerInfos)
		{
			if(sid.equals(schedulerInfo.getSid()))
			{
				return schedulerInfo;
			}
		}
		return null;
	}
	
	/**
	 * 获得任务执行列表
	 * @param info
	 * @return
	 */
	public Scheduler getScheduler(SchedulerInfo info)
	{
		return schedulers.get(info);
	}
	
	/**
	 * 通过SID值获得任务的列表
	 * @param sid 任务唯一标识
	 * @return 处理的任务
	 */
	public Scheduler getScheduler(String sid)
	{
		for (SchedulerInfo schedulerInfo : schedulers.keySet())
		{
			if(sid.equals(schedulerInfo.getSid()))
			{
				return schedulers.get(schedulerInfo);
			}
		}
		return null;
	}
	
	/**
	 * 保存计划数据到列表中
	 * @param info
	 */
	public boolean save(SchedulerInfo info)
	{
		return schedulerInfoService.save(info);
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
	
	/**
	 * 创建任务调度管理器
	 * @param schedulerInfo 任务信息类型
	 * @return 任务调度管理器
	 */
	public static Scheduler createTaskScheduler(SchedulerInfo schedulerInfo)
	{
		return null;
	}
	
	/**
	 * Get the SchedulerFactory instance
	 * @return
	 */
	public static SchedulerFactory me()
	{
		if(instance == null)
		{
			instance = ApplicationContextHelper.getBean(SchedulerFactory.class);
		}
		return instance;
	}
}
