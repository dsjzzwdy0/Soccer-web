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
import com.loris.soccer.collection.base.DataList;
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
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooOddsOp;
import com.loris.soccer.model.OkoooOddsYp;
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
import com.loris.soccer.service.OkoooDataService;

import static com.loris.soccer.constant.SoccerConstants.*;

import java.util.HashMap;
import java.util.List;


/**
 * @ClassName: League
 * @Description: 存储数据页面的服务接口实现类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
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
	
	@Autowired
	private OkoooDataService okoooDataService;
	
	
	private static HashMap<String, Boolean> defaultOverwrites = new HashMap<>();
	
	static
	{
		defaultOverwrites.put(SOCCER_DATA_ODDS_OKOOO_OP_LIST, Boolean.TRUE);
		defaultOverwrites.put(SOCCER_DATA_ODDS_OKOOO_YP_LIST, Boolean.TRUE);
		defaultOverwrites.put(SOCCER_DATA_ODDS_OP_LIST, Boolean.TRUE);
		defaultOverwrites.put(SOCCER_DATA_ODDS_YP_LIST, Boolean.TRUE);
		defaultOverwrites.put(SOCCER_DATA_BETODDS_BD_LIST, Boolean.TRUE);
		defaultOverwrites.put(SOCCER_DATA_BETODDS_JC_LIST, Boolean.TRUE);
		defaultOverwrites.put(SOCCER_DATA_LEAGUE_LIST, Boolean.FALSE);
	}

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
		boolean overwrite = getDefaultOverwrite(key);
		if(value instanceof DataList<?>)
		{
			overwrite = ((DataList<?>)value).isOverwrite();
		}
		
		switch (key)
		{
		case SOCCER_DATA_LEAGUE_LIST:
			List<League> leagues = (List<League>) value;		
			return leagueService.insertLeagues(leagues, overwrite);
		case SOCCER_DATA_LOGO_LIST:
			List<Logo> logos = (List<Logo>)value;
			return leagueService.insertLogos(logos, overwrite);
		case SOCCER_DATA_LEAGUE_ROUND_LIST:
			List<Round> rounds = (List<Round>)value;
			return leagueService.insertRounds(rounds, overwrite);
		case SOCCER_DATA_LEAGUE_SEASON_LIST:
			List<Season> seasons = (List<Season>)value;
			return leagueService.insertSeasons(seasons, overwrite);
		case SOCCER_DATA_TEAM_LIST:
			List<Team> teams = (List<Team>)value;
			return leagueService.insertTeams(teams, overwrite);
		case SOCCER_DATA_TEAM_SEASON_RELATION:
			List<TeamRfSeason> teamRfSeasons = (List<TeamRfSeason>)value;
			return leagueService.insertTeamRfSeasons(teamRfSeasons, overwrite);
		case SOCCER_DATA_LEAGUE_RANK_LIST:
			List<Rank> ranks = (List<Rank>)value;
			return leagueService.insertRanks(ranks, overwrite);
		case SOCCER_DATA_MATCH_LIST:
			List<Match> matchs = (List<Match>)value;
			return matchService.insertMatchs(matchs, overwrite);
		case SOCCER_DATA_MATCH_BD_LIST:
		case SOCCER_DATA_MATCH_JC_LIST:
			List<IssueMatch> matchBds = (List<IssueMatch>)value;
			return matchService.insertIssueMatchs(matchBds, overwrite);
		case SOCCER_DATA_MATCH_RESULT_LIST:
			List<MatchResult> matchResults = (List<MatchResult>)value;
			return matchService.insertMatchResults(matchResults, overwrite);
		case SOCCER_DATA_ODDS_OP_LIST:
			List<OddsOp> ops = (List<OddsOp>)value;
			return oddsService.insertOddsOps(ops, overwrite);
		case SOCCER_DATA_ODDS_YP_LIST:
			List<OddsYp> yps = (List<OddsYp>)value;				
			return oddsService.insertOddsYps(yps, overwrite);
		case SOCCER_DATA_RECORD_ODDS_OP_LIST:
			List<RecordOddsOp> recordOddsOps = (List<RecordOddsOp>)value;
			return oddsService.insertRecordOddsOps(recordOddsOps, overwrite);
		case SOCCER_DATA_RECORD_ODDS_YP_LIST:
			List<RecordOddsYp> recordOddsYps = (List<RecordOddsYp>)value;
			return oddsService.insertRecordOddsYps(recordOddsYps, overwrite);
		case SOCCER_DATA_ODDS_NUM_LIST:
			List<OddsNum> nums = (List<OddsNum>) value;
			return oddsService.insertOddsNums(nums, overwrite);
		case SOCCER_DATA_ODDS_SCORE_LIST:
			List<OddsScore> scores = (List<OddsScore>) value;
			return oddsService.insertOddsScores(scores, overwrite);
		case SOCCER_DATA_CASINO_COMP_LIST:
			List<CasinoComp> comps = (List<CasinoComp>)value;
			return compService.insertCasinoComps(comps, overwrite);
		case SOCCER_DATA_BETODDS_BD_LIST:
			List<BetBdOdds> betBdOdds = (List<BetBdOdds>)value;
			return betOddsService.insertBetBdOdds(betBdOdds, overwrite);
		case SOCCER_DATA_BETODDS_JC_LIST:
			List<BetJcOdds> betJcOdds = (List<BetJcOdds>)value;
			return betOddsService.insertBetJcOdds(betJcOdds, overwrite);
		case SOCCER_DATA_MATCH_OKOOO_LIST:
			List<OkoooMatch> okoooMatchs = (List<OkoooMatch>)value;			
			return okoooDataService.insertOkoooMatchs(okoooMatchs, overwrite);
		case SOCCER_DATA_LEAGUE_OKOOO_LIST:
			List<OkoooLeague> okoooLeagues = (List<OkoooLeague>)value;
			return okoooDataService.insertOkoooLeagues(okoooLeagues, overwrite);
		case SOCCER_DATA_MATCH_OKOOO_BD_LIST:
		case SOCCER_DATA_MATCH_OKOOO_JC_LIST:
			List<OkoooIssueMatch> okoooIssueMatchs = (List<OkoooIssueMatch>)value;
			return okoooDataService.insertOkoooIssueMatch(okoooIssueMatchs, overwrite);
		case SOCCER_DATA_ODDS_OKOOO_OP_LIST:
			List<OkoooOddsOp> okoooOddsOps = (List<OkoooOddsOp>)value;
			return okoooDataService.insertOkoooOddsOps(okoooOddsOps, overwrite);
		case SOCCER_DATA_ODDS_OKOOO_YP_LIST:
			List<OkoooOddsYp> okoooOddsYps = (List<OkoooOddsYp>)value;
			return okoooDataService.insertOkoooOddsYps(okoooOddsYps, overwrite);
		default:
			// No nothing.
			logger.warn("Warn: The Data '" + key + "' will not be saved into databases.");
			return false;
		}
	}
	
	/**
	 * 获得默认的是否覆盖的标志
	 * @param key 关键词
	 * @return 是否覆盖的标志
	 */
	protected boolean getDefaultOverwrite(String key)
	{
		Boolean b = defaultOverwrites.get(key);
		return (b == null) ? false : b;
	}
}
