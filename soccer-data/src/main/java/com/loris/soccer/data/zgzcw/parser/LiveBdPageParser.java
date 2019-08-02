/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LiveBdPageParser.java   
 * @Package com.loris.soccer.data.zgzcw.parser   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser;

import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.util.LotteryUtil;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractZgzcwWebPageParser;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.Match;

/**
 * @ClassName: LiveBdPageParser
 * @Description: (@Todo)
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LiveBdPageParser extends AbstractZgzcwWebPageParser
{
	/**
	 * @param acceptType
	 */
	public LiveBdPageParser()
	{
		super(ZgzcwConstants.PAGE_LIVE_BD);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		String issueno = null;
		
		Element issueEl = document.selectFirst(".top-chosse #matchSel");
		if(issueEl != null)
		{
			issueno = getSelectElementValue(issueEl);
		}
		
		DataList<Match> matchs = new DataList<>();
		DataList<IssueMatch> issueMatchs = new DataList<>();
		
		matchs.setOverwrite(false);
		issueMatchs.setOverwrite(true);
		
		results.put(SoccerConstants.SOCCER_DATA_MATCH_LIST, matchs);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST, issueMatchs);
		
		Element matchesElement = document.selectFirst(".bf-main .live-sta #matchTable tbody");
		if(matchesElement != null)
		{
			parseMatches(matchesElement, issueno, matchs, issueMatchs);
		}
		
		return results;
	}
	
	/**
	 * 解析比赛数据
	 * @param element
	 */
	protected void parseMatches(Element element, String issueno, List<Match> matchs, List<IssueMatch> issueMatchs)
	{
		Elements elements = element.select("tr");
		for (Element element2 : elements)
		{
			parseMatch(element2, issueno, matchs, issueMatchs);
		}
	}
	
	/**
	 * 解析比赛数据
	 * @param element
	 */
	protected void parseMatch(Element element, String issueno, List<Match> matchs, List<IssueMatch> issueMatchs)
	{
		String ordinary;
		String mid;
		String lid;
		String homeid;
		String clientid;
		Date matchtime;
		
		Elements es = element.children();
		
		mid = element.attr("matchid");
		ordinary = es.get(0).text();
		matchtime = DateUtil.tryToParseDate(es.get(3).attr("date"));
		lid = parseLeagueid(es.get(1));
		homeid = getTeamid(es.get(5));
		clientid = getTeamid(es.get(7));
		
		if(matchtime == null) return;
		
		IssueMatch issueMatch = new IssueMatch();
		Match match = new Match();
		
		match.setHomeid(homeid);
		match.setClientid(clientid);
		match.setLid(lid);
		match.setMatchtime(matchtime);
		match.setMid(mid);
		
		issueMatch.setOrdinary(ordinary);
		issueMatch.setMid(mid);
		issueMatch.setMatchtime(matchtime);
		issueMatch.setIssueno(issueno);
		issueMatch.setIssue(LotteryUtil.getLotteryIssue(matchtime));
		
		issueMatchs.add(issueMatch);
		matchs.add(match);
	}
	
	/**
	 * 获得球队的编号
	 * @param element
	 * @return
	 */
	protected String getTeamid(Element element)
	{
		Element teamInfo = element.selectFirst("a");
		String teamid = getLastNumberValue(teamInfo.attr("href"));
		return teamid;
	}
	
	/**
	 * 解析比赛的联赛信息
	 * @param match
	 * @param element
	 */
	protected String parseLeagueid(Element element)
	{
		Element el = element.selectFirst("a");
		String leagueInfo = el.attr("href");
		leagueInfo = getLastNumberValue(leagueInfo);
		return leagueInfo;
	}

	/**
	 * @param issueEl
	 * @return
	 */
	private String getSelectElementValue(Element issueEl)
	{
		Elements elements = issueEl.children();
		if (elements == null || elements.size() == 0)
		{
			return "";
		}
		for (Element el : elements)
		{
			if (el.hasAttr("selected"))
			{
				return el.text();
			}
		}
		return elements.get(0).text();
	}
	
	/**
	 * Get the id value.
	 * 
	 * @param url
	 * @return
	 */
	public String getLastNumberValue(String url)
	{
		String[] values = url.split(RIGHT_SPLASH);
		int size = values.length;
		for (int i = size - 1; i >= 0; i--)
		{
			String id = values[i];
			if (NumberUtil.isNumber(id))
			{
				return id;
			}
		}
		return "";
	}
}
