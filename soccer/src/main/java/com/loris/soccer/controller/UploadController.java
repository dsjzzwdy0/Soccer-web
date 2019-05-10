/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  DataController.java   
 * @Package com.loris.soccer.controller   
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loris.client.exception.DataRecieveException;
import com.loris.client.sender.DataReciever;
import com.loris.common.web.BaseController;
import com.loris.common.web.wrapper.Rest;

/**   
 * @ClassName:  DataController    
 * @Description: 数据控制器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController
{
	//private static Logger logger = Logger.getLogger(UploadController.class);
	
	/** 数据接收处理器 */
	@Autowired
	private DataReciever dataReciever;
	
	/**
	 * 上传数据记录表
	 * @param records
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadRecords")
	public Rest uploadRecords(String records)
	{
		return processRecievedData(records);
	}
	
	/**
	 * 上传JSON数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadJson")
	public Rest uploadJson(@RequestBody String request)
	{
		return processRecievedData(request);
	}
	
	/**
	 * 处理数据类
	 * @param content
	 * @return
	 */
	private Rest processRecievedData(String content)
	{
		//logger.info(content);
		try
		{
			if(dataReciever.recieve(content))
			{
				return Rest.ok();
			}
			else
			{
				return Rest.failure("Failure to process upload records.");
			}
		}
		catch (DataRecieveException e) 
		{
			e.printStackTrace();
			return Rest.failure(e.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return Rest.failure(e.toString());
		}
	}
}
