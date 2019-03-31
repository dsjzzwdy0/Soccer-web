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
package com.loris.client.scheduler.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.loris.client.model.SchedulerInfo;
import com.loris.client.model.SchedulerStatus;
import com.loris.client.scheduler.Scheduler;
import com.loris.client.scheduler.TaskScheduler;
import com.loris.client.service.SchedulerInfoService;
import com.loris.client.service.SchedulerStatusService;
import com.loris.client.task.plugin.TaskPlugin;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.support.StrKit;

/**
 * @ClassName: SchedulerFactory
 * @Description: 任务调度器的工厂类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.loris.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SchedulerFactory
{
	private static Logger logger = Logger.getLogger(SchedulerFactory.class);
	
	@Autowired
	SchedulerInfoService schedulerInfoService;
	
	@Autowired
	SchedulerStatusService schedulerStatusService;

	/** Scheduler 的列表 */
	List<SchedulerInfo> schedulerInfos = new ArrayList<>();

	/** 当前的处理任务 */
	Map<SchedulerInfo, Scheduler> schedulers = new HashMap<>();

	/** */
	private static SchedulerFactory instance = null;

	/**
	 * 获得数据列表
	 * 
	 * @return
	 */
	public List<SchedulerInfo> getSchedulers()
	{
		return schedulerInfos;
	}

	/**
	 * 设置数据列表
	 * 
	 * @param schedulers
	 */
	public void setSchedulers(List<SchedulerInfo> schedulers)
	{
		this.schedulerInfos = schedulers;
	}

	/**
	 * 获得新的配置信息
	 * 
	 * @param sid
	 *            Sid编号
	 * @return
	 */
	public SchedulerInfo getInitSchedulerInfo(String sid)
	{
		for (SchedulerInfo schedulerInfo : schedulerInfos)
		{
			if (sid.equals(schedulerInfo.getId()))
			{
				return schedulerInfo;
			}
		}
		return null;
	}

	/**
	 * 获得任务执行列表
	 * 
	 * @param info
	 * @return
	 */
	public Scheduler getScheduler(SchedulerInfo info)
	{
		return schedulers.get(info);
	}

	/**
	 * 通过SID值获得任务的列表
	 * 
	 * @param sid
	 *            任务唯一标识
	 * @return 处理的任务
	 */
	public Scheduler getScheduler(String sid)
	{
		for (SchedulerInfo schedulerInfo : schedulers.keySet())
		{
			if (sid.equals(schedulerInfo.getId()))
			{
				return schedulers.get(schedulerInfo);
			}
		}
		return null;
	}

	/**
	 * 保存计划数据到列表中
	 * 
	 * @param info 计算信息
	 * @return 是否成功的标志
	 */
	public boolean save(SchedulerInfo info)
	{
		return schedulerInfoService.save(info);
	}
	
	/**
	 * 保存到数据库中
	 * @param status 计算状态信息
	 * @return 是否成功的标志
	 */
	public boolean save(SchedulerStatus status)
	{
		return schedulerStatusService.save(status);
	}

	/**
	 * 启动任务管理器
	 * 
	 * @param scheduler
	 */
	public static void startTaskScheduler(Scheduler scheduler)
	{
		Thread thread = new Thread(scheduler);
		thread.start();
	}
	
	/**
	 * 创建任务调度管理器
	 * @param info 状态配置信息
	 * @return 调度器
	 */
	public static Scheduler createTaskScheduler(SchedulerInfo info)
	{
		SchedulerStatus status = new SchedulerStatus(info);
		return createTaskScheduler(status);
	}

	/**
	 * 创建任务调度管理器
	 * 
	 * @param schedulerInfo
	 *            任务信息类型
	 * @return 任务调度管理器
	 */
	public static Scheduler createTaskScheduler(SchedulerStatus status)
	{
		TaskScheduler scheduler = new TaskScheduler(status);
		
		List<String> plugins = status.getPluginInfos();
		for (String pluginName : plugins)
		{
			logger.info(status.getName() + " add plugin: " + pluginName);
			if(pluginName.startsWith(SchedulerInfo.PLUGIN_BEAN + ":"))
			{
				try
				{
					TaskPlugin plugin = (TaskPlugin)ApplicationContextHelper.getBean(StrKit.removePrefix(pluginName, SchedulerInfo.PLUGIN_BEAN + ":"));
					scheduler.addTaskPlugin(plugin);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					logger.info(e.getStackTrace());
				}
			}
			else if(pluginName.startsWith(SchedulerInfo.PLUGIN_CLASS + ":"))
			{
				try
				{
					TaskPlugin plugin = (TaskPlugin) ApplicationContextHelper.getBean(Class.forName(StrKit.removePrefix(pluginName, SchedulerInfo.PLUGIN_CLASS + ":")));
					scheduler.addTaskPlugin(plugin);
				}
				catch (ClassNotFoundException e)
				{
					logger.info(e.getStackTrace());
				}
			}
			else 
			{
				try
				{
					TaskPlugin plugin = (TaskPlugin)ApplicationContextHelper.getBean(StrKit.removePrefix(pluginName, SchedulerInfo.PLUGIN_BEAN + ":"));
					scheduler.addTaskPlugin(plugin);
				}
				catch(Exception e)
				{
					logger.info(e.getStackTrace());
				}			
			}
		}
		return scheduler;
	}

	/**
	 * Get the SchedulerFactory instance
	 * 
	 * @return
	 */
	public static SchedulerFactory me()
	{
		if (instance == null)
		{
			instance = ApplicationContextHelper.getBean("schedulerFactory");
		}
		return instance;
	}
}
