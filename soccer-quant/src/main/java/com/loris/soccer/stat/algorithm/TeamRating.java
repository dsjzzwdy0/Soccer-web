/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  TeamRating.java   
 * @Package com.loris.soccer.stat.algorithm   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.algorithm;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.MatchResult.ResultType;
import com.loris.soccer.model.view.MatchInfo;
import com.loris.soccer.service.LeagueService;
import com.loris.soccer.service.MatchService;
import com.loris.soccer.stat.collection.TeamCapabilityList;
import com.loris.soccer.stat.model.TeamCapability;

/**   
 * @ClassName:  TeamRating    
 * @Description: 球队能力评价系统
 *  1、球队实力基准数：capability
 *	2、每进行一场比赛，放入奖金池(Kitty值)，其kitty参数：
 *    主队：kittyHome
 *    客队：kittyClient
 *	3、球队比赛之后的实力计算值：
 *    胜： + kittyHome + kittyClient
 *    平： + (kittyHome + kittyClient) / 2
 *    负： + 0  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TeamRating
{
	private static Logger logger = Logger.getLogger(TeamRating.class);
	
	public static float TEAM_BASE_CAPABILITY = 1000.0f;		//基础分值
	
	protected float baseCapability = TEAM_BASE_CAPABILITY;
	protected float homeKitty = 0.07f;
	protected float clientKitty = 0.05f;
	protected float goalWinPercent = 0.6f;
	
	/** 比赛数据服务 */
	protected MatchService matchService;
	
	/** 联赛数据服务 */
	protected LeagueService leagueService;
	
	/**
	 * Create a new instance of TeamRating
	 */
	public TeamRating(MatchService matchService, LeagueService leagueService)
	{
		this.matchService = matchService;
		this.leagueService = leagueService;
	}
	
	/**
	 * 计算球队的评分系统
	 * @param teams
	 * @param matchs
	 * @return
	 */
	public List<TeamCapability> rating(String lid, Date start, Date end)
	{
		List<MatchInfo> matchInfos = matchService.getMatchInfos(lid, start, end, true);
		if(matchInfos == null || matchInfos.size() == 0)
		{
			logger.info("There are no match record in database of lid=" + lid + ", start=" + start + ", end=" + end);
			return null;
		}
		logger.info("Total match size is : " + matchInfos.size());
		TeamCapabilityList teams = new TeamCapabilityList();
		for (MatchInfo match : matchInfos)
		{
			TeamCapability homeTeam = teams.geTeamCapability(lid, match.getHomeid());
			TeamCapability clientTeam = teams.geTeamCapability(lid, match.getClientid());
			
			if(homeTeam == null)
			{
				homeTeam = new TeamCapability(lid, match.getHomeid(), "");
				homeTeam.setCapability(baseCapability);
				teams.add(homeTeam);
			}
			if(clientTeam == null)
			{
				clientTeam = new TeamCapability(lid, match.getClientid(), "");
				clientTeam.setCapability(baseCapability);
				teams.add(clientTeam);
			}
			
			computeNewRating(homeTeam, clientTeam, match.getMatchResult());
		}
		
		return teams;
	}
	
	/**
	 * 计算球队的最新评分系统
	 * @param homeTeam 主队
	 * @param clientTeam 客队
	 * @param result 比赛结果
	 */
	protected void computeNewRating(TeamCapability homeTeam, TeamCapability clientTeam, MatchResult result)
	{
		ResultType resultType = result.getResult();
		
		float homeCap = homeTeam.getCapability();
		float clientCap = clientTeam.getCapability();
		
		float totalCap = homeCap * homeKitty + clientCap * clientKitty;
		
		float homeChangeCap = -homeCap * homeKitty ;
		float clientChangeCap =  - clientCap * clientKitty;
		
		if(resultType == ResultType.DRAW)
		{
			homeChangeCap += totalCap * 0.5f;
			clientChangeCap += totalCap * 0.5f;
		}
		else
		{
			float homeGoal = result.getHomegoal();
			float clietGoal = result.getClientgoal();
			float totalGoal = homeGoal + clietGoal;
						
			if(resultType == ResultType.WIN)
			{
				homeChangeCap += goalWinPercent * totalCap + (1.0f - goalWinPercent) * (homeGoal * totalCap) / totalGoal;
				clientChangeCap += (1.0f - goalWinPercent) * (clietGoal * totalCap) / totalGoal;
			}
			else if(resultType == ResultType.LOSE)
			{
				homeChangeCap += (1.0f - goalWinPercent) * (homeGoal * totalCap) / totalGoal;
				clientChangeCap += goalWinPercent * totalCap + (1.0f - goalWinPercent) * (clietGoal * totalCap) / totalGoal;
			}
		}
		
		homeTeam.addCapability(homeChangeCap);
		clientTeam.addCapability(clientChangeCap);
	}

	public float getHomeKitty()
	{
		return homeKitty;
	}

	public void setHomeKitty(float homeKitty)
	{
		this.homeKitty = homeKitty;
	}

	public float getClientKitty()
	{
		return clientKitty;
	}

	public void setClientKitty(float clientKitty)
	{
		this.clientKitty = clientKitty;
	}

	public float getGoalWinPercent()
	{
		return goalWinPercent;
	}

	public void setGoalWinPercent(float goalWinPercent)
	{
		this.goalWinPercent = goalWinPercent;
	}

	public float getBaseCapability()
	{
		return baseCapability;
	}

	public void setBaseCapability(float baseCapability)
	{
		this.baseCapability = baseCapability;
	}
}
