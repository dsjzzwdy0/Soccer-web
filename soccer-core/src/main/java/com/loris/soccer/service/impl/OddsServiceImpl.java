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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loris.common.filter.ObjectFilter;
import com.loris.common.service.SqlHelper;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.OddsNumMapper;
import com.loris.soccer.dao.OddsOpMapper;
import com.loris.soccer.dao.OddsScoreMapper;
import com.loris.soccer.dao.OddsYpMapper;
import com.loris.soccer.model.OddsNum;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsScore;
import com.loris.soccer.model.OddsYp;
import com.loris.soccer.model.complex.OddsOpRecord;
import com.loris.soccer.service.OddsService;
import com.loris.soccer.wrapper.OddsOpListWrapper;

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
	@Autowired
	private SqlHelper sqlHelper;
	
	@Autowired
	private OddsOpMapper oddsOpMapper;
	
	@Autowired
	private OddsYpMapper oddsYpMapper;
	
	@Autowired
	private OddsNumMapper oddsNumMapper;
	
	@Autowired
	private OddsScoreMapper oddsScoreMapper;
	
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
		queryWrapper.orderByAsc("opentime");
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
		QueryWrapper<OddsOp> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(SoccerConstants.NAME_FIELD_MID, "2402789");
		
		List<OddsOp> list = oddsOpMapper.selectList(queryWrapper);
		for (OddsOp oddsOp : list)
		{
			oddsOp.setLossratio(0.58f);
		}
		try
		{
			sqlHelper.updateBatch(list, OddsOp.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
	 * @see com.loris.soccer.service.OddsService#insertOddsOps(java.util.List)
	 */
	@Override
	public boolean insertOddsOps(List<OddsOp> oddsOps)
	{
		return insertOddsOps(oddsOps, true);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertOddsOps(java.util.List, boolean)
	 */
	@Override
	public boolean insertOddsOps(List<OddsOp> oddsOps, boolean overwrite)
	{
		ObjectFilter<OddsOp> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(oddsOps, OddsOp.class, oddsOpMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertOddsYps(java.util.List)
	 */
	@Override
	public boolean insertOddsYps(List<OddsYp> oddsYps)
	{
		return insertOddsYps(oddsYps, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertOddsYps(java.util.List, boolean)
	 */
	@Override
	public boolean insertOddsYps(List<OddsYp> oddsYps, boolean overwrite)
	{
		ObjectFilter<OddsYp> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(oddsYps, OddsYp.class, oddsYpMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertOddsNums(java.util.List)
	 */
	@Override
	public boolean insertOddsNums(List<OddsNum> oddsNums)
	{
		return insertOddsNums(oddsNums, false);
	}

	/* (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertOddsNums(java.util.List, boolean)
	 */
	@Override
	public boolean insertOddsNums(List<OddsNum> oddsNums, boolean overwrite)
	{
		ObjectFilter<OddsNum> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(oddsNums, OddsNum.class, oddsNumMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertOddsScores(java.util.List)
	 */
	@Override
	public boolean insertOddsScores(List<OddsScore> oddsScores)
	{
		return insertOddsScores(oddsScores, false);
	}

	/* (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertOddsScore(java.util.List, boolean)
	 */
	@Override
	public boolean insertOddsScores(List<OddsScore> oddsScores, boolean overwrite)
	{
		ObjectFilter<OddsScore> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(oddsScores, OddsScore.class, oddsScoreMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#selectOddsOpRecords(java.lang.String)
	 */
	@Override
	public List<OddsOpRecord> selectOddsOpRecords(String mid)
	{
		List<OddsOp> ops = selectOddsOp(mid);
		if(ops == null || ops.size() == 0)
		{
			return null;
		}
		return new OddsOpListWrapper().wrap(ops);
	}
}
