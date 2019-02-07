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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.page.WebPage;
import com.loris.common.wrapper.TableRecords;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.constant.ZgzcwConstants;
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
public class CupWebPageParser extends AbstractZgzcwWebPageParser
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
		
		Element element = null;
		
		if(StringUtils.isEmpty(season))
		{
			//解析得到当前的赛季信息
		}
		
		List<Team> teams = new ArrayList<>();
		List<TeamRfSeason> teamRfSeasons = new ArrayList<>();
		
		// 解析球队信息
		Elements elements = document.select(".league .left .tongji_list");
		for (Element element2 : elements)
		{
			element = element2.select(">div").first();
			String name = element.text();
			if ("球队列表".equals(name))
			{
				// 解析球队信息
				parseTeams(element2);
			}
		}

		// 解析比赛轮次信息
		element = document.selectFirst(".league .league_right .table_head #tabs9");
		parseRound(element);

		// System.out.println(rounds.size() + ": " + rounds);

		// 解析比赛信息
		elements = document.select(".league .league_right .cupBlock01 .cup .tabs9_main1");
		// System.out.println("Matches Elements Size: " + elements.size());
		parseMatches(elements);
		return results;
	}

}
