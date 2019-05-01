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
package com.loris.soccer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loris.client.model.SchedulerInfo;
import com.loris.client.model.SchedulerStatus;
import com.loris.client.scheduler.factory.SchedulerFactory;
import com.loris.client.service.SchedulerInfoService;
import com.loris.common.exception.ParamsException;
import com.loris.common.pagination.PageInfo;
import com.loris.common.web.BaseController;
import com.loris.common.web.wrapper.Rest;

/**   
 * @ClassName:  League   
 * @Description: 任务管理器接口  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController
{
	private static Logger logger = Logger.getLogger(TaskController.class);
	
	@Autowired
	private SchedulerInfoService schedulerInfoService;
	

	@ResponseBody
	@RequestMapping("/listSchedulers")
	public Rest getSchedulerInfoList(HttpServletRequest request)
	{
		List<SchedulerInfo> schedulerInfos = schedulerInfoService.list();
		return Rest.okData(schedulerInfos);
	}
	
	/**
	 * 添加调度计划任务
	 * @param info 计划任务
	 * @return 返回结果值
	 */
	@ResponseBody
	@RequestMapping("/addScheduler")
	public Rest addSchedulerInfo(@Validated SchedulerInfo info, BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			return Rest.failure(errorInfos(bindingResult).toString());
		}
		return Rest.ok();
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Rest list(@Validated PageInfo pageInfo, BindingResult bindingResult)
	{
		//logger.info("index=" + pageInfo.getIndex() + ", pernum=" + pageInfo.getPernum());
		if(bindingResult.hasErrors())
		{
			//return Rest.failureData(errorInfos(bindingResult));
			throw new ParamsException(errorInfos(bindingResult));
		}	
		return Rest.okData(pageInfo);
	}
	
	/**
	 * 通过SID创建任务
	 * @param sid 任务调度编号
	 * @return 返回信息状态
	 */
	@ResponseBody
	@RequestMapping("/create")
	public Rest create(String sid)
	{
		SchedulerFactory schedulerFactory = SchedulerFactory.me();
		SchedulerInfo schedulerInfo = schedulerFactory.getInitSchedulerInfo(sid);
		if(schedulerInfo != null)
		{
			schedulerFactory.save(schedulerInfo);
			return Rest.okData(schedulerInfo);
		}
		else
		{
			return Rest.failure("There are no SchedulerInfo of sid='" + sid + "'");
		}
	}
	
	/**
	 * 获得配置的内容
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getConf")
	public Rest getConf(String sid)
	{
		SchedulerFactory schedulerFactory = SchedulerFactory.me();
		SchedulerInfo schedulerInfo = schedulerFactory.getInitSchedulerInfo(sid);
		if(schedulerInfo != null)
		{
			return Rest.okData(schedulerInfo.wrapToWebElements());
		}
		else
		{
			return Rest.failure("There are no SchedulerInfo of sid='" + sid + "'");
		}
	}
	
	@ResponseBody
	@RequestMapping("/createScheduler")
	public Rest createTaskScheduler(@Validated SchedulerStatus status, BindingResult bindingResult)
	{
		logger.info(status.getSid() + "," + status.getName() + ", " + status.getIntervaltime() + ", " +
				status.getMaxActiveTaskThread() + ", " + status.getPlugins() + ", " + status.getType());
		if(bindingResult.hasErrors())
		{
			logger.info(bindingResult.getAllErrors());
		}
		return Rest.ok();
	}
	
	/**
	 * 数据下载与任务创建
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping("/download")
	public String download(String type, ModelAndView model)
	{
		return "/soccer/download";
	}
}
