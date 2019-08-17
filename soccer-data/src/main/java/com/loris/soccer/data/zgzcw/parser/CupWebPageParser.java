/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  CupWebPageParser.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.soccer.collection.SeasonList;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractLeagueWebPageParser;
import com.loris.soccer.model.Logo;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.Round;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.TeamRfSeason;

/**   
 * @ClassName:  CupWebPageParser  
 * @Description: 杯赛页面数据的解析器，解析的内容包括球队、比赛、结果等信息  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class CupWebPageParser extends AbstractLeagueWebPageParser
{
	/**
	 * Create a new instance of CupWebPageParser
	 */
	public CupWebPageParser()
	{
		super(ZgzcwConstants.PAGE_LEAGUE_CUP);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, 
	 * 		org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		String lid = page.getParams().get(SoccerConstants.NAME_FIELD_LID);
		String season = page.getParams().get(SoccerConstants.NAME_FIELD_SEASON);
				
		
		SeasonList seasons = new SeasonList();
		DataList<Team> teams = new DataList<>();
		DataList<TeamRfSeason> teamRfSeasons = new DataList<>();
		DataList<Round> rounds = new DataList<>();
		DataList<Match> matchs = new DataList<>();
		DataList<MatchResult> matchResults = new DataList<>();
		
		seasons.setOverwrite(false);
		matchs.setOverwrite(true);
		teams.setOverwrite(true);
		matchResults.setOverwrite(true);
		rounds.setOverwrite(true);
		
		parseSeasonInfos(document, lid, seasons);		
		if(StringUtils.isEmpty(season))
		{
			season = seasons.getLastSeason();
		}
		
		// 解析球队信息
		parseTeams(document, lid, season, teams, teamRfSeasons);

		// 解析比赛轮次信息
		parseRound(document, lid, season, rounds);

		// 解析比赛信息
		parseMatches(document, lid, season, rounds, matchs, matchResults, null);

		results.put(SoccerConstants.SOCCER_DATA_LEAGUE_SEASON_LIST, seasons);
		results.put(SoccerConstants.SOCCER_DATA_TEAM_LIST, teams);
		results.put(SoccerConstants.SOCCER_DATA_TEAM_SEASON_RELATION, teamRfSeasons);
		results.put(SoccerConstants.SOCCER_DATA_LEAGUE_ROUND_LIST, rounds);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_LIST, matchs);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST, matchResults);
		
		return results;
	}
	
	/**
	 * 解析比赛信息
	 * 
	 * @param elements
	 */
	protected void parseMatches(Document document, String lid, String season,  List<Round> rounds, 
			List<Match> matchs, List<MatchResult> matchResults, List<Logo> logos)
	{
		Elements elements = document.select(".league .league_right .cupBlock01 .cup .tabs9_main1");
		int size = elements.size();
		// System.out.println("Matches Elements Size: " + size);
		for (int i = 0; i < size; i++)
		{
			Round round = rounds.get(i);
			Element element = elements.get(i);
			Elements elements2 = element.select(".zstab");
			for (Element element2 : elements2)
			{
				Elements els = element2.select("tbody tr");
				for (Element e : els)
				{
					parseMatch(e, lid, season, round.getRound(), matchs, matchResults, logos);
				}
			}
		}
	}

	/**
	 * Parse the round info.
	 * 
	 * @param element
	 */
	protected void parseRound(Document document,String lid, String season, List<Round> rounds)
	{
		Element element = document.selectFirst(".league .league_right .table_head #tabs9");
		if (element == null)
		{
			return;
		}
		String name;
		Elements elements = element.select("li");
		for (Element element2 : elements)
		{
			name = element2.text();
			
			Round round = new Round();
			round.setLid(lid);
			round.setSeason(season);
			round.setRound(name);
			rounds.add(round);
		}
	}
}
