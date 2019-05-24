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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.common.filter.ObjectFilter;
import com.loris.common.service.SqlHelper;
import com.loris.common.util.ToolUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.IssueMatchMapper;
import com.loris.soccer.dao.MatchMapper;
import com.loris.soccer.dao.MatchResultMapper;
import com.loris.soccer.dao.view.IssueMatchInfoMapper;
import com.loris.soccer.dao.view.MatchBdInfoMapper;
import com.loris.soccer.dao.view.MatchInfoMapper;
import com.loris.soccer.dao.view.MatchJcInfoMapper;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.complex.TeamGrade;
import com.loris.soccer.model.view.IssueMatchInfo;
import com.loris.soccer.model.view.MatchBdInfo;
import com.loris.soccer.model.view.MatchInfo;
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
	private IssueMatchMapper issueMatchMapper;

	@Autowired
	private MatchResultMapper matchResultMapper;
	
	@Autowired
	private IssueMatchInfoMapper issueMatchInfoMapper;
	
	@Autowired
	private MatchBdInfoMapper matchBdInfoMapper;
	
	@Autowired
	private MatchJcInfoMapper matchJcInfoMapper;
	
	@Autowired
	private MatchInfoMapper matchInfoMapper;
	
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
	 * @see com.loris.soccer.service.MatchService#getMatchInfos(java.util.Date, java.util.Date, java.lang.Boolean)
	 */
	@Override
	public List<MatchInfo> getMatchInfos(Date start, Date end, Boolean hasResult)
	{
		QueryWrapper<MatchInfo> queryWrapper = new QueryWrapper<>();
		if(ToolUtil.isNotEmpty(start))
		{
			queryWrapper.gt("matchtime", start);
		}
		if(ToolUtil.isNotEmpty(end))
		{
			queryWrapper.lt("matchtime", end);
		}
		if(ToolUtil.isNotEmpty(hasResult))
		{
			if(!hasResult) queryWrapper.isNull("result");
			else queryWrapper.isNotNull("result");
		}
		return matchInfoMapper.selectList(queryWrapper);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchBdService#insertIssueMatchs(java.util.List)
	 */
	@Override
	public boolean insertIssueMatchs(List<IssueMatch> issueMatchs)
	{
		return insertIssueMatchs(issueMatchs, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchBdService#insertIssueMatchs(java.util.List, boolean)
	 */
	@Override
	public boolean insertIssueMatchs(List<IssueMatch> issueMatchs, boolean overwrite)
	{
		ObjectFilter<IssueMatch> filter = new ObjectFilter<>();		
		return SqlHelper.insertList(issueMatchs, IssueMatch.class, issueMatchMapper, filter,
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

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#getMatchs(java.util.Date, java.util.Date)
	 */
	@Override
	public List<Match> getMatchs(Date start, Date end)
	{
		QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
		if(ToolUtil.isNotEmpty(start))
		{
			queryWrapper.gt("matchtime", start);
		}
		if(ToolUtil.isNotEmpty(end))
		{
			queryWrapper.lt("matchtime", end);
		}
		return baseMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#getMatchInfos(java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public List<MatchInfo> getMatchInfos(List<String> lids, List<String> tids, Boolean hasResult)
	{
		QueryWrapper<MatchInfo> queryWrapper = new QueryWrapper<>();
		if(ToolUtil.isNotEmpty(hasResult))
		{
			if(!hasResult)
			{
				queryWrapper.isNull("result");
			}
			else
			{
				queryWrapper.isNotNull("result");
				queryWrapper.and(wrapper->wrapper.lt("matchtime", new Date()));
			}
		}
		if(lids != null && lids.size() > 0)
		{
			queryWrapper.in("lid", lids);
		}
		if(tids != null && tids.size() > 0)
		{
			queryWrapper.and(wrapper->wrapper.in("homeid", tids).or().in("clientid", tids));
		}
		
		return matchInfoMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#getTeamGrades(java.util.List, int, boolean)
	 */
	@Override
	public List<TeamGrade> getTeamGrades(List<? extends Match> matchs, int maxMatchNum, boolean sameLeague)
	{
		List<String> lids = new ArrayList<>();
		List<String> tids = new ArrayList<>();
		for (Match match : matchs)
		{
			lids.add(match.getLid());
			tids.add(match.getHomeid());
			tids.add(match.getClientid());
		}
		return null;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#getMatchBds(java.util.Date, java.util.Date)
	 */
	@Override
	public List<IssueMatch> getIssueMatchs(String type, Date start, Date end)
	{
		QueryWrapper<IssueMatch> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotEmpty(type))
		{
			queryWrapper.eq("type", type);
		}
		if(ToolUtil.isNotEmpty(start))
		{
			queryWrapper.gt("matchtime", start);
		}
		if(ToolUtil.isNotEmpty(end))
		{
			queryWrapper.lt("matchtime", end);
		}
		return issueMatchMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#getIssueMatchs(java.lang.String, java.lang.String)
	 */
	@Override
	public List<IssueMatch> getIssueMatchs(String issue, String type)
	{
		QueryWrapper<IssueMatch> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", type);
		queryWrapper.eq("issue", issue);
		return issueMatchMapper.selectList(queryWrapper);
	}

	/* (non-Javadoc)
	 * @see com.loris.soccer.service.MatchService#getIssueMatchsInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<IssueMatchInfo> getIssueMatchsInfo(String issue, String type)
	{
		QueryWrapper<IssueMatchInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("type", type);
		queryWrapper.eq("issue", issue);
		return issueMatchInfoMapper.selectList(queryWrapper);
	}
}
