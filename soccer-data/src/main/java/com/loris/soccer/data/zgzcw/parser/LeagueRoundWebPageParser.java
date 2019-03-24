/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LeagueRoundWebPageParser.java   
 * @Package com.loris.soccer.data.zgzcw.parser   
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
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.soccer.collection.LogoList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.collection.MatchResultList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractLeagueWebPageParser;

/**   
 * @ClassName: LeagueRoundWebPageParser   
 * @Description: 解析联赛类型轮次比赛数据页面
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class LeagueRoundWebPageParser extends AbstractLeagueWebPageParser
{
	/**
	 * Create a new instance of LeagueRoundWebPageParser
	 */
	public LeagueRoundWebPageParser()
	{
		super(ZgzcwConstants.PAGE_LEAGUE_LEAGUE_ROUND);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#accept(com.loris.client.model.WebPage)
	 */
	@Override
	protected boolean accept(WebPage page) throws WebParserException
	{
		if(!ZgzcwConstants.SOURCE_ZGZCW.equalsIgnoreCase(page.getSource()))
		{
			return false;
		}
		
		if(StringUtils.isBlank(acceptType) || !acceptType.equalsIgnoreCase(page.getType()))
		{
			return false;
		}
		
		if(StringUtils.isBlank(page.getParam(ZgzcwConstants.NAME_FIELD_SOURCE_LID))
				|| StringUtils.isBlank(page.getParam(ZgzcwConstants.NAME_FIELD_CUR_ROUND))
				|| StringUtils.isBlank(page.getParam(ZgzcwConstants.NAME_FIELD_SEASON)))
		{
			return false;
		}
		return true;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		Elements elements = document.select(".zstab tbody tr");
		if(elements == null) throw new WebParserException("The document has no matches data.");
		
		String lid = page.getParam(ZgzcwConstants.NAME_FIELD_SOURCE_LID);
		String season = page.getParam(ZgzcwConstants.NAME_FIELD_SEASON);
		String round = page.getParam(ZgzcwConstants.NAME_FIELD_CUR_ROUND);
		
		MatchList matchs = new MatchList();
		MatchResultList matchResults = new MatchResultList();
		LogoList logos = new LogoList();
		matchs.setOverwrite(true);
		matchResults.setOverwrite(true);
		
		for (Element e : elements)
		{
			parseMatch(e, lid, season, round, matchs, matchResults, logos);
		}
		
		results.put(SoccerConstants.SOCCER_DATA_MATCH_LIST, matchs);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST, matchResults);
		results.put(SoccerConstants.SOCCER_DATA_LOGO_LIST, logos);
		
		return results;
	}
}
