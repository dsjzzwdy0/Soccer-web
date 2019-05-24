/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @CompServiceImpl.java   
 * @Package com.loris.soccer.service.implcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.common.constant.Enviroment;
import com.loris.common.filter.ObjectFilter;
import com.loris.common.service.SqlHelper;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.CasinoCompMapper;
import com.loris.soccer.dao.CompSettingMapper;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.CompSetting;
import com.loris.soccer.service.CompService;

/**   
 * @ClassName:  CompServiceImpl.java   
 * @Description: 博彩公司服务类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("compService")
public class CompServiceImpl extends ServiceImpl<CompSettingMapper, CompSetting> implements CompService
{
	@Autowired
	private SqlHelper sqlHelper;
	
	@Autowired
	private CasinoCompMapper casinoCompMapper;
	
	/** 默认的公司配置 */
	private CompSetting defaultSetting;

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.CompService#insertCasinoComps(java.util.List)
	 */
	@Override
	public boolean insertCasinoComps(List<CasinoComp> comps)
	{
		return insertCasinoComps(comps, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.CompService#insertCasinoComps(java.util.List, boolean)
	 */
	@Override
	public boolean insertCasinoComps(List<CasinoComp> comps, boolean overwrite)
	{
		ObjectFilter<CasinoComp> filter = new ObjectFilter<>();
		return SqlHelper.insertList(comps, CasinoComp.class, casinoCompMapper, filter, 
				SoccerConstants.NAME_FIELD_CORPID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.CompService#getCasinoComps()
	 */
	@Override
	public List<CasinoComp> getCasinoComps()
	{
		return casinoCompMapper.selectList(new QueryWrapper<CasinoComp>());
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.CompService#getCasinoComps(java.lang.String)
	 */
	@Override
	public List<CasinoComp> getCasinoComps(String type)
	{
		QueryWrapper<CasinoComp> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotEmpty(type))
		{
			queryWrapper.eq("type", type);
		}
		return casinoCompMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.CompService#getCompSetting(java.lang.String)
	 */
	@Override
	public CompSetting getCompSetting(String sid)
	{
		QueryWrapper<CompSetting> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("sid", sid);
		return baseMapper.selectOne(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.CompService#getDefaultSetting()
	 */
	@Override
	public CompSetting getDefaultSetting()
	{
		if(defaultSetting == null)
		{
			defaultSetting = baseMapper.selectOne(new QueryWrapper<CompSetting>().eq("sid", Enviroment.defaultCompSettingId));
		}
		return defaultSetting;
	}

	/**
	 * (non-Javadoc)
	 * @see com.loris.soccer.service.CompService#getCompSettingOrDefault()
	 */
	@Override
	public CompSetting getCompSettingOrDefault(String sid)
	{
		CompSetting setting = getCompSetting(sid);
		if(setting == null)
		{
			setting = getDefaultSetting();
		}
		return setting;
	}

}
