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

import java.util.ArrayList;
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
import com.loris.soccer.collection.BetJcOddsList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.parser.base.AbstractOkoooPageParser;
import com.loris.soccer.model.BetJcOdds;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;

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
		super(OkoooConstants.PAGE_SCORE_JC);
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
		
		List<OkoooIssueMatch> issueMatchs = new ArrayList<>();
		List<OkoooMatch> matchs = new ArrayList<>();
		List<OkoooLeague> leagues = new ArrayList<>();
		BetJcOddsList oddsList = new BetJcOddsList();
		
		results.put(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_JC_LIST, issueMatchs);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_LIST, matchs);
		results.put(SoccerConstants.SOCCER_DATA_LEAGUE_OKOOO_LIST, leagues);
		results.put(SoccerConstants.SOCCER_DATA_BETODDS_JC_LIST, oddsList);
		
		for (Element element : elements)
		{
			parseJcIssue(element, leagues, matchs, issueMatchs, oddsList);
		}
		return results;
	}

	/**
	 * 按照期号解析比赛数据
	 * 
	 * @param element 数据元素
	 */
	protected void parseJcIssue(Element element, List<OkoooLeague> leagues,
			List<OkoooMatch> matchs, List<OkoooIssueMatch> issueMatchs, List<BetJcOdds> oddsList)
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
			parseJcMatch(el, issue, leagues, matchs, issueMatchs, oddsList);
		}
	}
	
	/**
	 * 解析比赛数据
	 * 
	 * @param element 每场比赛的数据
	 * @return 解析完成的比赛数据
	 */
	private void parseJcMatch(Element element, String issue, List<OkoooLeague> leagues,
			List<OkoooMatch> matchs, List<OkoooIssueMatch> issueMatchs, List<BetJcOdds> oddsList)
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
		String closetime;
		String homename;
		String clientname;

		// 联赛信息
		mid = element.attr("id");
		mid = mid.replace("match_", "");
		Element el = elements.get(0);
		ordinary = el.selectFirst("span").text();
		Element e2 = el.selectFirst("a");
		leaguename = e2.text();
		lid = getLeadueId(e2.attr("href"));

		// 比赛时间信息
		matchtime = el.selectFirst(".shijian").attr("title");
		closetime = matchtime;

		// 球队信息
		el = elements.get(1);
		Elements teams = el.select(".zhud");
		homename = teams.get(0).text();
		clientname = teams.get(1).text();
		Date mtime = getMatchTime(matchtime);

		OkoooIssueMatch issueMatch = new OkoooIssueMatch();
		issueMatch.setIssue(issue);
		issueMatch.setIssueno(issue);
		issueMatch.setMid(mid);
		issueMatch.setType(SoccerConstants.LOTTERY_JC);
		issueMatch.setOrdinary(ordinary);
		issueMatch.setClosetime(getCloseTime(matchtime, closetime));
		issueMatch.setMatchtime(mtime);
		
		OkoooMatch match = new OkoooMatch();
		match.setMid(mid);
		match.setLid(lid);
		match.setHomeid(homename);
		match.setClientid(clientname);
		match.setMatchtime(mtime);
		
		BetJcOdds odds = new BetJcOdds();
		odds.setMid(mid);
		odds.setType(SoccerConstants.LOTTERY_JC);
		
		// 设置比赛的信息
		match.setMid(mid);
		match.setLid(lid);
		
		// 奖金信息
		el = elements.get(4);
		Element oddsEl = el.selectFirst(".frqBetObj");
		parseOdds(oddsEl, odds);
		// 让球信息
		oddsEl = el.selectFirst(".rqBetObj");
		parseRqOdds(oddsEl, odds);
		
		OkoooLeague league = new OkoooLeague();
		league.setLid(lid);
		league.setName(leaguename);

		issueMatchs.add(issueMatch);
		matchs.add(match);
		oddsList.add(odds);
		
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
		Elements elements = element.select("a");
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

		Elements elements = element.select("div");
		if (elements.size() < 2)
		{
			odds.setIsrqopen(false);
			return;
		}
		odds.setIsrqopen(true);
		rq = elements.get(0).selectFirst(".handicapObj").text();
		Elements oddsEls = elements.get(2).select("a");

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
		Element el = titleElement.selectFirst("td span span");
		if (el != null)
		{
			String issue = el.text();
			Date date = DateUtil.tryToParseDate(issue);
			if (date != null)
			{
				return DateUtil.formatDay(date);
			}
		}
		return "";
	}

	/**
	 * Get the Lid value.
	 * 
	 * @param url
	 * @return
	 */
	protected String getLeadueId(String url)
	{
		String[] values = url.split(RIGHT_SPLASH);
		int size = values.length;
		String tid = values[size - 1];
		return tid;
	}

	/**
	 * 获得比赛时间
	 * 
	 * @param matchtime
	 * @return
	 */
	protected Date getMatchTime(String matchtime)
	{
		String str = matchtime.replace("比赛时间：", "");
		return DateUtil.tryToParseDate(str);
	}

	/**
	 * 
	 * @param matchtrends
	 * @return
	 */
	protected String getMatchId(String matchtrends)
	{
		String[] values = matchtrends.split(RIGHT_SPLASH);
		int size = values.length;
		String mid = values[size - 2];
		return mid;
	}

	/**
	 * 获得截止时间
	 * 
	 * @param matchtime 比赛时间
	 * @param closetime 截止小时
	 * @return 关闭的时间
	 */
	private Date getCloseTime(String matchtime, String closetime)
	{
		closetime += DateUtil.getYear() + "-" + closetime;
		return DateUtil.tryToParseDate(closetime);
	}
}
