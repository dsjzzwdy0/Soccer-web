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
import com.loris.soccer.dao.RecordOddsOpMapper;
import com.loris.soccer.dao.RecordOddsYpMapper;
import com.loris.soccer.dao.view.RecordOddsOpInfoMapper;
import com.loris.soccer.dao.view.RecordOddsYpInfoMapper;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.CompSetting;
import com.loris.soccer.model.OddsNum;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.RecordOddsOp;
import com.loris.soccer.model.RecordOddsYp;
import com.loris.soccer.model.view.RecordOddsOpInfo;
import com.loris.soccer.model.view.RecordOddsYpInfo;
import com.loris.soccer.model.OddsScore;
import com.loris.soccer.model.OddsYp;
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
@Service("oddsService")
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
	
	@Autowired
	private RecordOddsOpMapper recordOddsOpMapper;
	
	@Autowired
	private RecordOddsYpMapper recordOddsYpMapper;
	
	@Autowired
	private RecordOddsOpInfoMapper recordOddsOpinfoMapper;
	
	@Autowired
	private RecordOddsYpInfoMapper recordOddsYpInfoMapper;
	
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
	public List<RecordOddsOp> selectOddsOpRecords(String mid)
	{
		List<OddsOp> ops = selectOddsOp(mid);
		if(ops == null || ops.size() == 0)
		{
			return null;
		}
		return new OddsOpListWrapper().wrap(ops);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#selectOddsOps(java.util.List, java.util.List)
	 */
	@Override
	public List<OddsOp> selectOddsOps(List<String> mids, List<String> corpids)
	{
		QueryWrapper<OddsOp> queryWrapper = new QueryWrapper<>();
		if(mids != null && mids.size() > 0)
		{
			queryWrapper.in(SoccerConstants.NAME_FIELD_MID, mids);
		}
		if(corpids != null && corpids.size() > 0)
		{
			queryWrapper.in(SoccerConstants.NAME_FIELD_CORPID, corpids);
		}
		return oddsOpMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#selectOddsYps(java.util.List, java.util.List)
	 */
	@Override
	public List<OddsYp> selectOddsYps(List<String> mids, List<String> corpids)
	{
		QueryWrapper<OddsYp> queryWrapper = new QueryWrapper<>();
		if(mids != null && mids.size() > 0)
		{
			queryWrapper.in(SoccerConstants.NAME_FIELD_MID, mids);
		}
		if(corpids != null && corpids.size() > 0)
		{
			queryWrapper.in(SoccerConstants.NAME_FIELD_CORPID, corpids);
		}
		return oddsYpMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertRecordOddsOps(java.util.List)
	 */
	@Override
	public boolean insertRecordOddsOps(List<RecordOddsOp> ops)
	{
		return insertRecordOddsOps(ops, true);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertRecordOddsOps(java.util.List, boolean)
	 */
	@Override
	public boolean insertRecordOddsOps(List<RecordOddsOp> ops, boolean overwrite)
	{
		ObjectFilter<RecordOddsOp> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(ops, RecordOddsOp.class, recordOddsOpMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertRecordOddsYps(java.util.List)
	 */
	@Override
	public boolean insertRecordOddsYps(List<RecordOddsYp> yps)
	{
		return insertRecordOddsYps(yps, true);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#insertRecordOddsYps(java.util.List, boolean)
	 */
	@Override
	public boolean insertRecordOddsYps(List<RecordOddsYp> yps, boolean overwrite)
	{
		ObjectFilter<RecordOddsYp> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(yps, RecordOddsYp.class, recordOddsYpMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#getRecordOddsOps(java.util.List, java.util.List)
	 */
	@Override
	@Deprecated
	public List<RecordOddsOp> getRecordOddsOps(List<String> mids, List<String> corpids)
	{
		QueryWrapper<RecordOddsOp> queryWrapper = new QueryWrapper<>();
		if(mids != null && mids.size() > 0)
		{
			queryWrapper.in(SoccerConstants.NAME_FIELD_MID, mids);
		}
		if(corpids != null && corpids.size() > 0)
		{
			queryWrapper.in(SoccerConstants.NAME_FIELD_CORPID, corpids);
		}
		return recordOddsOpMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#getRecordOddsYps(java.util.List, java.util.List)
	 */
	@Override
	@Deprecated
	public List<RecordOddsYp> getRecordOddsYps(List<String> mids, List<String> corpids)
	{
		QueryWrapper<RecordOddsYp> queryWrapper = new QueryWrapper<>();
		if(mids != null && mids.size() > 0)
		{
			queryWrapper.in(SoccerConstants.NAME_FIELD_MID, mids);
		}
		if(corpids != null && corpids.size() > 0)
		{
			queryWrapper.in(SoccerConstants.NAME_FIELD_CORPID, corpids);
		}
		return recordOddsYpMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#getRecordOddsOps(java.util.List, com.loris.soccer.model.CompSetting)
	 */
	@Override
	public List<RecordOddsOpInfo> getRecordOddsOps(List<String> mids, CompSetting compSetting)
	{
		List<CasinoComp> comps = compSetting.getOpComps();
		if(comps == null || comps.size() <= 0) return null;
		
		QueryWrapper<RecordOddsOpInfo> queryWrapper = new QueryWrapper<>();
		for (CasinoComp comp : comps)
		{
			queryWrapper.or(wrapper->wrapper.eq("corpid", comp.getCorpid()).eq("source", comp.getSource()));
		}
		queryWrapper.and(wrapper->wrapper.in("mid", mids).or().in("destid", mids));
		
		//System.out.println(queryWrapper.getCustomSqlSegment());
		return recordOddsOpinfoMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OddsService#getRecordOddsYps(java.util.List, com.loris.soccer.model.CompSetting)
	 */
	@Override
	public List<RecordOddsYpInfo> getRecordOddsYps(List<String> mids, CompSetting compSetting)
	{
		List<CasinoComp> comps = compSetting.getYpComps();
		if(comps == null || comps.size() <= 0) return null;
		
		QueryWrapper<RecordOddsYpInfo> queryWrapper = new QueryWrapper<>();
		for (CasinoComp comp : comps)
		{
			queryWrapper.or(wrapper->wrapper.eq("corpid", comp.getCorpid()).eq("source", comp.getSource()));
		}
		queryWrapper.and(wrapper->wrapper.in("mid", mids).or().in("destid", mids));
		
		//System.out.println(queryWrapper.getCustomSqlSegment());
		return recordOddsYpInfoMapper.selectList(queryWrapper);
	}
}
