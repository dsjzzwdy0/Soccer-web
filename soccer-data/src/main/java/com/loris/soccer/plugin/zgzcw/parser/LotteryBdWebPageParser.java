/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LotteryBdWebPageParser.java   
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
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.base.IssueMatch;
import com.loris.soccer.plugin.zgzcw.parser.base.AbstractLotteryWebPageParser;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwConstants;
import com.loris.soccer.util.LotteryUtil;

/**   
 * @ClassName:  LotteryBdWebPageParser  
 * @Description: 解析北单数据页面
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class LotteryBdWebPageParser extends AbstractLotteryWebPageParser
{	
	/**
	 * Create a new instance of LotteryWebPageParser.
	 */
	public LotteryBdWebPageParser()
	{
		super(ZgzcwConstants.PAGE_LOTTERY_BD);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, 
	 * 		org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		String issue = page.getParams().get(SoccerConstants.NAME_FIELD_ISSUE);
		if(StringUtils.isEmpty(issue))
		{
			issue = parseIssueElement(document);
		}
		List<MatchBd> matchBds = new ArrayList<>();
		parseBdMatchList(document, issue, matchBds);
		results.put(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST, matchBds);
		
		return results;
	}
	
	/**
	 * 解析比赛数据
	 * 
	 * @param document
	 * @param matchBds
	 */
	protected void parseBdMatchList(Document document, String issue, List<MatchBd> matchBds)
	{
		Element element = document.selectFirst("#tw #dcc");

		Elements childElements = element.children();
		int childnum = childElements.size() / 2;

		for (int i = 0; i < childnum; i++)
		{
			Element el0 = childElements.get(i * 2);
			Element el1 = childElements.get(i * 2 + 1);

			if (el0 == null || el1 == null)
			{
				continue;
			}
			Element dateEl = el0.selectFirst(".fl strong");
			String dataStr = "";
			if (dateEl != null)
			{
				dataStr = dateEl.text();
				dataStr = DateUtil.findDateFromString(dataStr, dataFormat);
			}
			
			parseBdMatchList(el1, issue, matchBds);
		}
	}
	
	/**
	 * 解析比赛数据列表
	 * @param element
	 * @param date
	 */
	protected void parseBdMatchList(Element element, String issue, List<MatchBd> matchBds)
	{
		Elements elements = element.select("tbody tr");
		for (Element el : elements)
		{
			String ord = el.attr("i");
			String t = el.attr("t");
			Date closeTime = DateUtil.tryToParseDate(t);
			//String lname = el.attr("m");			
			//String expired = el.attr("expire");

			MatchBd match = new MatchBd();
			match.setBdno(issue);
			match.setOrdinary(ord);
			match.setIssue(LotteryUtil.getLotteryIssue(closeTime));
			match.setRqopened(true);
			match.setClosetime(closeTime);
			//match.setClosed("0".equals(expired));
			//match.setDate(date);
			//match.setLeaguename(lname);
			//match.setMatchtime(t);			
			parseMatchInfo(element, match);

			matchBds.add(match);
		}
	}
	
	/**
	 * 解析比赛信息数据
	 * 
	 * @param match
	 * @param els
	 */
	protected void parseMatchInfo(Element el, IssueMatch match)
	{
		Elements els = el.select("td");
		for (Element element : els)
		{
			if (element.hasClass("wh-1"))
			{

			}
			else if (element.hasClass("wh-2"))
			{
				// Element e = element.select("a").first();
				// if (e != null)
				// {
				// String url = e.attr("href");
				// String lid = getLastIdValue(url);
				// match.setLid(lid);
				// }
			}
			else if (element.hasClass("wh-3"))
			{
				//Element el2 = element.select("span").get(1);
				//String endtime = el2.text();
				//match.setClosetime(LotteryUtil.parseCloseTime(matchTime, endtime));
			}
			else if (element.hasClass("wh-4"))
			{
				// String tn = element.attr("tn");
				// match.setHomename(tn);
				// Element e = element.selectFirst("a");
				// if (e != null)
				// {
				// String url = e.attr("href");
				// String tid = getLastIdValue(url);
				// match.setHomeid(tid);
				// }
				//
				// e = element.selectFirst(".pm");
				// if (e != null)
				// {
				// String pm = e.text();
				// pm = NumberUtil.parseFirstIntegerString(pm);
				// match.setHomerank(pm);
				// }
			}
			else if (element.hasClass("wh-5"))
			{

			}
			else if (element.hasClass("wh-6"))
			{
				// String tn = element.attr("tn");
				// match.setClientname(tn);
				// Element e = element.selectFirst("a");
				// if (e != null)
				// {
				// String url = e.attr("href");
				// String tid = getLastIdValue(url);
				// match.setClientid(tid);
				// }
				//
				// e = element.selectFirst(".pm");
				// if (e != null)
				// {
				// String pm = e.text();
				// pm = NumberUtil.parseFirstIntegerString(pm);
				// match.setClientrank(pm);
				// }
			}
			else if (element.hasClass("wh-8"))
			{
				Element rq = element.selectFirst(".rq");
				Elements oddslist = element.select(".bets-area a");
				String rqvalue = rq.text();
				String win = oddslist.get(0).text();
				String draw = oddslist.get(1).text();
				String lose = oddslist.get(2).text();
				
				match.setRqnum(Integer.valueOf(rqvalue));
				match.setWinodds(Float.valueOf(win));
				match.setDrawodds(Float.valueOf(draw));
				match.setLoseodds(Float.valueOf(lose));
			}
			else if (element.hasClass("wh-10"))
			{
				String mid = element.attr("newplayid");
				match.setMid(mid);
			}
			else if (element.hasClass("wh-11"))
			{

			}
			else if (element.hasClass("wh-12"))
			{

			}
		}
	}
}
