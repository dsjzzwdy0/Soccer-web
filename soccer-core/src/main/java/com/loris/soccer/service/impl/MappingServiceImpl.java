/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MappingServiceImpl.java   
 * @Package com.loris.soccer.service.implcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loris.common.filter.ObjectFilter;
import com.loris.common.service.SqlHelper;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.IssueMatchMapper;
import com.loris.soccer.dao.LeagueMapper;
import com.loris.soccer.dao.OkoooIssueMatchMapper;
import com.loris.soccer.dao.OkoooLeagueMapper;
import com.loris.soccer.dao.OkoooMatchMapper;
import com.loris.soccer.dao.OkoooTeamMapper;
import com.loris.soccer.dao.TeamMapper;
import com.loris.soccer.dao.mapping.LeagueMappingMapper;
import com.loris.soccer.dao.mapping.MatchMappingMapper;
import com.loris.soccer.dao.mapping.TeamMappingMapper;
import com.loris.soccer.dao.view.MatchInfoMapper;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.League;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooTeam;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.base.Mapping;
import com.loris.soccer.model.mapping.LeagueMapping;
import com.loris.soccer.model.mapping.MatchMapping;
import com.loris.soccer.model.mapping.TeamMapping;
import com.loris.soccer.model.view.MatchInfo;
import com.loris.soccer.service.MappingService;

/**   
 * @ClassName:  MappingServiceImpl.java   
 * @Description: 映射服务的实现类   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
@Service("mappingService")
public class MappingServiceImpl implements MappingService
{
	@Autowired
	private SqlHelper sqlHelper;
	
	@Autowired
	private TeamMapper teamMapper;
	
	@Autowired
	private OkoooTeamMapper okoooTeamMapper;
	
	@Autowired
	private LeagueMapper leagueMapper;
	
	@Autowired
	private OkoooLeagueMapper okoooLeagueMapper;
	
	@Autowired
	private MatchInfoMapper matchInfoMapper;
	
	@Autowired
	private OkoooMatchMapper okoooMatchMapper;
	
	@Autowired
	private MatchMappingMapper matchMappingMapper;
	
	@Autowired
	private IssueMatchMapper issueMatchMapper;
	
	@Autowired
	private OkoooIssueMatchMapper okoooIssueMatchMapper;
	
	@Autowired
	private TeamMappingMapper teamMappingMapper;

	@Autowired
	private LeagueMappingMapper leagueMappingMapper;

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getMatchMappings(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<MatchMapping> getMatchMappings(String source, String dest, Date start, Date end)
	{
		QueryWrapper<MatchMapping> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotBlank(source))
		{
			queryWrapper.eq("sourcefrom", source);
		}
		if(start != null)
		{
			queryWrapper.gt("matchtime", start);
		}
		if(end != null)
		{
			queryWrapper.lt("matchtime", end);
		}
		return matchMappingMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getOkoooIssueMatchs(java.util.Date, java.util.Date)
	 */
	@Override
	public List<OkoooIssueMatch> getOkoooIssueMatchs(Date start, Date end)
	{
		QueryWrapper<OkoooIssueMatch> queryWrapper = new QueryWrapper<>();
		if(start != null)
		{
			queryWrapper.gt("matchtime", start);
		}
		if(end != null)
		{
			queryWrapper.lt("matchtime", end);
		}
		return okoooIssueMatchMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getIssueMatchs(java.util.Date, java.util.Date)
	 */
	@Override
	public List<IssueMatch> getIssueMatchs(Date start, Date end)
	{
		QueryWrapper<IssueMatch> queryWrapper = new QueryWrapper<>();
		if(start != null)
		{
			queryWrapper.gt("matchtime", start);
		}
		if(end != null)
		{
			queryWrapper.lt("matchtime", end);
		}
		return issueMatchMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getMatchs(java.util.Date, java.util.Date)
	 */
	@Override
	public List<MatchInfo> getMatchs(Date start, Date end)
	{
		QueryWrapper<MatchInfo> queryWrapper = new QueryWrapper<>();
		if(start != null)
		{
			queryWrapper.gt("matchtime", start);
		}
		if(end != null)
		{
			queryWrapper.lt("matchtime", end);
		}
		return matchInfoMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getOkoooMatchs(java.util.Date, java.util.Date)
	 */
	@Override
	public List<OkoooMatch> getOkoooMatchs(Date start, Date end)
	{
		QueryWrapper<OkoooMatch> queryWrapper = new QueryWrapper<>();
		if(start != null)
		{
			queryWrapper.gt("matchtime", start);
		}
		if(end != null)
		{
			queryWrapper.lt("matchtime", end);
		}
		return okoooMatchMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getMatchs(java.util.List)
	 */
	@Override
	public List<MatchInfo> getMatchs(List<String> mids)
	{
		QueryWrapper<MatchInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("mid", mids);
		return matchInfoMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getOkoooMatchs(java.util.List)
	 */
	@Override
	public List<OkoooMatch> getOkoooMatchs(List<String> mids)
	{
		QueryWrapper<OkoooMatch> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("mid", mids);
		return okoooMatchMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getLeagues()
	 */
	@Override
	public List<League> getLeagues()
	{
		return leagueMapper.selectList(new QueryWrapper<>());
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getOkoooLeagues()
	 */
	@Override
	public List<OkoooLeague> getOkoooLeagues()
	{
		return okoooLeagueMapper.selectList(new QueryWrapper<>());
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getTeams()
	 */
	@Override
	public List<Team> getTeams()
	{
		return teamMapper.selectList(new QueryWrapper<>());
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getTeams(java.util.List)
	 */
	@Override
	public List<Team> getTeams(List<String> tids)
	{
		QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("tid", tids);
		return teamMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#getOkoooTeams(java.util.List)
	 */
	@Override
	public List<OkoooTeam> getOkoooTeams(List<String> names)
	{
		QueryWrapper<OkoooTeam> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("tid", names).or().in("name", names);
		return okoooTeamMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MappingService#insertList(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Mapping> boolean insertList(Class<T> clazz, List<T> mappings)
	{
		BaseMapper<T> mapper = null;
		if(clazz.isAssignableFrom(TeamMapping.class))
		{
			mapper = (BaseMapper<T>)teamMappingMapper;
		}
		else if(clazz.isAssignableFrom(LeagueMapping.class))
		{
			mapper = (BaseMapper<T>) leagueMappingMapper;
		}
		else if(clazz.isAssignableFrom(MatchMapping.class))
		{
			mapper = (BaseMapper<T>) matchMappingMapper;
		}
		if(mapper == null)
			return false;
		
		return SqlHelper.insertList(mappings, clazz, mapper, new ObjectFilter<T>(),
				SoccerConstants.NAME_FIELD_SOURCE_ID, sqlHelper, false);
	}

}
