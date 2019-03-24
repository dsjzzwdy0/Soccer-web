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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.common.filter.ObjectFilter;
import com.loris.common.service.SqlHelper;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.MatchBdMapper;
import com.loris.soccer.dao.MatchJcMapper;
import com.loris.soccer.dao.MatchMapper;
import com.loris.soccer.dao.MatchResultMapper;
import com.loris.soccer.dao.view.MatchBdInfoMapper;
import com.loris.soccer.dao.view.MatchJcInfoMapper;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.MatchJc;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.view.MatchBdInfo;
import com.loris.soccer.model.view.MatchJcInfo;
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
	@Autowired
	SqlHelper sqlHelper;
	
	@Autowired
	private MatchBdMapper matchBdMapper;
	
	@Autowired
	private MatchJcMapper matchJcMapper;

	@Autowired
	private MatchResultMapper matchResultMapper;
	
	@Autowired
	private MatchBdInfoMapper matchBdInfoMapper;
	
	@Autowired
	private MatchJcInfoMapper matchJcInfoMapper;
	
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
		ObjectFilter<Match> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(matchs, Match.class, baseMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
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
		ObjectFilter<MatchBd> filter = new ObjectFilter<>();		
		return SqlHelper.insertList(matchBds, MatchBd.class, matchBdMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
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
		ObjectFilter<MatchJc> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(matchJcs, MatchJc.class, matchJcMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#insertMatchResults(java.util.List)
	 */
	@Override
	public boolean insertMatchResults(List<MatchResult> results)
	{
		return insertMatchResults(results, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#insertMatchResults(java.util.List, boolean)
	 */
	@Override
	public boolean insertMatchResults(List<MatchResult> results, boolean overwrite)
	{
		ObjectFilter<MatchResult> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(results, MatchResult.class, matchResultMapper, filter, 
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#getMatchBdInfos(java.lang.String)
	 */
	@Override
	public List<MatchBdInfo> getMatchBdInfos(String issue, String bdno)
	{
		QueryWrapper<MatchBdInfo> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotBlank(issue))
			queryWrapper.eq("issue", issue);
		if(StringUtils.isNotBlank(bdno))
			queryWrapper.eq("bdno", bdno);
		queryWrapper.orderByAsc("issue, ordinary+0");
		return matchBdInfoMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#getMatchJcInfos(java.lang.String)
	 */
	@Override
	public List<MatchJcInfo> getMatchJcInfos(String issue)
	{
		QueryWrapper<MatchJcInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("issue", issue);
		queryWrapper.orderByAsc("issue, ordinary+0");
		return matchJcInfoMapper.selectList(queryWrapper);
	}
}
