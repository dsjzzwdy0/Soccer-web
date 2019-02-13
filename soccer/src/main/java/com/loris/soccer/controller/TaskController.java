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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loris.client.scheduler.SchedulerFactory;
import com.loris.common.page.PageInfo;
import com.loris.common.wrapper.Rest;

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
public class TaskController
{
	//private static Logger logger = Logger.getLogger(TaskController.class);
	@Autowired
	SchedulerFactory schedulerFactory;
	
	@ResponseBody
	@RequestMapping("/listSchedulers")
	public Rest getSchedulerInfoList()
	{
		return Rest.okData(schedulerFactory.getSchedulers());
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Rest list(PageInfo pageInfo)
	{
		//logger.info("index=" + pageInfo.getIndex() + ", pernum=" + pageInfo.getPernum());
		return Rest.okData(pageInfo);
	}
}
