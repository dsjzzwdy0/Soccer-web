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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loris.common.model.TableRecords;
import com.loris.soccer.collection.BetBdOddsList;
import com.loris.soccer.collection.BetJcOddsList;
import com.loris.soccer.collection.CasinoCompList;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.collection.LogoList;
import com.loris.soccer.collection.IssueMatchList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.collection.MatchResultList;
import com.loris.soccer.collection.OddsNumList;
import com.loris.soccer.collection.OddsOpList;
import com.loris.soccer.collection.OddsScoreList;
import com.loris.soccer.collection.OddsYpList;
import com.loris.soccer.collection.RankList;
import com.loris.soccer.collection.RecordOddsOpList;
import com.loris.soccer.collection.RecordOddsYpList;
import com.loris.soccer.collection.RoundList;
import com.loris.soccer.collection.SeasonList;
import com.loris.soccer.collection.TeamList;
import com.loris.soccer.collection.TeamRfSeasonList;
import com.loris.soccer.model.BetBdOdds;
import com.loris.soccer.model.BetJcOdds;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Logo;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.OddsNum;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsScore;
import com.loris.soccer.model.OddsYp;
import com.loris.soccer.model.Rank;
import com.loris.soccer.model.RecordOddsOp;
import com.loris.soccer.model.RecordOddsYp;
import com.loris.soccer.model.Round;
import com.loris.soccer.model.Season;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.TeamRfSeason;
import com.loris.soccer.service.BetOddsService;
import com.loris.soccer.service.CompService;
import com.loris.soccer.service.DataService;
import com.loris.soccer.service.LeagueService;
import com.loris.soccer.service.MatchService;
import com.loris.soccer.service.OddsService;

import static com.loris.soccer.constant.SoccerConstants.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: League
 * @Description: 存储数据页面的服务接口实现类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service("soccerDataService")
public class SoccerDataServiceImpl implements DataService
{
	private static Logger logger = Logger.getLogger(SoccerDataServiceImpl.class);

	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private OddsService oddsService;
	
	@Autowired
	private CompService compService;
	
	@Autowired
	private BetOddsService betOddsService;

	/**
	 * 保存数据页面解析得到的内容
	 * 
	 * @see com.loris.soccer.service.DataService#saveTableRecords(com.loris.common.model.TableRecords)
	 */
	@Override
	@Transactional
	public boolean saveTableRecords(TableRecords results)
	{
		for (String key : results.keySet())
		{
			try
			{
				saveRecord(key, results.get(key));
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.warn("Error occured when save '" + key + "' record.");
			}
		}
		return true;
	}
	
	/**
	 * 保存数据记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean saveRecord(String key, Object value)
	{
		boolean overwrite = true;
		switch (key)
		{
		case SOCCER_DATA_LEAGUE_LIST:
			List<League> leagues = null;
			if(value instanceof LeagueList)
			{
				leagues = (LeagueList) value;
				overwrite = ((LeagueList)leagues).isOverwrite();
			}
			else
			{
				leagues = (List<League>) value;				
			}
			return leagueService.insertLeagues(leagues, overwrite);
		case SOCCER_DATA_LOGO_LIST:
			List<Logo> logos = null;
			if(value instanceof LogoList)
			{
				logos = (LogoList)value;
				overwrite = ((LogoList)value).isOverwrite();
			}
			else {
				logos = (List<Logo>)value;
			}
			return leagueService.insertLogos(logos, overwrite);
		case SOCCER_DATA_LEAGUE_ROUND_LIST:
			List<Round> rounds = null;
			if(value instanceof RoundList)
			{
				rounds = (RoundList)value;
				overwrite = ((RoundList)value).isOverwrite();
			}
			else {
				rounds = (List<Round>)value;
			}
			return leagueService.insertRounds(rounds, overwrite);
		case SOCCER_DATA_LEAGUE_SEASON_LIST:
			List<Season> seasons = null;
			if(value instanceof SeasonList)
			{
				seasons = (SeasonList)value;
				overwrite = ((SeasonList)value).isOverwrite();
			}
			else {
				seasons = (List<Season>)value;
			}
			return leagueService.insertSeasons(seasons, overwrite);
		case SOCCER_DATA_TEAM_LIST:
			List<Team> teams = null;
			if(value instanceof TeamList)
			{
				teams = (TeamList)value;
				overwrite = ((TeamList)value).isOverwrite();
			}
			else {
				teams = (List<Team>)value;
			}
			return leagueService.insertTeams(teams, overwrite);
		case SOCCER_DATA_TEAM_SEASON_RELATION:
			List<TeamRfSeason> teamRfSeasons = null;
			if(value instanceof TeamRfSeasonList)
			{
				teamRfSeasons = (TeamRfSeasonList) value;
				overwrite = ((TeamRfSeasonList)value).isOverwrite();
			}
			else {
				teamRfSeasons = (List<TeamRfSeason>)value;
			}
			return leagueService.insertTeamRfSeasons(teamRfSeasons, overwrite);
		case SOCCER_DATA_LEAGUE_RANK_LIST:
			List<Rank> ranks = null;
			if(value instanceof RankList)
			{
				ranks = (RankList) value;
				overwrite = ((RankList)value).isOverwrite();
			}
			else
			{
				ranks = (List<Rank>)value;
			}
			return leagueService.insertRanks(ranks, overwrite);
		case SOCCER_DATA_MATCH_LIST:
			List<Match> matchs = null;
			if(value instanceof MatchList)
			{
				matchs = (MatchList) value;
				overwrite = ((MatchList) value).isOverwrite();
			}
			else {
				matchs = (List<Match>)value;
			}
			return matchService.insertMatchs(matchs, overwrite);
		case SOCCER_DATA_MATCH_BD_LIST:
		case SOCCER_DATA_MATCH_JC_LIST:
			List<IssueMatch> matchBds = null;
			if(value instanceof IssueMatchList)
			{			
				IssueMatchList matchBdItemList = (IssueMatchList) value;
				matchBds = new ArrayList<>();
				for (IssueMatch matchBd : matchBdItemList)
				{
					matchBds.add((IssueMatch)matchBd);
				}
				overwrite = matchBdItemList.isOverwrite();
			}
			else
			{
				matchBds = (List<IssueMatch>)value;
			}
			return matchService.insertIssueMatchs(matchBds, overwrite);
		case SOCCER_DATA_MATCH_RESULT_LIST:
			List<MatchResult> matchResults = null;
			if(value instanceof MatchResultList)
			{
				matchResults = (MatchResultList) value;
				overwrite = ((MatchResultList) value).isOverwrite();
			}
			else
			{
				matchResults = (List<MatchResult>)value;
			}
			return matchService.insertMatchResults(matchResults, overwrite);
		case SOCCER_DATA_ODDS_OP_LIST:
			List<OddsOp> ops = null;
			if(value instanceof OddsOpList)
			{
				ops = (OddsOpList) value;
				overwrite = ((OddsOpList)value).isOverwrite();
			}
			else {
				ops = (List<OddsOp>)value;
			}
			return oddsService.insertOddsOps(ops, overwrite);
		case SOCCER_DATA_ODDS_YP_LIST:
			List<OddsYp> yps = null;
			if(value instanceof OddsYpList)
			{
				yps = (OddsYpList)value;
				overwrite = ((OddsYpList)value).isOverwrite();
			}
			else {
				yps = (List<OddsYp>)value;				
			}
			return oddsService.insertOddsYps(yps, overwrite);
		case SOCCER_DATA_RECORD_ODDS_OP_LIST:
			List<RecordOddsOp> recordOddsOps = (List<RecordOddsOp>)value;
			overwrite = true;
			if(value instanceof RecordOddsOpList)
			{
				overwrite = ((RecordOddsOpList)value).isOverwrite();
			}
			return oddsService.insertRecordOddsOps(recordOddsOps, overwrite);
		case SOCCER_DATA_RECORD_ODDS_YP_LIST:
			List<RecordOddsYp> recordOddsYps = (List<RecordOddsYp>)value;
			overwrite = true;
			if(value instanceof RecordOddsYpList)
			{
				overwrite = ((RecordOddsYpList)value).isOverwrite();
			}
			return oddsService.insertRecordOddsYps(recordOddsYps, overwrite);
		case SOCCER_DATA_ODDS_NUM_LIST:
			List<OddsNum> nums = null;
			if(value instanceof OddsNumList)
			{
				nums = (OddsNumList)value;
				overwrite = ((OddsNumList)value).isOverwrite();
			}
			else {
				nums = (List<OddsNum>) value;
			}
			return oddsService.insertOddsNums(nums, overwrite);
		case SOCCER_DATA_ODDS_SCORE_LIST:
			List<OddsScore> scores = null;
			if(value instanceof OddsScoreList)
			{
				scores = (OddsScoreList)value;
				overwrite = ((OddsScoreList)value).isOverwrite();
			}
			else
			{
				scores = (List<OddsScore>) value;
			}
			return oddsService.insertOddsScores(scores, overwrite);
		case SOCCER_DATA_CASINO_COMP_LIST:
			List<CasinoComp> comps = null;
			if(value instanceof CasinoCompList)
			{
				comps = (CasinoCompList)value;
				overwrite = ((CasinoCompList)value).isOverwrite();
			}
			else 
			{
				comps = (List<CasinoComp>)value;
			}
			return compService.insertCasinoComps(comps, overwrite);
		case SOCCER_DATA_BETODDS_BD_LIST:
			List<BetBdOdds> betBdOdds = null;
			if(value instanceof BetBdOddsList)
			{
				betBdOdds = (BetBdOddsList)value;
				overwrite = ((BetBdOddsList)value).isOverwrite();
			}
			else 
			{
				betBdOdds = (List<BetBdOdds>)value;
			}
			return betOddsService.insertBetBdOdds(betBdOdds, overwrite);
		case SOCCER_DATA_BETODDS_JC_LIST:
			List<BetJcOdds> betJcOdds = null;
			if(value instanceof BetJcOddsList)
			{
				betJcOdds = (BetJcOddsList)value;
				overwrite = ((BetJcOddsList)value).isOverwrite();
			}
			else 
			{
				betJcOdds = (List<BetJcOdds>)value;
			}
			return betOddsService.insertBetJcOdds(betJcOdds, overwrite);
		default:
			// No nothing.
			logger.warn("Warn: The Data '" + key + "' will not be saved into databases.");
			return false;
		}
	}
}
