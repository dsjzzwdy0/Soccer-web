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
package com.loris.soccer.stat;

import java.util.List;

import org.apache.log4j.Logger;

import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.OddsOpList;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.MatchResult.ResultType;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.view.MatchInfo;
import com.loris.soccer.stat.collection.TeamCapabilityList;
import com.loris.soccer.stat.model.EveluateResult;
import com.loris.soccer.stat.model.MatchProbility;
import com.loris.soccer.stat.model.RatingParam;
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
	
	public final static float TEAM_BASE_CAPABILITY = 1000.0f;		//基础分值
		
	/**
	 * Create a new instance of TeamRating
	 */
	public TeamRating()
	{
	}
	
	/**
	 * 计算球队的评分系统
	 * @param teams
	 * @param lid
	 * @param matchs
	 * @param goalWinPercent
	 */
	public TeamCapabilityList computeTeamCapability(String lid, List<MatchInfo> matchs, RatingParam param)
	{
		if(matchs == null || matchs.size() == 0)
		{
			logger.info("There are no match record");
			return null;
		}
		TeamCapabilityList teams = computeTeamCapability(lid, matchs, param.getHomekitty(), 
				param.getClientkitty(), param.getGoalwinpercent(), param.getCappownum());
		computeTeamGoal(lid, teams, matchs, param.isUsecapratio());
		return teams;
	}
	
	/**
	 * 计算球队实力的函数
	 * @param teams
	 * @param lid
	 * @param matchs
	 * @param homeKitty
	 * @param clietnKitty
	 * @param goalWinPercent
	 */
	public TeamCapabilityList computeTeamCapability(String lid, List<MatchInfo> matchs, 
			float homeKitty, float clientKitty, float goalWinPercent, float capPowNum)
	{
		TeamCapabilityList teams = new TeamCapabilityList(); 
		for (MatchInfo match : matchs)
		{
			TeamCapability homeTeam = teams.getTeamCapability(lid, match.getHomeid());
			TeamCapability clientTeam = teams.getTeamCapability(lid, match.getClientid());
			
			if(homeTeam == null)
			{
				homeTeam = new TeamCapability(lid, match.getHomeid(), "");
				teams.add(homeTeam);
			}
			if(clientTeam == null)
			{
				clientTeam = new TeamCapability(lid, match.getClientid(), "");
				teams.add(clientTeam);
			}
			
			computeDynamicTeamRating(homeTeam, homeKitty, clientTeam, clientKitty, match.getMatchResult(), goalWinPercent, capPowNum);
		}
		return teams;
	}
	
	/**
	 * 计算动态球队实力数据
	 * @param homeTeam
	 * @param homeKitty
	 * @param clientTeam
	 * @param clientKitty
	 * @param result
	 * @param goalWinPercent
	 */
	protected void computeDynamicTeamRating(TeamCapability homeTeam, float homeKitty, 
			TeamCapability clientTeam, float clientKitty,
			MatchResult result, float goalWinPercent, float capPowNum)
	{
		float homeCap = homeTeam.getCapability();
		float clientCap = clientTeam.getCapability();
		
		float newHomeKitty = homeKitty *(float) Math.pow(homeCap / TEAM_BASE_CAPABILITY, capPowNum);
		float newClientKitty = clientKitty * (float) Math.pow(clientCap / TEAM_BASE_CAPABILITY, capPowNum);
		
		computeBasicTeamRating(homeTeam, newHomeKitty, clientTeam, newClientKitty, result, goalWinPercent);
	}
	
	/**
	 * 计算球队的最新评分系统
	 * @param homeTeam 主队
	 * @param clientTeam 客队
	 * @param result 比赛结果
	 */
	protected void computeBasicTeamRating(TeamCapability homeTeam, float homeKitty, 
			TeamCapability clientTeam, float clientKitty,
			MatchResult result, float goalWinPercent)
	{
		ResultType resultType = result.getResultType();
		
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
	
	/**
	 * 计算球队的进球数据
	 * @param lid 联赛编号
	 * @param teams 球队列表
	 * @param matchs 比赛数据
	 */
	protected void computeTeamGoal(String lid, TeamCapabilityList teams, List<MatchInfo> matchs, boolean useCapRatio)
	{
		for (MatchInfo matchInfo : matchs)
		{
			MatchResult result = matchInfo.getMatchResult();
			TeamCapability homeTeam = teams.getTeamCapability(lid, matchInfo.getHomeid());
			TeamCapability clientTeam = teams.getTeamCapability(lid, matchInfo.getClientid());
			if(homeTeam == null)
			{
				continue;
			}
			if(clientTeam == null)
			{
				continue;
			}
			
			float wingoal = 0;
			float losegoal = 0;
			if(useCapRatio)
			{
				wingoal = result.getHomegoal() * (clientTeam.getCapability() / TEAM_BASE_CAPABILITY);
				losegoal = result.getClientgoal() * (homeTeam.getCapability() / TEAM_BASE_CAPABILITY);
			}
			else
			{
				wingoal = result.getHomegoal();
				losegoal = result.getClientgoal();
			}
			
			homeTeam.addGoal(wingoal, losegoal);
			clientTeam.addGoal(losegoal, wingoal);
		}
	}
	
	/**
	 * 球队战绩评估函数
	 * @param teams 球队列表
	 * @param validateMatches 待评估比赛
	 * @param ops 欧赔数据
	 */
	public void evaluate(TeamCapabilityList teams, List<MatchInfo> validateMatches, OddsOpList ops, String corpid, float maxError)
	{
		//double[] errors = new double[3];
		EveluateResult result = new EveluateResult();
		result.setTotal(validateMatches.size());
		for (MatchInfo match : validateMatches)
		{
			TeamCapability homeTeam = teams.getTeamCapability(match.getHomeid());
			TeamCapability clientTeam = teams.getTeamCapability(match.getClientid());
			
			if(homeTeam == null || clientTeam ==null)
				continue;
			
			MatchProbility p = new MatchProbility(match.getMid(), homeTeam, clientTeam);
			logger.info(p);
			
			OddsOp op = ops.getOddsOp(match.getMid(), corpid);
			if(op == null)
				continue;
			
			double winError = p.getWinprob() - op.getWinprob();
			double drawError = p.getDrawprob() - op.getDrawprob();
			double loseError = p.getLoseprob() - op.getLoseprob();
			
			logger.info(match.getMid() + " winErr=" + NumberUtil.formatDouble(2, winError) 
				+ ", drawErr=" + NumberUtil.formatDouble(2, drawError)  
				+ ", loseErr=" + NumberUtil.formatDouble(2, loseError)
				+ ", " + match.getMatchResult());
			
			if(Math.abs(winError) > maxError && Math.abs(drawError) > maxError
					&& Math.abs(loseError) > maxError)
			{
				continue;
			}
			
			result.addError(winError, drawError, loseError);
		}
		
		logger.info(result);
	}
}
