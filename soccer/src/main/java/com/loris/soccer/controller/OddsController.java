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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loris.common.util.ArraysUtil;
import com.loris.common.web.BaseController;
import com.loris.common.web.wrapper.Rest;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.CompSetting;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.RecordOddsOp;
import com.loris.soccer.model.RecordOddsYp;
import com.loris.soccer.model.complex.MatchOddsList;
import com.loris.soccer.model.view.IssueMatchInfo;
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
	private static Logger logger = Logger.getLogger(OddsController.class);
	
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
		if(StringUtils.isAllBlank(type))
		{
			type = SoccerConstants.LOTTERY_BD;
		}
		
		long st = System.currentTimeMillis();		
		List<IssueMatchInfo> issueMatchs = matchService.getIssueMatchsInfo(issue, type);
		if(issueMatchs == null || issueMatchs.size() == 0)
		{
			return Rest.failure("There are no IssueMatch in database of '" + issue + "'.");
		}
		MatchOddsList matchOddsList = new MatchOddsList(setting, issueMatchs);
		matchOddsList.setIssue(issue);
		matchOddsList.setType(type);
		
		//处理赔率数据
		List<String> mids = ArraysUtil.getObjectFieldValue(issueMatchs, IssueMatchInfo.class, SoccerConstants.NAME_FIELD_MID);
		List<String> opcorpids = setting.getCorpIds(SoccerConstants.ODDS_TYPE_OP);
		if(opcorpids != null && opcorpids.size() > 0)
		{
			List<RecordOddsOp> ops = oddsService.getRecordOddsOps(mids, opcorpids);
			//logger.info("总共的欧赔数据的量为： " + ops.size());
			matchOddsList.addRecodOddsOpList(ops);
		}
		
		List<String> ypcorpids = setting.getCorpIds(SoccerConstants.ODDS_TYPE_YP);
		if(ypcorpids != null && ypcorpids.size() > 0)
		{
			List<RecordOddsYp> yps = oddsService.getRecordOddsYps(mids, ypcorpids);
			//logger.info("总共的欧赔数据的量为： " + yps.size());
			matchOddsList.addRecodOddsYpList(yps);
		}
		
		long en = System.currentTimeMillis();		
		logger.info("Load " + issueMatchs.size() + " " + type + " issue match Total spend time is " + (en - st) + " ms.");
		
		return Rest.okData(matchOddsList);
	}
	
	/**
	 * 获得配置数据
	 * @param sid 数据编号
	 * @return 数据列表
	 */
	@ResponseBody
	@RequestMapping("/getCompSetting")
	public Rest getCompSetting(String sid)
	{
		long st = System.currentTimeMillis();
		CompSetting setting = compService.getCompSetting(sid);
		long en = System.currentTimeMillis();
		logger.info("Total spend time to load CompSetting '" + sid + "' is " + (en - st) + " ms.");
		if(setting == null)
		{
			return Rest.failure("There are no CompSetting of sid='" + sid + "'");
		}
		else
		{
			return Rest.okData(setting);
		}
	}
}
