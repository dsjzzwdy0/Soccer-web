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
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.page.WebPage;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.common.wrapper.TableRecords;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.constant.ZgzcwConstants;
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
public class LeagueWebPageParser extends AbstractZgzcwWebPageParser
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
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#accept(com.loris.client.page.WebPage)
	 */
	@Override
	protected boolean accept(WebPage page) throws WebParserException
	{
		if(!super.accept(page))
		{
			return false;
		}
		
		if(StringUtils.isEmpty(page.getParams().get(SoccerConstants.NAME_FIELD_LID)))
		{
			return false;
		}
		return true;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.page.WebPage, 
	 * 		org.jsoup.nodes.Document, com.loris.common.wrapper.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		String lid = page.getParams().get(SoccerConstants.NAME_FIELD_LID);
		String season = page.getParams().get(SoccerConstants.NAME_FIELD_SEASON);
		String round = null;
		
		Element element = null;
		
		if(StringUtils.isEmpty(season))
		{
			//解析赛季信息
			element = document.select(".league .left .team_out .div-select").first();		
			season = parseFirstSeasonInfo(element);
			if(StringUtils.isEmpty(season))
			{
				throw new WebParserException("The WebPage is not a validate LeagueWebPage, there are no season value defined.");
			}
		}
		
		List<Team> teams = new ArrayList<>();
		List<TeamRfSeason> teamRfSeasons = new ArrayList<>();
		List<Round> rounds = new ArrayList<>();
		List<Match> matchs = new ArrayList<>();
		List<MatchResult> matchResults = new ArrayList<>();
		List<Logo> logos = new ArrayList<>();
		List<Rank> ranks = new ArrayList<>();

		//解析球队列表情况
		Elements elements = document.select(".league .left .tongji_list");
		for (Element element2 : elements)
		{
			element = element2.select(">div").first();
			String name = element.text();
			if("球队列表".equals(name))
			{
				//解析球队信息				
				parseTeams(element2, lid, season, teams, teamRfSeasons);
			}
		}
		
		//解析比赛轮次数据，并且得到当前的轮次
		element = document.selectFirst(".league .league_right .table_out .league_right_021 .luncib");
		round = parseRounds(element, lid, season, rounds);
		
		//解析比赛数据
		element = document.selectFirst(".league .league_right .table_out #matchInfo table");
		parseMatchs(element, lid, season, round, matchs, matchResults, logos);
		
		// 球队的排名情况
		element = document.selectFirst(".league .league_right #hideList .table_out");
		parseRanks(element, lid, season, round, ranks);
		
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
	protected void parseMatchs(Element element, String lid, String season, String round, 
			List<Match> matchs, List<MatchResult> matchResults, List<Logo> logos)
	{
		Elements elements = element.select("tbody tr");
		for (Element e : elements)
		{
			parseMatch(e, lid, season, round, matchs, matchResults, logos);
		}
	}
	
	/**
	 * 解析比赛信息
	 * @param element
	 * @param lid
	 * @param season
	 * @param round
	 * @return
	 */
	protected void parseMatch(Element element, String lid, String season, String round, 
			List<Match> matchs, List<MatchResult> matchResults, List<Logo> logos)
	{
		Elements elements = element.select("td");
		if(elements.size() < 7)
		{
			return;
		}
		
		String matchtime = elements.get(0).text();
		String homeTeamUrl = elements.get(1).selectFirst("a").attr("href");
		String homeLogoUrl = elements.get(1).selectFirst("img").attr("src");
		String score = elements.get(2).text();
		String clientTeamUrl = elements.get(3).selectFirst("a").attr("href");
		String clientLogoUrl = elements.get(3).selectFirst("img").attr("src");
		String midUrl = elements.get(6).selectFirst("a").attr("href");
		String mid = NumberUtil.parseFirstIntegerString(midUrl);
		
		Date time = DateUtil.tryToParseDate(matchtime);
		String homeid = getTeamId(homeTeamUrl);
		String clientid = getTeamId(clientTeamUrl);
		
		Match match = new Match(mid);
		match.setHomeid(homeid);
		match.setClientid(clientid);
		match.setMatchtime(time);
		match.setLid(lid);
		match.setSeason(season);
		match.setRound(round);
		matchs.add(match);
		
		if(MatchResult.validateScore(score))
		{
			MatchResult result = new MatchResult(mid, score);
			matchResults.add(result);
		}
		
		Logo homeLogo = new Logo();
		homeLogo.setType(SoccerConstants.LOGO_TYPE_TEAM);
		homeLogo.setPid(homeid);
		homeLogo.setUrl(homeLogoUrl);
		logos.add(homeLogo);
		
		Logo clientLogo = new Logo();
		clientLogo.setType(SoccerConstants.LOGO_TYPE_TEAM);
		clientLogo.setPid(clientid);
		clientLogo.setUrl(clientLogoUrl);
		logos.add(clientLogo);
	}
	
	/**
	 * 解析球队排名
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
	protected void parseRanks(Element element, String lid, String season, String round, List<Rank> ranks)
	{
		// 球队的排名情况
		Elements elements = element.select(".tabs1_main_ul");
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
	 * parse the seasons.
	 * 
	 * @param element
	 */
	protected String parseFirstSeasonInfo(Element element)
	{
		if(element == null)
		{
			return "";
		}
		String season;
		Element el = element.select("a").first();
		season = el.select("li").first().text();
		return season;
	}
	
	/**
	 * Parse the round info.
	 * 
	 * @param element
	 */
	protected String parseRounds(Element element, String lid, String season, List<Round> rounds)
	{
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
	
	/**
	 * parse the teams.
	 * 
	 * @param element
	 */
	protected void parseTeams(Element element, String lid, String season, List<Team> teams, List<TeamRfSeason> teamRfSeasons)
	{
		if(element == null)
		{
			return;
		}
		String name;
		String url;
		Elements elements = element.select("a");
		for (Element element2 : elements)
		{
			url = element2.attr("href");
			name = element2.select("li").first().text();
			
			String tid = getTeamId(url);
			
			//球队信息
			Team team = new Team();
			team.setName(name);
			team.setTid(tid);		
			teams.add(team);
			
			//赛季的信息
			TeamRfSeason seasonTeam = new TeamRfSeason();
			seasonTeam.setTid(tid);
			seasonTeam.setSeason(season);
			seasonTeam.setLid(lid);
			teamRfSeasons.add(seasonTeam);
		}
	}
	
	
	/**
	 * Get the TeamId value.
	 * @param url
	 * @return
	 */
	protected String getTeamId(String url)
	{
		String[] values = url.split(RIGHT_SPLASH);
		int size = values.length;
		String tid = values[size - 1];
		return tid;
	}
}
