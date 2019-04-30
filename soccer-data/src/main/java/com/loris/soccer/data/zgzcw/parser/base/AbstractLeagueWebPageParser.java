/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  AbstractLeagueWebPageParser.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser.base;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.Logo;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.Season;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.TeamRfSeason;
import com.loris.soccer.model.Logo.LogoType;

/**   
 * @ClassName:  AbstractLeagueWebPageParser  
 * @Description: 联赛网页的基础解析类
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractLeagueWebPageParser extends AbstractZgzcwWebPageParser
{
	/**
	 * Create a new instance of AbstractLeagueWebPageParser
	 * @param acceptType
	 */
	public AbstractLeagueWebPageParser(String acceptType)
	{
		super(acceptType);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#accept(com.loris.client.model.WebPage)
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
	 * parse the seasons.
	 * 
	 * @param element
	 */
	protected void parseSeasonInfos(Element document, String lid, List<Season> seasons)
	{
		//解析赛季信息
		Element element = document.selectFirst(".league .left .team_out .div-select");	
		if(element == null)
		{
			return;
		}
		String seasonInfo = "";
		Elements els = element.select("a");
		for (Element el : els)
		{
			seasonInfo = el.select("li").first().text();
			Season s = new Season();
			s.setLid(lid);
			s.setSeason(seasonInfo);
			seasons.add(s);
		}
	}
	
	/**
	 * 解析球队信息
	 * @param document
	 * @param lid
	 * @param season
	 * @param teams
	 * @param teamRfSeasons
	 */
	protected void parseTeams(Document document, String lid, String season, List<Team> teams, List<TeamRfSeason> teamRfSeasons)
	{
		Elements elements = document.select(".league .left .tongji_list");
		for (Element element2 : elements)
		{
			Element element = element2.select(">div").first();
			String name = element.text();
			if("球队列表".equals(name))
			{
				//解析球队信息				
				parseTeams(element2, lid, season, teams, teamRfSeasons);
			}
		}		
	}
	
	/**
	 * 解析球队信息与数据
	 * @param element
	 * @param lid
	 * @param season
	 * @param teams
	 * @param teamRfSeasons
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
		
		String score = elements.get(2).text();
		String clientTeamUrl = elements.get(3).selectFirst("a").attr("href");
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
		
		if(logos != null)
		{			
			Logo homeLogo = new Logo();
			//homeLogo.setType(SoccerConstants.LOGO_TYPE_TEAM);
			homeLogo.setLogotype(LogoType.Team);
			homeLogo.setPid(homeid);
			parseLogoUrl(elements.get(1), homeLogo);
			logos.add(homeLogo);
			
			Logo clientLogo = new Logo();
			//clientLogo.setType(SoccerConstants.LOGO_TYPE_TEAM);
			clientLogo.setLogotype(LogoType.Team);
			clientLogo.setPid(clientid);
			parseLogoUrl(elements.get(3), clientLogo);
			logos.add(clientLogo);
		}
	}

	/**
	 * 解析图标地址
	 * @param element
	 * @param logo
	 */
	private	void parseLogoUrl(Element element, Logo logo)
	{
		try
		{
			String clientLogoUrl = element.selectFirst("img").attr("src");
			logo.setUrl(clientLogoUrl);
		}
		catch (Exception e) {
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
