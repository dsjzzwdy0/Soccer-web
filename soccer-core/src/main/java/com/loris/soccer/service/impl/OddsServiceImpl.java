/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsOpServiceImpl.java   
 * @Package com.loris.soccer.service.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:33:53   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loris.common.service.SqlHelper;
import com.loris.common.util.ArraysUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.OddsOpMapper;
import com.loris.soccer.filter.OddsValueFilter;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.service.OddsService;

/**   
 * @ClassName:  OddsOpServiceImpl   
 * @Description: 更新数据时使用 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:33:53   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("oddsOpService")
public class OddsServiceImpl implements OddsService
{
	private static Logger logger = Logger.getLogger(OddsServiceImpl.class);
	
	@Autowired
	private SqlHelper sqlHelper;
	
	@Autowired
	private OddsOpMapper oddsOpMapper;
	
	/**
	 * 通过比赛编号获得欧赔数据列表
	 * @param mid 比赛编号
	 * @return 欧赔数据列表
	 */
	@Override
	@Cacheable(value=SoccerConstants.CAHE_ODDS_NAME, key="#mid")
	public List<OddsOp> selectOddsOp(String mid)
	{
		QueryWrapper<OddsOp> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mid", mid);
		return oddsOpMapper.selectList(queryWrapper);
	}
	
	/**
	 * 通过比赛编号与博彩公司的编号获得欧赔数据列表
	 * @param mid 比赛编号
	 * @param corpid 博彩公司编号
	 * @return 欧赔数据列且
	 */
	@Override
	@Cacheable(value=SoccerConstants.CAHE_ODDS_NAME, key="#mid,#corpid")
	public List<OddsOp> selectOddsOp(String mid, String corpid)
	{
		QueryWrapper<OddsOp> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mid", mid).eq("corpid", corpid);
		return oddsOpMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#updateOpList()
	 */
	@Override
	@CacheEvict(value=SoccerConstants.CAHE_ODDS_NAME, allEntries=true, beforeInvocation=true)
	public void updateOpList()
	{
	}

	/* (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#save(com.loris.soccer.model.OddsOp)
	 */
	@Override
	public boolean save(OddsOp op)
	{
		return oddsOpMapper.insert(op) > 0;
	}

	/**
	 * 插入欧赔数据列表
	 * @see com.loris.soccer.service.OddsService#insertOddsOp(java.util.List)
	 */
	@Override
	public boolean insertOddsOp(List<OddsOp> ops)
	{
		return insertOddsOp(ops, true);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertOddsOp(java.util.List, boolean)
	 */
	@Override
	public boolean insertOddsOp(List<OddsOp> ops, boolean overwrite)
	{
		if(ops == null || ops.size() == 0)
		{
			logger.warn("Warn: No " + OddsOp.class.getName() + " need to be updated.");
			return false;
		}
		
		List<String> mids = ArraysUtil.getObjectFieldValue(ops, OddsOp.class, SoccerConstants.NAME_FIELD_MID);		
		QueryWrapper<OddsOp> queryWrapper = new QueryWrapper<OddsOp>().in(SoccerConstants.NAME_FIELD_MID, mids);
		List<OddsOp> existOps = oddsOpMapper.selectList(queryWrapper);		
		List<OddsOp> newOps = new ArrayList<>();
		if(overwrite)
		{
			OddsValueFilter<OddsOp> filter = new OddsValueFilter<>();
			for (OddsOp oddsOp : ops)
			{
				filter.setValue(oddsOp);
				if(!ArraysUtil.hasSameObject(existOps, filter))
				{
					newOps.add(oddsOp);
				}
			}			
			if(newOps.size() == 0)
			{
				logger.warn("Warn: No " + OddsOp.class.getName() + " need to be updated.");
				return true;
			}			
			ops = newOps;
		}
		
		try
		{
			return sqlHelper.insertBatch(ops);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
}
