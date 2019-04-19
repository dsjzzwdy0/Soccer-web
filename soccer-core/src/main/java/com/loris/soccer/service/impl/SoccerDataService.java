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
import com.loris.common.service.DataService;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.collection.LogoList;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.collection.MatchResultList;
import com.loris.soccer.collection.OddsNumList;
import com.loris.soccer.collection.OddsOpList;
import com.loris.soccer.collection.OddsScoreList;
import com.loris.soccer.collection.OddsYpList;
import com.loris.soccer.collection.RankList;
import com.loris.soccer.collection.RoundList;
import com.loris.soccer.collection.TeamList;
import com.loris.soccer.collection.TeamRfSeasonList;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.MatchJc;
import com.loris.soccer.model.base.MatchItem;
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
public class SoccerDataService implements DataService
{
	private static Logger logger = Logger.getLogger(SoccerDataService.class);

	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private OddsService oddsService;

	/**
	 * 保存数据页面解析得到的内容
	 * 
	 * @see com.loris.common.service.DataService#saveTableRecords(com.loris.common.model.TableRecords)
	 */
	@Override
	@Transactional
	public boolean saveTableRecords(TableRecords results)
	{
		for (String key : results.keySet())
		{
			switch (key)
			{
			case SOCCER_DATA_LEAGUE_LIST:
				LeagueList leagues = (LeagueList) results.get(key);
				leagueService.insertLeagues(leagues, leagues.isOverwrite());
				break;
			case SOCCER_DATA_LOGO_LIST:
				LogoList logos = (LogoList) results.get(key);
				leagueService.insertLogos(logos, logos.isOverwrite());
				break;
			case SOCCER_DATA_ROUND_LIST:
				RoundList rounds = (RoundList) results.get(key);
				leagueService.insertRounds(rounds, rounds.isOverwrite());
				break;
			case SOCCER_DATA_TEAM_LIST:
				TeamList teams = (TeamList) results.get(key);
				leagueService.insertTeams(teams, teams.isOverwrite());
				break;
			case SOCCER_DATA_TEAM_SEASON:
				TeamRfSeasonList rfSeasons = (TeamRfSeasonList) results.get(key);
				leagueService.insertTeamRfSeasons(rfSeasons, rfSeasons.isOverwrite());
				break;
			case SOCCER_DATA_RANK_LIST:
				RankList ranks = (RankList) results.get(key);
				leagueService.insertRanks(ranks, ranks.isOverwrite());
				break;
			case SOCCER_DATA_MATCH_LIST:
				MatchList matchList = (MatchList) results.get(key);
				matchService.insertMatchs(matchList, matchList.isOverwrite());
				break;
			case SOCCER_DATA_MATCH_BD_LIST:
				MatchItemList matchBdItemList = (MatchItemList) results.get(key);
				List<MatchBd> matchBds = new ArrayList<>();
				for (MatchItem matchBd : matchBdItemList)
				{
					matchBds.add((MatchBd)matchBd);
				}
				matchService.insertMatchBds(matchBds, matchBdItemList.isOverwrite());
				break;
			case SOCCER_DATA_MATCH_JC_LIST:
				MatchItemList matchJcItemList = (MatchItemList) results.get(key);
				List<MatchJc> matchJcs = new ArrayList<>();
				for (MatchItem matchJc : matchJcItemList)
				{
					matchJcs.add((MatchJc)matchJc);
				}
				matchService.insertMatchJcs(matchJcs, matchJcItemList.isOverwrite());
				break;
			case SOCCER_DATA_MATCH_RESULT_LIST:
				MatchResultList matchResults = (MatchResultList) results.get(key);
				matchService.insertMatchResults(matchResults, matchResults.isOverwrite());
				break;
			case SOCCER_DATA_OP_LIST:
				OddsOpList ops = (OddsOpList) results.get(key);
				oddsService.insertOddsOps(ops, ops.isOverwrite());
				break;
			case SOCCER_DATA_YP_LIST:
				OddsYpList yps = (OddsYpList) results.get(key);
				oddsService.insertOddsYps(yps, yps.isOverwrite());
				break;
			case SOCCER_DATA_NUM_LIST:
				OddsNumList nums = (OddsNumList) results.get(key);
				oddsService.insertOddsNums(nums, nums.isOverwrite());
				break;
			case SOCCER_DATA_SCORE_LIST:
				OddsScoreList scores = (OddsScoreList) results.get(key);
				oddsService.insertOddsScores(scores, scores.isOverwrite());
				break;
			default:
				// No nothing.
				logger.warn("Warn: The Data '" + key + "' will not be saved into databases.");
				break;
			}
		}
		return true;
	}
}
