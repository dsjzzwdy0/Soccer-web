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
package com.loris.soccer.plugin.zgzcw.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;

import static com.loris.soccer.constant.SoccerConstants.*;

import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Logo;
import com.loris.soccer.plugin.zgzcw.parser.base.AbstractZgzcwWebPageParser;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwConstants;

/**   
 * @ClassName:  LeagueCenterPageParser   
 * @Description: 主页数据的解析器，解析的内容包括两部分：
 *      1. 洲际、国家的联赛、杯赛等数据
 * 		2. 每个联赛的图标数据
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class CenterPageParser extends AbstractZgzcwWebPageParser
{
	/**
	 * Create a new instance of LeagueMainPageParser.
	 */
	public CenterPageParser()
	{
		super(ZgzcwConstants.PAGE_CENTER);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, 
	 * org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		Elements gamesContentMatcheses = document.select(".liansai .wrapper .mainbottom .gamesContent");
		LeagueList leagues = new LeagueList();
		List<Logo> logos = new ArrayList<>();
		
		for (Element element : gamesContentMatcheses)
		{
			Element element2 = element.select(".mbl").first();
			{
				parseCountryLeagues(element2, leagues, logos);
			}

			Element element3 = element.select(".mbr").first();
			{
				parseCupLeagues(element3, leagues, logos);
			}
		}		
		results.put(SOCCER_DATA_LEAGUE_LIST, leagues);
		results.put(SOCCER_DATA_LOGO_LIST, logos);
		return results;
	}
	
	/**
	 * 解析洲际杯赛内容
	 * 
	 * @param element
	 */
	protected void parseCupLeagues(Element element, List<League> leagues, List<Logo> logos)
	{
		String continent;
		Element contType = element.select("span").first();
		continent = contType.text();

		Elements lianTypes = element.select(".bs a");
		this.parseRecords(lianTypes, continent, "", leagues, logos);
	}
	
	/**
	 * 解析国内赛事内容
	 * 
	 * @param element
	 */
	protected void parseCountryLeagues(Element element, List<League> leagues, List<Logo> logos)
	{
		String continent;
		String country;
		Element contType = element.select("span").first();
		continent = contType.text();
		
		Elements nationElements = element.select(".ls > .lslogo");
		for (Element element3 : nationElements)
		{
			Element nationType = element3.select(".lstitle").first();
			country = nationType.text();
									
			Elements lianTypes = element3.select("a");
			this.parseRecords(lianTypes, continent, country, leagues, logos);
		}
	}
	
	/**
	 * 解析联赛数据
	 * @param lianTypes 元素列表
	 * @param continent
	 * @param country
	 * @param leagues
	 * @param logos
	 */
	protected void parseRecords(Elements lianTypes, String continent, String country, List<League> leagues, List<Logo> logos)
	{
		String url;
		String name;
		for (Element element : lianTypes)
		{
			url = element.attr("href");
			name = element.text();
			
			League league = createLeague(continent, country, name, url);			
			if(league == null)
			{
				continue;
			}
			
			leagues.add(league);
			Logo logo = parseLogo(element, league.getLid(), SoccerConstants.LOGO_TYPE_LEAGUE);
			if(logo != null)
			{
				logos.add(logo);
			}
		}
	}
	
	/**
	 * 解析数据的LOGO值
	 * @param element
	 * @param lid
	 * @param leagueType
	 * @return
	 */
	protected Logo parseLogo(Element element, String lid, String leagueType)
	{
		Element element2 = element.selectFirst("img");
		if(element2 != null)
		{			
			Logo logo = new Logo();
			logo.setPid(lid);
			logo.setType(SoccerConstants.LOGO_TYPE_LEAGUE);
			logo.setUrl(element2.attr("src"));
			return logo;
		}
		return null;
	}
	
	
	/**
	 * 创建联赛数据内容
	 * @param continent
	 * @param country
	 * @param name
	 * @param url
	 */
	protected League createLeague(String continent, String country, String name, String url)
	{
		League league = new League();
		if(!parseLeagueType(league, url))
		{
			return null;
		}
				
		league.setContinent(continent);
		league.setCountry(country);
		league.setName(name);
		league.setIntroduction("");
		
		return league;
	}
	
	
	
	/**
	 * "/soccer/league/160"
	 * @param url
	 * @return
	 */
	protected boolean parseLeagueType(League league, String url)
	{
		String[] values = url.split(RIGHT_SPLASH);
		if(values == null || values.length < 3)
		{
			return false;
		}
		
		if(values.length == 4)
		{
			league.setType(values[2]);
			league.setLid(values[3]);
		}
		else
		{
			league.setType(values[1]);
			league.setLid(values[2]);
		}
		
		//不是杯赛数据、联赛数据，不需要这样的内容
		if(league.getType() == null || 
			(!league.getType().equalsIgnoreCase(MATCH_TYPE_CUP) &&
			!league.getType().equalsIgnoreCase(MATCH_TYPE_LEAGUE)))
		{
			return false;
		}
		return true;
	}
}
