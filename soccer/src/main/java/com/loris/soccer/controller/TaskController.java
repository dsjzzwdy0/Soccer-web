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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loris.client.model.SchedulerInfo;
import com.loris.client.scheduler.SchedulerFactory;
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
	//private static Logger logger = Logger.getLogger(TaskController.class);
	
	@Autowired
	SchedulerFactory schedulerFactory;	
	
	@ResponseBody
	@RequestMapping("/listSchedulers")
	public Rest getSchedulerInfoList(HttpServletRequest request)
	{
		//logger.info(getCurrentIp(request));
		return Rest.okData(schedulerFactory.getSchedulers());
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
}
