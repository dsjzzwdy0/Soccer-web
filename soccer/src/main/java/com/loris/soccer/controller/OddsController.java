/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OddsController.java   
 * @Package com.loris.soccer.controllercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loris.common.web.BaseController;
import com.loris.common.web.wrapper.Rest;
import com.loris.soccer.model.CompSetting;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.service.CompService;
import com.loris.soccer.service.MatchService;
import com.loris.soccer.service.OddsService;
import com.loris.soccer.wrapper.OddsOpListWrapper;

/**   
 * @ClassName:  OddsController.java   
 * @Description: 赔率服务控制器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
@RequestMapping("/odds")
public class OddsController extends BaseController
{
	@Autowired
	private OddsService oddsService;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	private CompService compService;
	
	/** 欧赔数据的包装管理器 */
	private OddsOpListWrapper oddsOpListWrapper = new OddsOpListWrapper();
	
	/**
	 * 获得比赛的欧赔数据
	 * @param mid 比赛编号
	 * @return 欧赔列表数据
	 */
	@ResponseBody
	@RequestMapping("/getMatchOps")
	public Rest getMatchOddsOp(String mid)
	{
		List<OddsOp> ops = oddsService.selectOddsOp(mid);
		return Rest.okData(oddsOpListWrapper.wrap(ops));
	}
	
	/**
	 * 获得指定博彩公司比赛的欧赔数据
	 * @param mid
	 * @param corpid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMatchCorpOps")
	public Rest getMatchCorpOddsOp(String mid, String corpid)
	{
		return Rest.ok();
	}
	
	/**
	 * 获得指定期号的比赛与赔率基本信息
	 * @param issue 比赛期号
	 * @param sid 配置编号
	 * @param type 类型
	 * @return 数据列表
	 */
	@ResponseBody
	@RequestMapping("/getMatchesOdds")
	public Rest getMatchesOdds(String issue, String type, String sid)
	{
		CompSetting setting = compService.getCompSettingOrDefault(sid);
		if(setting == null)
		{
			return Rest.failure("There are no CompSetting of '" + sid + "' set or there are not default CompSetting.");
		}
		return Rest.ok();
	}
}
