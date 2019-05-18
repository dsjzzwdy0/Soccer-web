/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  QuartzUtil.java   
 * @Package com.loris.common.quartz   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @ClassName: QuartzUtil
 * @Description: 任务应用的工具
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.loris.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class QuartzUtil
{
	private final static String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";// 任务组
	private final static String TRIGGER_GROUP_NAME = "QUARTZ_TRIGGERGROUP_NAME";// 触发器组
	private static Logger logger = Logger.getLogger(QuartzUtil.class);// 日志

	// 创建一个SchedulerFactory工厂实例
	static SchedulerFactory sf = new StdSchedulerFactory();

	/**
	 * 关闭任务调度
	 * 
	 * @param jobName
	 *            任务名称
	 * @return 0 关闭成功 1： 关闭失败 2：操作异常
	 */
	public static int closeJob(String jobName, String jobGorupName)
	{
		// 关闭任务调度
		try
		{
			Scheduler scheduler = sf.getScheduler();
			JobKey jobKey = JobKey.jobKey(jobName, jobGorupName);
			return scheduler.deleteJob(jobKey) == true ? 0 : 1;
		}
		catch (SchedulerException e)
		{
			// e.printStackTrace();
			return 2;
		}
	}

	/**
	 * 添加任务的方法
	 *
	 * @param jobName
	 *            任务名
	 * @param triggerName
	 *            触发器名
	 * @param jobClass
	 *            执行任务的类
	 * @param seconds
	 *            间隔时间
	 * @throws SchedulerException
	 */
	public static void addJob(String jobName, String triggerName, Class<? extends Job> jobClass, int seconds)
			throws SchedulerException
	{
		logger.info("==============add the Job to Scheduler==================");

		// 通过SchedulerFactory构建Scheduler对象
		Scheduler scheduler = sf.getScheduler();

		// 用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();

		// 构建一个触发器，规定触发的规则
		Trigger trigger = TriggerBuilder.newTrigger()// 创建一个新的TriggerBuilder来规范一个触发器
				.withIdentity(triggerName, TRIGGER_GROUP_NAME)// 给触发器起一个名字和组名
				.startNow()// 立即执行
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(seconds)// 时间间隔
																									// 单位：秒
						.repeatForever()// 一直执行
				// CronTrigger使用cron表达式来设置触发时间。CronTrigger创建方式：
				// CronScheduleBuilder.cronSchedule("0 53 19 ? * *")
				// [[秒] [分] [小时] [日] [月] [周] [年] 当前为每天下午7点53执行
				).build();// 产生触发器

		//trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME).startNow()
		//		.withSchedule(CronScheduleBuilder.cronSchedule("0 30,35,40 * * * ?")).build();

		// 向Scheduler中添加job任务和trigger触发器
		scheduler.scheduleJob(jobDetail, trigger);

		// 启动
		scheduler.start();
	}
}
