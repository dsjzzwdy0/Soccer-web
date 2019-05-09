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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class UploadController
{
	private static Logger logger = Logger.getLogger(UploadController.class);
	
	/**
	 * 上传数据记录表
	 * @param records
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadRecords")
	public Rest uploadRecords(String records)
	{
		logger.info(records);
		return Rest.ok();
	}
}
