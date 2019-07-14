/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooBdPageParser.java   
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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.BetBdOddsList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.parser.base.AbstractOkoooPageParser;
import com.loris.soccer.model.BetBdOdds;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;

/**   
 * @ClassName:  OkoooBdPageParser.java   
 * @Description: 北京单场数据下载页面解析器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooBdPageParser extends AbstractOkoooPageParser
{
	private static Logger logger = Logger.getLogger(OkoooBdPageParser.class);
	
	/**
	 * @param acceptType
	 */
	public OkoooBdPageParser()
	{
		super(OkoooConstants.PAGE_SCORE_BD);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		Elements elements = document.select("#gametablesend .jcmaintable");
		if(elements == null || elements.size() <= 0)
		{
			return null;
		}
		
		List<OkoooIssueMatch> matchs = new ArrayList<>();
		List<OkoooMatch> baseMatchs = new ArrayList<>();
		List<OkoooLeague> leagues = new ArrayList<>();
		BetBdOddsList oddsList = new BetBdOddsList();
		oddsList.setOverwrite(true);
		
		results.put(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_BD_LIST, matchs);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_OKOOO_LIST, baseMatchs);
		results.put(SoccerConstants.SOCCER_DATA_LEAGUE_OKOOO_LIST, leagues);
		results.put(SoccerConstants.SOCCER_DATA_BETODDS_BD_LIST, oddsList);
		
		for (Element element : elements)
		{
			parseBdIssue(element, baseMatchs, matchs, oddsList, leagues);
		}
		return results;
	}
	
	/**
	 * 解析北单数据
	 * @param element
	 */
	protected void parseBdIssue(Element element, List<OkoooMatch> baseMatchs, 
			List<OkoooIssueMatch> matchs, List<BetBdOdds> oddsList, List<OkoooLeague> leagues)
	{
		String issue;
		int size;
		Elements elements = element.select("tr");
		size = elements.size();
		if(size <= 0)
		{
			logger.info("No issue element.");
			return;
		}	
		
		Element el = elements.get(0);
		issue = getIssue(el);
		
		for(int i = 1; i < size; i ++)
		{
			el = elements.get(i);
			parseBdMatch(el, issue, baseMatchs, matchs, oddsList, leagues);
		}
	}
	
	/**
	 * 解析比赛数据
	 * @param element 元素
	 * @param issue 期号
	 * @param baseMatchs 基础比赛数据
	 * @param issueMatchs 北单比赛数据
	 * @param oddsList 北单的开出盘号
	 * @param leagues 联赛数据
	 */
	private void parseBdMatch(Element element, String issue,
			List<OkoooMatch> baseMatchs, 
			List<OkoooIssueMatch> issueMatchs,
			List<BetBdOdds> oddsList, 
			List<OkoooLeague> leagues)
	{
		Elements elements = element.children();
		if(elements.size() <= 0)
		{
			return ;
		}
				
		String mid = "";
		String ordinary;
		String leaguename;
		String lid;
		
		String matchtime;
		String closetime;
		
		//比赛编号
		mid = element.attr("matchid");
		
		//联赛信息
		Element el = elements.get(0);
		ordinary = el.selectFirst("span i").text();
		Element e2 = el.selectFirst("a");
		leaguename = e2.text();
		lid = getLeadueId(e2.attr("href"));
		
		//比赛时间信息
		el = elements.get(1);
		matchtime = el.attr("title");
		matchtime = getMatchTime(matchtime);
		closetime = el.selectFirst(".BuyTime").text();

		Date mtime = DateUtil.tryToParseDate(matchtime);
		
		OkoooIssueMatch issueMatch = new OkoooIssueMatch();
		issueMatch.setType(SoccerConstants.LOTTERY_BD);
		OkoooMatch match = new OkoooMatch();
		BetBdOdds odds = new BetBdOdds();
		OkoooLeague league = new OkoooLeague();	
		
		//设置比赛的信息
		issueMatch.setMid(mid);
		issueMatch.setOrdinary(ordinary);
		issueMatch.setIssue(issue);
		issueMatch.setIssueno(issue);
		issueMatch.setMatchtime(mtime);
		issueMatch.setClosetime(getCloseTime(matchtime, closetime));
		
		match.setMid(mid);
		match.setLid(lid);
		match.setMatchtime(mtime);
		
		odds.setMid(mid);
		
		league.setLid(lid);
		league.setName(leaguename);
		
		//球队信息与奖金信息
		el = elements.get(2);
		parseTeamAndOdds(el, match, issueMatch, odds);
		
		issueMatchs.add(issueMatch);
		baseMatchs.add(match);
		oddsList.add(odds);
		
		if(!leagues.contains(league))
		{
			leagues.add(league);
		}
	}
	
	/**
	 * 解析主队客队信息
	 * @param element
	 * @param issueMatch
	 * @return 解析是否成功的标志
	 */
	protected boolean parseTeamAndOdds(Element element, OkoooMatch match, OkoooIssueMatch issueMatch, BetBdOdds odds)
	{
		String homename;
		String clientname;
		//String homerank = "";
		//String clientrank = "";
		String rq;
		String winodds;
		String drawodds;
		String loseodds;
		
		Elements elements = element.children();
		if(elements.size() < 3)
		{
			return false;
		}
		
		//主队信息
		Element hrefElemement = elements.get(0);
		/*Element e1 = hrefElemement.selectFirst(".paim_em");
		if(e1 != null && e1.children() != null && e1.children().size() == 2)
		{
			homerank = "" + NumberUtil.parseIntegerFromString(e1.children().get(1).text());
		}*/
		homename = hrefElemement.selectFirst(".homename").text();
		rq = parseRangqiu(hrefElemement.selectFirst(".handicapobj").text());
		winodds = hrefElemement.selectFirst(".pltxt").text();
		
		//平局信息
		hrefElemement = elements.get(1);
		drawodds = hrefElemement.selectFirst("em").text();
		
		//客队信息
		hrefElemement = elements.get(2);
		/*e1 = hrefElemement.selectFirst(".paim_em");
		if(e1 != null && e1.children() != null && e1.children().size() == 2)
		{
			clientrank = "" + NumberUtil.parseIntegerFromString(e1.children().get(1).text());
		}*/
		clientname = hrefElemement.selectFirst(".awayname").text();
		loseodds = hrefElemement.selectFirst(".pltxt").text();
		
		match.setHomeid(homename);
		match.setClientid(clientname);
		
		odds.setRqnum(NumberUtil.parseInt(rq));
		odds.setOpentime(new Date());
		odds.setType(SoccerConstants.LOTTERY_BD);
		odds.setWinodds(NumberUtil.parseFloat(winodds));
		odds.setDrawodds(NumberUtil.parseFloat(drawodds));
		odds.setLoseodds(NumberUtil.parseFloat(loseodds));
		
		return true;
	}
	
	/**
	 * 处理让球数据
	 * @param value 原始值
	 * @return 让球数据
	 */
	protected String parseRangqiu(String value)
	{
		if(StringUtils.isEmpty(value))
		{
			return "";
		}
		value = value.trim();
		value = value.replace("(", "");
		value = value.replace(")", "");
		return value;
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
	private String getMatchTime(String matchtime)
	{
		String str = matchtime.replace("比赛时间：", "");
		return str;
	}
	
	/**
	 * 获得比赛的ID值
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
	 * 获得期号信息
	 * 
	 * @param titleElement 信息元素
	 * @return 期号
	 */
	private String getIssue(Element titleElement)
	{
		Element el = titleElement.selectFirst("td span span");
		if(el != null)
		{
			String issue = el.text();
			Date date = DateUtil.tryToParseDate(issue);
			if(date != null)
			{
				return DateUtil.formatDay(date);
			}
		}
		return "";
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
