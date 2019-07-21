/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooJcPageParser.java   
 * @Package com.loris.soccer.data.okooo.parsercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.okooo.parser;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.parser.base.AbstractOkoooPageParser;
import com.loris.soccer.model.BetJcOdds;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooTeam;

/**   
 * @ClassName:  OkoooJcPageParser.java   
 * @Description: 澳客竞彩数据下载页面 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooJcPageParser extends AbstractOkoooPageParser
{
	private static Logger logger = Logger.getLogger(OkoooJcPageParser.class);
	
	/**
	 * @param acceptType
	 */
	public OkoooJcPageParser()
	{
		super(OkoooConstants.PAGE_LOTTERY_JC);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		Elements elements = document.select("#content .cont");
		if (elements == null || elements.size() <= 0)
		{
			return null;
		}
		
		DataList<OkoooIssueMatch> issueMatchs = new DataList<>();
		DataList<OkoooMatch> matchs = new DataList<>();
		DataList<OkoooLeague> leagues = new DataList<>();
		DataList<BetJcOdds> oddsList = new DataList<>();
		DataList<OkoooTeam> teams = new DataList<>();
		
		matchs.setOverwrite(false);
		leagues.setOverwrite(false);
		issueMatchs.setOverwrite(false);
		oddsList.setOverwrite(true);
		teams.setOverwrite(false);
		
		results.put(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_JC_LIST, issueMatchs);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_LIST, matchs);
		results.put(SoccerConstants.SOCCER_DATA_LEAGUE_OKOOO_LIST, leagues);
		results.put(SoccerConstants.SOCCER_DATA_BETODDS_JC_LIST, oddsList);
		results.put(SoccerConstants.SOCCER_DATA_TEAM_OKOOO_LIST, teams);
		
		for (Element element : elements)
		{
			parseJcIssue(element, leagues, matchs, issueMatchs, oddsList, teams);
		}
		return results;
	}

	/**
	 * 按照期号解析比赛数据
	 * 
	 * @param element 数据元素
	 */
	protected void parseJcIssue(Element element, 
			List<OkoooLeague> leagues,
			List<OkoooMatch> matchs,
			List<OkoooIssueMatch> issueMatchs,
			List<BetJcOdds> oddsList,
			List<OkoooTeam> teams)
	{
		Element issueElement = element.selectFirst(".cont_1 .riqi .a5 span");
		Elements matchElements = element.select(".touzhu .touzhu_1");
		
		String issue;
		int size;
		
		size = matchElements.size();
		if (issueElement == null || size <= 0)
		{
			logger.info("No issue element.");
			return;
		}
		
		issue = getIssue(issueElement);
		for (int i = 1; i < size; i++)
		{
			Element el = matchElements.get(i);
			parseJcMatch(el, issue, leagues, matchs, issueMatchs, oddsList, teams);
		}
	}
	
	/**
	 * 解析比赛数据
	 * 
	 * @param element 每场比赛的数据
	 * @return 解析完成的比赛数据
	 */
	private void parseJcMatch(Element element, String issue,
			List<OkoooLeague> leagues,
			List<OkoooMatch> matchs, 
			List<OkoooIssueMatch> issueMatchs, 
			List<BetJcOdds> oddsList,
			List<OkoooTeam> teams)
	{
		Elements elements = element.children(); 
		if (elements.size() <= 0)
		{
			return;
		}

		String mid;
		String ordinary;
		String leaguename;
		String lid;
		String matchtime;
		//String closetime;
		String homeAlias;
		String homename;
		String clientAlias;
		String clientname;

		// 比赛基本信息
		Element el = elements.get(0);
		mid = element.attr("data-mid");							//比赛编号
		ordinary = el.selectFirst("span").text();				//比赛序号
		Element e2 = el.selectFirst("a");
		leaguename = e2.text();									//联赛名称
		lid = getLeadueId(e2.attr("href"));						//联赛编号
		matchtime = el.selectFirst(".shijian").attr("title");	//比赛时间
		//System.out.println("Match time is: " + matchtime);
		//closetime = matchtime;								//结束时间
		Date mtime = getMatchTime(matchtime);

		// 球队信息
		el = elements.get(1);
		Element hteam = el.selectFirst(".zhu .zhud .zhum");
		homeAlias = hteam.attr("title");
		homename = hteam.text();
		
		Element cteam = el.selectFirst(".fu .ked .zhum");
		clientAlias = cteam.attr("title");
		clientname = cteam.text();

		OkoooIssueMatch issueMatch = new OkoooIssueMatch();
		issueMatch.setIssue(issue);
		issueMatch.setIssueno(issue);
		issueMatch.setMid(mid);
		issueMatch.setType(SoccerConstants.LOTTERY_JC);
		issueMatch.setOrdinary(ordinary);
		issueMatch.setClosetime(mtime);
		issueMatch.setMatchtime(mtime);
		
		OkoooMatch match = new OkoooMatch();
		match.setMid(mid);
		match.setLid(lid);
		match.setHomeid(homename);
		match.setClientid(clientname);
		match.setMatchtime(mtime);
		
		OkoooTeam homeTeam = new OkoooTeam(null, homename, null);
		homeTeam.setAlias(homeAlias);
		OkoooTeam clientTeam = new OkoooTeam(null, clientname, null);
		clientTeam.setAlias(clientAlias);
		
		BetJcOdds odds = new BetJcOdds();
		odds.setMid(mid);
		odds.setType(SoccerConstants.LOTTERY_JC);
		
		// 设置比赛的信息
		match.setMid(mid);
		match.setLid(lid);
		
		// 奖金信息
		Element oddsEl = element.selectFirst(".shenpf");
		parseOdds(oddsEl, odds);
		
		// 让球信息
		oddsEl = element.selectFirst(".rangqiuspf");
		parseRqOdds(oddsEl, odds);
		
		OkoooLeague league = new OkoooLeague();
		league.setLid(lid);
		league.setName(leaguename);

		issueMatchs.add(issueMatch);
		matchs.add(match);
		oddsList.add(odds);
		teams.add(homeTeam);
		teams.add(clientTeam);
		
		if(!leagues.contains(league))
		{
			leagues.add(league);
		}
	}

	/**
	 * 解析竞彩的赔率
	 * 
	 * @param element 竞彩赔率数据
	 * @param match   比赛数据
	 */
	private void parseOdds(Element element, BetJcOdds odds)
	{
		String winodds;
		String drawodds;
		String loseodds;
		
		// boolean isopen = true;
		Elements elements = element.select(".peilv");
		if (elements.size() <= 0)
		{
			odds.setIsopen(false);
			return;
		}

		odds.setIsopen(true);
		winodds = elements.get(0).text();
		drawodds = elements.get(1).text();
		loseodds = elements.get(2).text();

		odds.setWinodds(NumberUtil.parseFloat(winodds));
		odds.setDrawodds(NumberUtil.parseFloat(drawodds));
		odds.setLoseodds(NumberUtil.parseFloat(loseodds));
	}

	/**
	 * 解析让球数据
	 * 
	 * @param element 让球数据
	 * @param match   比赛数据
	 */
	private void parseRqOdds(Element element, BetJcOdds odds)
	{
		String rq;
		String rqwinodds;
		String rqdrawodds;
		String rqloseodds;

		Element rqEl = element.selectFirst(".rangqiuzhen");
		if (rqEl == null)
		{
			odds.setIsrqopen(false);
			return;
		}
		rq = rqEl.text();
		odds.setIsrqopen(true);
		
		Elements oddsEls = element.select(".peilv");
		if (oddsEls.size() < 3)
		{
			return;
		}
		rqwinodds = oddsEls.get(0).text();
		rqdrawodds = oddsEls.get(1).text();
		rqloseodds = oddsEls.get(2).text();

		odds.setRqnum(NumberUtil.parseInt(rq));
		odds.setRqwinodds(NumberUtil.parseFloat(rqwinodds));
		odds.setRqdrawodds(NumberUtil.parseFloat(rqdrawodds));
		odds.setRqloseodds(NumberUtil.parseFloat(rqloseodds));
	}
	
	/**
	 * 获得期号信息
	 * 
	 * @param titleElement 信息元素
	 * @return 期号
	 */
	private String getIssue(Element titleElement)
	{
		if (titleElement != null)
		{
			String issue = titleElement.text();
			Date date = DateUtil.tryToParseDate(issue);
			if (date != null)
			{
				return DateUtil.formatDay(date);
			}
		}
		return "";
	}
}
