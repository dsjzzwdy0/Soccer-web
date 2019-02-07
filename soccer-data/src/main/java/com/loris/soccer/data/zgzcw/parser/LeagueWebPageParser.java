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

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.page.WebPage;
import com.loris.common.wrapper.TableRecords;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.constant.ZgzcwConstants;

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
		
		if(StringUtils.isEmpty(page.getParams().get(SoccerConstants.NAME_FIELD_MID)))
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
		
		Element element = document.select(".league .left .team_out .div-select").first();		
		//解析赛季信息
		parseSeasons(element);
		
		if(StringUtils.isEmpty(seasonInfo))
		{
			if(seasons.size() > 0)
			{
				seasonInfo = seasons.get(0).getSeason();
			}
			
			page2.setSeason(seasonInfo);
		}
		
		//解析球队列表情况
		Elements elements = document.select(".league .left .tongji_list");
		for (Element element2 : elements)
		{
			element = element2.select(">div").first();
			String name = element.text();
			//System.out.println("Parse the Teams: " + name);
			if("球队列表".equals(name))
			{
				//解析球队信息				
				parseTeams(element2);
			}
		}
		
		//解析比赛轮次
		element = document.select(".league .league_right .table_out .luncib").first();
		parseRound(element);
		return null;
	}

	
}
