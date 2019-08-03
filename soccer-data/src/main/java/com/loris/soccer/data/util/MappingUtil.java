/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MappingUtil.java   
 * @Package com.loris.soccer.data.utilcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.loris.common.util.ArraysUtil;
import com.loris.common.util.NameUtils;
import com.loris.common.util.PairMap;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.collection.LeagueMappingList;
import com.loris.soccer.collection.MatchMappingList;
import com.loris.soccer.collection.TeamMappingList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.filter.MatchItemFilter;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooTeam;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.base.MatchItem;
import com.loris.soccer.model.mapping.LeagueMapping;
import com.loris.soccer.model.mapping.MatchMapping;
import com.loris.soccer.model.mapping.TeamMapping;
import com.loris.soccer.model.view.MatchInfo;
import com.loris.soccer.service.MappingService;

import cn.hutool.core.date.DateUtil;


/**   
 * @ClassName:  MappingUtil.java   
 * @Description: 数据映射处理工具 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class MappingUtil
{
	private static Logger logger = Logger.getLogger(MappingUtil.class);
	
	/**
	 * 从比赛期号的数据创建数据映射
	 * @param mappingService 数据映射服务
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 是否成功的标志
	 */
	public static boolean createMappingFromIssueMatch(MappingService mappingService, Date start, Date end)
	{
		logger.info("Create Mappings from IssueMatch from " + DateUtil.formatDateTime(start) 
			+ (end == null ? "" : " to " + DateUtil.formatDateTime(end)));
		MatchMappingList matchMappingList = createOkoooIssueMatchMapping(mappingService, start, end);	
		if(matchMappingList == null || matchMappingList.size() <= 0)
			return false;
		
		try
		{
			matchMappingList.setOverwrite(true);
			mappingService.insertList(MatchMapping.class, matchMappingList);
		}
		catch (Exception e) {
			logger.warn("Error occuredn when save MatchMappings, info: " + e.toString());
		}
		
		//创建比赛数据的映射
		PairMap<? extends Match, ? extends Match> matchPairs = getPairMatchs(mappingService, matchMappingList);
		if(matchPairs == null || matchPairs.size() <= 0)
		{
			return true;
		}	
		
		try
		{			
			LeagueMappingList leagueMappingList = getOkoooLeagueMappings(mappingService, matchPairs);
			if(leagueMappingList != null && leagueMappingList.size() > 0)
				{
				leagueMappingList.setOverwrite(false);
				mappingService.insertList(LeagueMapping.class, leagueMappingList);
			}
		}
		catch (Exception e) {
			logger.warn("Error occured when create LeagueMappings, info: " + e.toString());
		}
		
		try
		{
			TeamMappingList teamMappingList = getOkoooTeamMappings(mappingService, matchPairs);
			if(teamMappingList != null && teamMappingList.size() > 0)
			{
				teamMappingList.setOverwrite(false);
				mappingService.insertList(TeamMapping.class, teamMappingList);
			}
		}
		catch (Exception e) {
			logger.warn("Error occured when create TeamMappings, info: " + e.toString());
		}
		
		return true;
	}
	
	
	/**
	 * 计算球队名称的相关系数
	 * @param sourceTeams 数据源球队
	 * @param destTeams 目标球队
	 * @return 相关系数
	 */
	public static float[][] computeTeamNameCoefficients(List<? extends Team> sourceTeams, List<? extends Team> destTeams)
	{
		int sourceNum = sourceTeams.size();
		int destNum = destTeams.size();
		
		float[][] coefficients = new float[sourceNum][destNum];
		for(int i = 0; i < sourceNum; i ++)
		{
			for(int j = 0; j < destNum; j ++)
			{
				coefficients[i][j] = NameUtils.distanceOfTwoStrings(sourceTeams.get(i).getName(), destTeams.get(j).getName());
			}
		}
		return coefficients;
	}
	
	/**
	 * 球队名称映射与匹配类
	 * @param service 数据服务类
	 * @param matchPairs 比赛匹配类
	 * @return 球队匹配列表
	 */
	public static TeamMappingList getOkoooTeamMappings(MappingService service, PairMap<? extends Match, ? extends Match> matchPairs)
	{
		String source = SoccerConstants.SOURCE_OKOOO;
		String dest = SoccerConstants.SOURCE_ZGZCW;
		TeamMappingList teamMappingList = new TeamMappingList(source, dest);
		
		List<String> tids = new ArrayList<>();
		List<String> srcnames = new ArrayList<>();
		getTeamIdsAndNames(matchPairs, tids, srcnames);
		
		List<OkoooTeam> okoooTeams = service.getOkoooTeams(srcnames);
		List<Team> teams = service.getTeams(tids);
		
		//判断是否存在球队数据
		if(okoooTeams == null || okoooTeams.size() <= 0 ||
				teams == null || teams.size() <=0)
		{
			return teamMappingList;
		}
		
		for (Match sourceMatch : matchPairs.keySet())
		{
			Match match = matchPairs.get(sourceMatch);
			
			//匹配主队
			Team homeTeam = getTeam(teams, match.getHomeid());
			Team homeSrcTeam = getTeam(okoooTeams, sourceMatch.getHomeid());
			if(homeTeam != null && homeSrcTeam != null)
			{
				teamMappingList.addMapping(homeSrcTeam, homeSrcTeam);
			}
			
			//匹配客队
			Team clientTeam = getTeam(teams, match.getClientid());
			Team clientSrcTeam = getTeam(okoooTeams, sourceMatch.getClientid());
			if(clientSrcTeam != null && clientTeam != null)
			{
				teamMappingList.addMapping(clientSrcTeam, clientTeam);
			}
		}
		
		return teamMappingList;
	}
	
	/**
	 * 创建联赛匹配数据
	 * @param service 数据服务类
	 * @param matchPairs 比赛匹配
	 * @return 联赛匹配数据列表
	 */
	public static LeagueMappingList getOkoooLeagueMappings(MappingService service,
			PairMap<? extends Match, ? extends Match> matchPairs)
	{
		String source = SoccerConstants.SOURCE_OKOOO;
		String dest = SoccerConstants.SOURCE_ZGZCW;
		LeagueMappingList leagueMappingList = new LeagueMappingList(source, dest);
		
		LeagueList leagues = new LeagueList(service.getLeagues());
		LeagueList okoooLeagues = new LeagueList(service.getOkoooLeagues());
		
		for (Match okoooMatch : matchPairs.keySet())
		{
			Match match = matchPairs.get(okoooMatch);
			
			League sourceLeague = okoooLeagues.getLeague(okoooMatch.getLid());
			League destLeague = leagues.getLeague(match.getLid());
			
			//添加联赛数据映射
			if(sourceLeague != null && destLeague != null)
			{
				leagueMappingList.addMapping(sourceLeague, destLeague);
			}
		}
		
		return leagueMappingList;
	}
	
	
	/**
	 * 从数据库中查找数据的对应的匹配的列表
	 * @param service 数据服务
	 * @param mappingList 匹配列表
	 * @return 匹配之后的列表
	 */
	public static PairMap<? extends Match, ? extends Match> getPairMatchs(MappingService service, MatchMappingList mappingList)
	{
		List<String> mids = new ArrayList<>();
		ArraysUtil.getObjectFieldValue(mappingList, mids, MatchMapping.class, "destid");
		List<MatchInfo> matchInfos = service.getMatchs(mids);
		
		List<String> okooomids = new ArrayList<>();
		ArraysUtil.getObjectFieldValue(mappingList, okooomids, MatchMapping.class, "sourceid");
		List<OkoooMatch> okoooMatchs = service.getOkoooMatchs(okooomids);
		
		PairMap<OkoooMatch, MatchInfo> pairs = new PairMap<>();
		
		MatchItemFilter<MatchItem> filter = new MatchItemFilter<>();
		
		for (MatchMapping mapping : mappingList)
		{
			filter.setMid(mapping.getSourceid());
			OkoooMatch okoooMatch = (OkoooMatch)ArraysUtil.getSameObject(okoooMatchs, filter);
			filter.setMid(mapping.getDestid());
			MatchInfo matchInfo = (MatchInfo)ArraysUtil.getSameObject(matchInfos, filter);
			
			if(okoooMatch != null && matchInfo != null)
				pairs.put(okoooMatch, matchInfo);
			else
				logger.info("There are no mapping matches: " + mapping);
		}
		return pairs;
	}
	
	/**
	 * 创建比赛数据映射列表
	 * @param service 数据服务
	 * @param source 数据来源
	 * @param dest 映射目标数据类型
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 完成映射的比赛数据列表
	 */
	public static MatchMappingList createOkoooIssueMatchMapping(MappingService service, Date start, Date end)
	{
		String source = SoccerConstants.SOURCE_OKOOO;
		String dest = SoccerConstants.SOURCE_ZGZCW;
		
		//获取该时间段内已经获得映射的比赛数据列表
		List<MatchMapping> mappings = service.getMatchMappings(source, dest, start, end);
		MatchMappingList mappingList = new MatchMappingList(source, dest, mappings);
		
		//获得需要进行映射的比赛数据
		List<? extends IssueMatch> sourceIssueMatchs = service.getOkoooIssueMatchs(start, end);
		
		//判断是否有比赛数据需要映射
		if(sourceIssueMatchs == null || sourceIssueMatchs.size() <= 0)
		{
			logger.info("There are no source matchs to be mapped, exist");
			return null;
		}
		logger.info("There are total " + sourceIssueMatchs.size() + " matchs in database.");
		
		sourceIssueMatchs = filterMappingMatchs(mappingList, sourceIssueMatchs);

		//第一步，通过竞彩、北单数据建立映射
		int num = createMatchMappingFromIssue(service, mappingList, source, sourceIssueMatchs, dest, start, end);
		logger.info("Create MatchMapping from Issue, there are " + num + " matches has mapped.");
		
		return mappingList;
	}
	
	/**
	 * 获得比赛的映射数据
	 * @param mappingList 数据映射结果
	 * @param source 数据来源
	 * @param sourceIssueMatchs 澳客网的比赛数据
	 * @param dest 数据目标
	 * @param issueMatchs 中国足彩网的比赛数据
	 * @return 数据映射
	 */
	public static int createMatchMappingFromIssue(MappingService service, MatchMappingList mappingList, 
			String source, List<? extends IssueMatch> sourceIssueMatchs,
			String dest, Date start, Date end)
	{
		List<IssueMatch> issueMatchs = service.getIssueMatchs(start, end);
		logger.info("There are total " + issueMatchs.size() + " matchs in database.");
		
		int matchnum = 0;
		for (IssueMatch sourceMatch : sourceIssueMatchs)
		{
			for (IssueMatch issueMatch : issueMatchs)
			{
				//只要是序号与类型相等，则两场比赛就可完全就行匹配
				if(StringUtils.equals(sourceMatch.getOrdinary(), issueMatch.getOrdinary())
						&& StringUtils.equals(sourceMatch.getType(), issueMatch.getType())
						&& StringUtils.equals(sourceMatch.getIssue(), issueMatch.getIssue()))
				{
					mappingList.addMapping(sourceMatch.getMid(), issueMatch.getMid(), sourceMatch.getMatchtime());
					matchnum ++;
					//logger.info(sourceMatch + "=>" + issueMatch);
				}
			}
		}

		return matchnum;
	}
	
	/**
	 * 检测是否存在的比赛数据,如果已经映射的比赛，则删除掉
	 * @param mappingList
	 * @param sourceMatchs
	 * @return
	 */
	protected static <T extends MatchItem> List<T> filterMappingMatchs(MatchMappingList mappingList, List<T> sourceMatchs)
	{
		List<T> results = new ArrayList<>();
		for (T t : sourceMatchs)
		{
			if(!mappingList.isSourceExist(t.getMid()))
			{
				results.add(t);
			}
		}
		return results;
	}
	
	/**
	 * 查找匹配的球队数据
	 * @param teams 球队列表
	 * @param name 名称
	 * @return 球队
	 */
	protected static <T extends Team> T getTeam(List<T> teams, String name)
	{
		for (T t : teams)
		{
			if(StringUtils.equals(t.getTid(), name) ||
					StringUtils.equals(t.getName(), name))
				return t;
		}
		return null;
	}
	
	/**
	 * 获得球队的编号和名称数据列表
	 * @param matchPairs 映射的比赛数据
	 * @param tids 原始数据球队ID列表
	 * @param names 映射数据球队的名称
	 */
	protected static void getTeamIdsAndNames(PairMap<? extends Match, ? extends Match> matchPairs,
			List<String> tids, List<String> names)
	{
		//处理球队编号和球队名称等数据
		//在澳客网数据中，比赛是以球队名字进行存储
		//在足彩网中，比赛是以球队的编号进行存储
		for (Match sourceMatch : matchPairs.keySet())
		{
			Match match = matchPairs.get(sourceMatch);
			tids.add(match.getHomeid());
			tids.add(match.getClientid());
			
			names.add(sourceMatch.getHomeid());
			names.add(sourceMatch.getClientid());
		}
	}
}
