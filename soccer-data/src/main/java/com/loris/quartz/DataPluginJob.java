/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  DataPluginJob.java   
 * @Package com.loris.soccer.quartz   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.quartz;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.loris.client.model.SchedulerPlugins;
import com.loris.client.scheduler.Scheduler;
import com.loris.client.scheduler.factory.SchedulerFactory;
import com.loris.client.service.SchedulerInfoService;
import com.loris.common.context.ApplicationContextHelper;

/**
 * @ClassName: DataPluginJob
 * @Description: 数据处理的任务工作
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class DataPluginJob extends BaseJob
{
	final static public String TYPE_JOB_PLUGIN = "typeofjobplugin";
	
	private static Logger logger = Logger.getLogger(DataPluginJob.class);
	
	/**
	 *  (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String type = dataMap.getString(TYPE_JOB_PLUGIN);
		if(StringUtils.isBlank(type))
		{
			logger.warn("The type of job plugin is not set, there are nothing to do, DataPluginJob exit.");
			return;
		}
		
		SchedulerInfoService schedulerInfoService = ApplicationContextHelper.getBean(SchedulerInfoService.class);
		SchedulerPlugins schedulerInfo = schedulerInfoService.getSchedulerInfo(type);
		
		if(schedulerInfo == null)
		{
			logger.warn("The type '" + type + "' of job plugin is not defined, DataPluginJob exit.");
			return;
		}
		
		logger.info("Starting to execute [" + schedulerInfo.getName() + "] ...");		
		Scheduler scheduler = SchedulerFactory.createTaskScheduler(schedulerInfo);
		SchedulerFactory.startTaskScheduler(scheduler);
	}
}
