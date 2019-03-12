/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.common.service.SqlHelper;
import com.loris.common.util.ArraysUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.MatchBdMapper;
import com.loris.soccer.dao.MatchJcMapper;
import com.loris.soccer.dao.MatchMapper;
import com.loris.soccer.filter.MatchItemFilter;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.MatchJc;
import com.loris.soccer.service.MatchService;

/**
 * @ClassName: League
 * @Description: 比赛数据服务接口实现
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service("matchService")
public class MatchServiceImpl extends ServiceImpl<MatchMapper, Match> implements MatchService
{
	private static Logger logger = Logger.getLogger(MatchServiceImpl.class);

	@Autowired
	SqlHelper helper;
	
	@Autowired
	private MatchBdMapper matchBdMapper;
	
	@Autowired
	private MatchJcMapper matchJcMapper;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.soccer.service.MatchService#insertMatchs(java.util.List)
	 */
	@Override
	public boolean insertMatchs(List<Match> matchs)
	{
		return insertMatchs(matchs, false);
	}

	/**
	 * 插入数据库记录
	 * 
	 * @param matchs
	 * @param overwrite
	 * @return 是否更新成功的标志
	 */
	@Transactional
	public boolean insertMatchs(List<Match> matchs, boolean overwrite)
	{
		if(matchs == null || matchs.size() == 0)
		{
			logger.warn("Warn: No match need to be updated.");
			return false;
		}
		List<String> mids = ArraysUtil.getObjectFieldValue(matchs, Match.class, SoccerConstants.NAME_FIELD_MID);
		if (overwrite)
		{
			baseMapper.delete(new QueryWrapper<Match>().in(SoccerConstants.NAME_FIELD_MID, mids));
		}
		else
		{
			List<Match> existMatchs = list(new QueryWrapper<Match>().in(SoccerConstants.NAME_FIELD_MID, mids));
			List<Match> destMatchs = new ArrayList<>();

			MatchItemFilter<Match> filter = new MatchItemFilter<>();
			for (Match match : matchs)
			{
				filter.setValue(match);
				if (!ArraysUtil.hasSameObject(existMatchs, filter))
				{
					destMatchs.add(match);
				}
			}

			if (destMatchs.size() == 0)
			{
				logger.warn("Warn: No match need to be updated.");
				return true;
			}
			matchs = destMatchs;
		}
		try
		{
			return helper.insertBatch(matchs);
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			return false;
		}
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchBdService#insertMatchBds(java.util.List)
	 */
	@Override
	public boolean insertMatchBds(List<MatchBd> matchBds)
	{
		return insertMatchBds(matchBds, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchBdService#insertMatchBds(java.util.List, boolean)
	 */
	@Override
	public boolean insertMatchBds(List<MatchBd> matchBds, boolean overwrite)
	{
		if(matchBds == null || matchBds.size() == 0)
		{
			logger.warn("Warn: No match need to be updated.");
			return false;
		}
		List<String> mids = ArraysUtil.getObjectFieldValue(matchBds, MatchBd.class, SoccerConstants.NAME_FIELD_MID);
		if (overwrite)
		{
			matchBdMapper.delete(new QueryWrapper<MatchBd>().in(SoccerConstants.NAME_FIELD_MID, mids));
		}
		else
		{
			List<MatchBd> existMatchs = matchBdMapper.selectList(new QueryWrapper<MatchBd>().in(SoccerConstants.NAME_FIELD_MID, mids));
			List<MatchBd> destMatchs = new ArrayList<>();

			MatchItemFilter<MatchBd> filter = new MatchItemFilter<>();
			for (MatchBd match : matchBds)
			{
				filter.setValue(match);
				if (!ArraysUtil.hasSameObject(existMatchs, filter))
				{
					destMatchs.add(match);
				}
			}

			if (destMatchs.size() == 0)
			{
				logger.warn("Warn: No match need to be updated.");
				return true;
			}
			matchBds = destMatchs;
		}
		try
		{
			return helper.insertBatch(matchBds);
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			return false;
		}
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#insertMatchJcs(java.util.List)
	 */
	@Override
	public boolean insertMatchJcs(List<MatchJc> matchJcs)
	{
		return insertMatchJcs(matchJcs, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#insertMatchJcs(java.util.List, boolean)
	 */
	@Override
	public boolean insertMatchJcs(List<MatchJc> matchJcs, boolean overwrite)
	{
		if(matchJcs == null || matchJcs.size() == 0)
		{
			logger.warn("Warn: No match need to be updated.");
			return false;
		}
		List<String> mids = ArraysUtil.getObjectFieldValue(matchJcs, MatchJc.class, SoccerConstants.NAME_FIELD_MID);
		if (overwrite)
		{
			matchJcMapper.delete(new QueryWrapper<MatchJc>().in(SoccerConstants.NAME_FIELD_MID, mids));
		}
		else
		{
			List<MatchJc> existMatchs = matchJcMapper.selectList(new QueryWrapper<MatchJc>().in(SoccerConstants.NAME_FIELD_MID, mids));
			List<MatchJc> destMatchs = new ArrayList<>();

			MatchItemFilter<MatchJc> filter = new MatchItemFilter<>();
			for (MatchJc match : matchJcs)
			{
				filter.setValue(match);
				if (!ArraysUtil.hasSameObject(existMatchs, filter))
				{
					destMatchs.add(match);
				}
			}

			if (destMatchs.size() == 0)
			{
				logger.warn("Warn: No match need to be updated.");
				return true;
			}
			matchJcs = destMatchs;
		}
		try
		{
			return helper.insertBatch(matchJcs);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
