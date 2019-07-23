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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loris.common.web.BaseController;
import com.loris.common.web.wrapper.PageWrapper;
import com.loris.soccer.model.mapping.LeagueMapping;
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
	static final String[][] MAPPING_TYPES = {
			{"league", "联赛映射"},
			{"team", "球队映射"},
			{"match", "比赛映射"}
	};

	@Autowired
	private MappingService mappingService;
	
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
	 * 查询联赛映射数据列表
	 * @param page 分页信息
	 * @return 数据页面
	 */
	@ResponseBody
	@RequestMapping("/getLeagues")
	public PageWrapper<LeagueMapping> getLeagueMappings(Page<LeagueMapping> page)
	{
		return new PageWrapper<>(mappingService.getLeagueMapping(page));
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
