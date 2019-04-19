/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  DataDownloadJob.java   
 * @Package com.loris.soccer.quartz   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.loris.client.model.SchedulerInfo;
import com.loris.client.scheduler.Scheduler;
import com.loris.client.scheduler.factory.SchedulerFactory;
import com.loris.common.quartz.BaseJob;

/**   
 * @ClassName:  DataDownloadJob    
 * @Description: 数据下载的任务 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class DataJob extends BaseJob
{
	private static Logger logger = Logger.getLogger(DataJob.class);
		
	/**
	 * 创建任务信息类
	 */
	protected abstract SchedulerInfo createSchedulerInfo();

	/**
	 *  (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		SchedulerInfo info = createSchedulerInfo();
		
		if(info == null)
			throw new JobExecutionException("The SchedulerInfo object is not initialized, error.");
		
		logger.info("Starting to execute [" + info.getName() + "] ...");		
		Scheduler scheduler = SchedulerFactory.createTaskScheduler(info);
		SchedulerFactory.startTaskScheduler(scheduler);
	}

}
