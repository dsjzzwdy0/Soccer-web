/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  JobController.java   
 * @Package com.loris.soccer.controller   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loris.common.constant.Enviroment;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.model.JobInfo;
import com.loris.common.quartz.BaseJob;
import com.loris.common.service.JobInfoService;
import com.loris.common.web.wrapper.Rest;
import com.loris.soccer.quartz.DataPluginJob;

/**   
 * @ClassName:  JobController    
 * @Description: 任务管理控制器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/job")
public class JobController
{
	private static Logger logger = Logger.getLogger(JobController.class);
	
	/** 数据组内容 */
	public final static String JOB_GROUP_NAME = "SOCCER_DATA_DOWNLOAD";
	
	/** 调度器 */
	private Scheduler scheduler;
	
	@Autowired
	JobInfoService jobInfoService;
	
	/**
	 * Create the JobController.
	 */
	public JobController()
	{
		try
		{
			initialize();
		}
		catch(SchedulerException e)
		{
			e.printStackTrace();
			logger.warn(e.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.warn(e.toString());
		}
	}
	
	/**
	 * 初始化任务控制器
	 * @throws SchedulerException
	 * @throws Exception
	 */
	protected void initialize() throws SchedulerException, Exception
	{
		if(!Enviroment.startJobScheduler) return;
		
		if(jobInfoService == null) jobInfoService = ApplicationContextHelper.getBean(JobInfoService.class);
		List<JobInfo> jobInfos = jobInfoService.list();
		if(jobInfos == null || jobInfos.size() == 0)
		{
			throw new SchedulerException("There are no job in database to be schedulered.");
		}
		
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();//可以通过SchedulerFactory创建一个Scheduler实例
        
        // 启动调度器
        scheduler.start();
        for (JobInfo jobInfo : jobInfos)
		{
        	if(!jobInfo.isEnabled())
        	{
        		continue;
        	}
			addAndStart(jobInfo);
		}
        
        /*
        JobInfo zgzcwIssueJobInfo = new JobInfo("足彩网期号数据下载", 
        		ZgzcwIssueJob.class.getName(), JOB_GROUP_NAME, "0 45 13 * * ?");
        JobInfo zgzcwOddsJobInfo = new JobInfo("足彩网赔率数据下载", 
        		ZgzcwOddsJob.class.getName(), JOB_GROUP_NAME, "0 45 11,23 * * ?");
        
        JobInfo zgzcwLeagueJobInfo = new JobInfo("足彩网联赛页面下载",
        		ZgzcwMainPageJob.class.getName(), JOB_GROUP_NAME, "0 45 18 ? * MON,TUE,THU,FRI");
        
        addAndStart(zgzcwIssueJobInfo);
        addAndStart(zgzcwOddsJobInfo);
        addAndStart(zgzcwLeagueJobInfo);*/
	}

	/**
	 * 远程控制添加任务
	 * @param jobClassName
	 * @param jobGroupName
	 * @param cronexpression
	 * @throws Exception
	 */
	@RequestMapping(value="/addjob")
	public void addjob(JobInfo info) throws Exception
	{
		addAndStart(info);
	}
	
	/**
	 * 全部的配置数据
	 * @return 数据列表
	 */
	@ResponseBody
	@RequestMapping("/jobInfos")
	public Rest getJobInfos()
	{
		List<JobInfo> jobinfos = jobInfoService.list();
		return Rest.okData(jobinfos);		
	}
	
	/**
	 * 在控制器中添加任务
	 * @param info
	 * @throws Exception
	 */
	public void addAndStart(JobInfo info) throws Exception
	{
		//addAndStart(info.getJobname(), info.getClassname(), info.getGroupname(), info.getCronexpression(), info.getClassname(), info.getGroupname());
		String jobName = info.getJobname();
		String jobClassName = info.getClassname();
		String jobGroupName = info.getGroupname();
		String cronExpression = info.getCronexpression();
		String triggerName = info.getJobname();
		String triggerGroupName = info.getGroupname();
		String data = info.getData();
		
		JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
		if(jobDetail != null)
		{
			logger.warn("The class[" + jobClassName + "] and group[" + jobGroupName + "] has been exist in Scheduler.");
		}
		
		// 构建job信息
		jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
				.withIdentity(jobName, jobGroupName).build();
		
		if(StringUtils.equals(DataPluginJob.class.getName(), jobClassName))
		{
			jobDetail.getJobDataMap().put(DataPluginJob.TYPE_JOB_PLUGIN, data);
		}
		else
		{
			if(StringUtils.isNotBlank(data))
			{
				jobDetail.getJobDataMap().put("data", data);
			}
		}
		
		// 表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(triggerName, triggerGroupName)
				.withSchedule(scheduleBuilder).build();
		
		logger.info("Add job " + jobName + "[" + jobClassName + "] to Group: " + jobGroupName + ", cron: " + cronExpression);
		try
		{
			scheduler.scheduleJob(jobDetail, trigger);
		}
		catch (SchedulerException e)
		{
			e.printStackTrace();
			logger.warn("创建定时任务失败：" + e);
		}
	}
	
	/** 
     * 功能: 移除一个任务 
     * @param jobName 
     * @param jobGroupName 
     * @param triggerName 
     * @param triggerGroupName 
     */  
    public void removeJob(String jobName, String jobGroupName,String triggerName, String triggerGroupName)
    {  
        try 
        { 
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,triggerGroupName);
            // 停止触发器  
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器  
            scheduler.unscheduleJob(triggerKey);
            // 删除任务  
            scheduler.deleteJob(JobKey.jobKey(jobName,jobGroupName));
        }
        catch (Exception e)
        {  
            e.printStackTrace();
            logger.warn("Error occured when remove job: " + jobGroupName + "->" + jobName);
        }  
    }

	/**
	 * 创建任务
	 * @param classname 类名称
	 * @return 任务
	 * @throws Exception 类创建时的异常
	 */
	public BaseJob getClass(String classname) throws Exception
	{
		Class<?> class1 = Class.forName(classname);
		return (BaseJob) class1.newInstance();
	}
}
