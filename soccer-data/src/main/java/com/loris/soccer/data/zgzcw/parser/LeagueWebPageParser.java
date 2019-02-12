/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LeagueWebPageParser.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.util.NumberUtil;
import com.loris.common.wrapper.TableRecords;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.constant.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractLeagueWebPageParser;
import com.loris.soccer.model.Logo;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.Rank;
import com.loris.soccer.model.Round;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.TeamRfSeason;

/**   
 * @ClassName:  LeagueWebPageParser  
 * @Description: 解析联赛类型的主页面
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class LeagueWebPageParser extends AbstractLeagueWebPageParser
{
	/**
	 * Create a new instance of LeagueWebPageParser
	 */
	public LeagueWebPageParser()
	{
		super(ZgzcwConstants.PAGE_LEAGUE_LEAGUE);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, 
	 * 		org.jsoup.nodes.Document, com.loris.common.wrapper.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		String lid = page.getParams().get(SoccerConstants.NAME_FIELD_LID);
		String season = page.getParams().get(SoccerConstants.NAME_FIELD_SEASON);
		String round = null;
		
		if(StringUtils.isEmpty(season))
		{
			season = parseFirstSeasonInfo(document);
		}
		
		List<Team> teams = new ArrayList<>();
		List<TeamRfSeason> teamRfSeasons = new ArrayList<>();
		List<Round> rounds = new ArrayList<>();
		List<Match> matchs = new ArrayList<>();
		List<MatchResult> matchResults = new ArrayList<>();
		List<Logo> logos = new ArrayList<>();
		List<Rank> ranks = new ArrayList<>();

		//解析球队列表情况
		parseTeams(document, lid, season, teams, teamRfSeasons);
		
		//解析比赛轮次数据，并且得到当前的轮次
		round = parseRounds(document, lid, season, rounds);
		
		//解析比赛数据		
		parseMatchs(document, lid, season, round, matchs, matchResults, logos);
		
		// 球队的排名情况
		parseRanks(document, lid, season, round, ranks);
		
		results.put(SoccerConstants.SOCCER_DATA_TEAM_LIST, teams);
		results.put(SoccerConstants.SOCCER_DATA_TEAM_SEASON, teamRfSeasons);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_LIST, matchs);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST, matchResults);
		results.put(SoccerConstants.SOCCER_DATA_ROUND_LIST, rounds);
		results.put(SoccerConstants.SOCCER_DATA_LOGO_LIST, logos);
		results.put(SoccerConstants.SOCCER_DATA_RANK_LIST, ranks);
		
		return results;
	}
	
	/**
	 * 解析比赛数据列表
	 * @param element DOM元素
	 * @param lid 联赛编号
	 * @param season 赛季编号
	 * @param round 轮次比赛数据
	 * @param matchs 比赛列表
	 */
	protected void parseMatchs(Document document, String lid, String season, String round, 
			List<Match> matchs, List<MatchResult> matchResults, List<Logo> logos)
	{
		Element element = document.selectFirst(".league .league_right .table_out #matchInfo table");
		Elements elements = element.select("tbody tr");
		for (Element e : elements)
		{
			parseMatch(e, lid, season, round, matchs, matchResults, logos);
		}
	}
	
	/**
	 * 解析球队排名
	 * 
	 * @param element
	 * @param type
	 * @param lid
	 * @param season
	 * @param round
	 * @param ranks
	 */
	protected void parseRanks(Element rankEls, String type, String lid, String season, String round, List<Rank> ranks)
	{
		Elements rankRecords = rankEls.select(".zstab tbody tr");
		for (Element element : rankRecords)
		{
			Elements recs = element.select("td");
			String no = recs.get(0).text();
	
			Element nameEl = recs.get(1).selectFirst("a");
			String tid = getTeamId(nameEl.attr("href"));
	
			String num = recs.get(2).text();
			String winnum = recs.get(3).text();
			String drawnum = recs.get(4).text();
			String losenum = recs.get(5).text();
			String goal = recs.get(6).text();
			String losegoal = recs.get(7).text();
			String score = recs.get(14).text();
			
			Rank rank = new Rank();
			rank.setType(type);
			rank.setTeamid(tid);
			rank.setLid(lid);
			rank.setSeason(season);
			rank.setRank(NumberUtil.parseInt(no));
			rank.setGamenum(NumberUtil.parseInt(num));
			rank.setWinnum(NumberUtil.parseInt(winnum));
			rank.setDrawnum(NumberUtil.parseInt(drawnum));
			rank.setLosenum(NumberUtil.parseInt(losenum));
			rank.setWingoal(NumberUtil.parseInt(goal));
			rank.setLosegoal(NumberUtil.parseInt(losegoal));
			rank.setScore(NumberUtil.parseInt(score));
			
			ranks.add(rank);
		}
	}
	
	
	/**
	 * 解析轮次球队排名数据
	 * @param element DOM元素 
	 * @param lid 联赛编号
	 * @param season
	 * @param round
	 * @param ranks
	 */
	protected void parseRanks(Document document, String lid, String season, String round, List<Rank> ranks)
	{
		// 球队的排名情况
		Elements elements = document.select(".league .league_right #hideList .table_out .tabs1_main_ul");
		
		String type = SoccerConstants.RANK_TOTAL;
		parseRanks(elements.get(0), type, lid, season, round, ranks);
		
		if(elements.size() == 6)
		{
			type = SoccerConstants.RANK_HOME;
			parseRanks(elements.get(1), type, lid, season, round, ranks);
			type = SoccerConstants.RANK_CLIENT;
			parseRanks(elements.get(2), type, lid, season, round, ranks);
			type = SoccerConstants.RANK_LATEST_6;
			parseRanks(elements.get(3), type, lid, season, round, ranks);
			type = SoccerConstants.RANK_FIRST_HALF;
			parseRanks(elements.get(4), type, lid, season, round, ranks);
			type = SoccerConstants.RANK_SECOND_HALF;
			parseRanks(elements.get(5), type, lid, season, round, ranks);
		}
	}
	
	/**
	 * Parse the round info.
	 * 
	 * @param element
	 */
	protected String parseRounds(Document document, String lid, String season, List<Round> rounds)
	{
		Element element = document.selectFirst(".league .league_right .table_out .league_right_021 .luncib");
		String curRound = "";
		String name;
		Elements elements = element.select("em");
		//System.out.println("轮次：");
		for (Element element2 : elements)
		{
			name = element2.text();
			
			Round round = new Round();
			round.setLid(lid);
			round.setSeason(season);
			round.setRound(name);
			rounds.add(round);
			
			if(element2.hasAttr("em_2"))
			{
				curRound = name;
			}
		}
		return curRound;
	}
	
	
}
