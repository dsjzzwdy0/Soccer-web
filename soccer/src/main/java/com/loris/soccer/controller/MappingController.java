/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MappingController.java   
 * @Package com.loris.soccer.controllercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.loris.common.web.BaseController;
import com.loris.common.web.wrapper.PageWrapper;
import com.loris.common.web.wrapper.Pagination;
import com.loris.soccer.model.League;
import com.loris.soccer.model.mapping.LeagueMapping;
import com.loris.soccer.model.mapping.MatchMapping;
import com.loris.soccer.model.mapping.TeamMapping;
import com.loris.soccer.service.MappingService;

/**   
 * @ClassName:  MappingController.java   
 * @Description: 数据映射处理控制器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
@Controller
@RequestMapping("/mapping")
public class MappingController extends BaseController
{
	//private static Logger logger = Logger.getLogger(MappingController.class);
	
	static final String[][] MAPPING_TYPES = {
			{"league", "联赛映射"},
			{"team", "球队映射"},
			{"match", "比赛映射"}
	};

	@Autowired
	private MappingService mappingService;
	
	/**
	 * 映射数据处理的首页
	 * @return 视图
	 */
	@RequestMapping("index")
	public ModelAndView index()
	{
		ModelAndView view = new ModelAndView("mapping/mapping");
		return view;
	}
	
	/**
	 * 映射数据处理的首页
	 * @return 视图
	 */
	@RequestMapping("/template")
	public ModelAndView template()
	{
		ModelAndView view = new ModelAndView("mapping/template");
		return view;
	}
	
	/**
	 * 联赛数据映射
	 * @return 视图
	 */
	@RequestMapping("/league")
	public ModelAndView league()
	{
		ModelAndView view = new ModelAndView("league.mapping");
		return view;
	}
	
	/**
	 * 添加联赛映射数据
	 * @return 视图
	 */
	@RequestMapping("/addLeagueMapping")
	public ModelAndView addLeagueMapping()
	{
		ModelAndView view = new ModelAndView("league_add.mapping");
		return view;
	}
	
	/**
	 * 球队数据映射
	 * @return 视图
	 */
	@RequestMapping("/team")
	public ModelAndView team()
	{
		ModelAndView view = new ModelAndView("team.mapping");
		return view;
	}
	
	/**
	 * 球队数据映射
	 * @return 视图
	 */
	@RequestMapping("/match")
	public ModelAndView match()
	{
		ModelAndView view = new ModelAndView("match.mapping");
		return view;
	}
	
	/**
	 * 数据映射检查工具类
	 * @param type 页面类型
	 * @return 页面
	 */
	@RequestMapping("/check")
	public ModelAndView checkMapping(String type)
	{
		int index = getMappingTypeIndex(type);
		ModelAndView view = new ModelAndView(MAPPING_TYPES[index][0] + ".mapping");
		return view;
	}
	
	/**
	 * 获得所有的联赛数据
	 * @param type 类型: okooo、zgzcw/ all
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLeagues")
	public Map<String, List<? extends League>> getLeagues(String type)
	{
		return null;
	}
	
	/**
	 * 查询联赛映射数据列表
	 * @param page 分页信息
	 * @return 数据页面
	 */
	@ResponseBody
	@RequestMapping("/getLeaguesMappings")
	public PageWrapper<LeagueMapping> getLeagueMappings(Pagination page)
	{
		//logger.info("Page informatino: " + page);
		return new PageWrapper<>(mappingService.getLeagueMappings(page));
	}
	
	/**
	 * 获得球队数据的映射
	 * @param pagination 分页数据表
	 * @return 数据集
	 */
	@ResponseBody
	@RequestMapping("/getTeamsMappings")
	public PageWrapper<TeamMapping> getTeamMappings(Pagination pagination)
	{
		return new PageWrapper<>(mappingService.getTeamMappings(pagination));
	}
	
	/**
	 * 获得球队数据的映射
	 * @param pagination 分页数据表
	 * @return 数据集
	 */
	@ResponseBody
	@RequestMapping("/getMatchsMappings")
	public PageWrapper<MatchMapping> getMatchMappings(Pagination pagination)
	{
		return new PageWrapper<>(mappingService.getMatchMappings(pagination));
	}
	
	/**
	 * 查询数据类型的序列值，在没有该类型的时候，默认用第1个
	 * @param type 页面类型
	 * @return 序号值
	 */
	protected int getMappingTypeIndex(String type)
	{
		for (int i = 0; i < MAPPING_TYPES.length; i ++)
		{
			if(StringUtils.equals(MAPPING_TYPES[i][0], type))
				return i;
		}
		return 0;
	}
}
