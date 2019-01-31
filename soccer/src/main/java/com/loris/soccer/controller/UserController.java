/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  UserController.java   
 * @Package com.loris.soccer.controller   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:38:55   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loris.soccer.model.OddsOp;
import com.loris.soccer.service.OddsOpService;
import com.loris.soccer.web.wrapper.Rest;


/**   
 * @ClassName:  UserController   
 * @Description: 用户信息管理控制器类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:38:55   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private OddsOpService oddsOpService;
	
	/**
	 * 主页登录
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("user", "no");
		return "login";
	}
	
	@ResponseBody
	@RequestMapping("/submit")
	public Rest submitOddsOp()
	{
		OddsOp op = new OddsOp();
		op.setWinodds(3.1f);
		op.setDrawodds(2.3f);
		op.setLoseodds(1.4f);
		
		oddsOpService.save(op);
		return Rest.okData(op);
	}
}
