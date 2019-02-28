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

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Match;
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
		MatchItemList matchBds = new MatchItemList();
		MatchList baseMatchs = new MatchList();		
		parseBdMatchList(document, issue, baseMatchs, matchBds);
		
		if(matchBds.size() > 0)	results.put(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST, matchBds);
		if(baseMatchs.size() >0) results.put(SoccerConstants.SOCCER_DATA_MATCH_LIST, baseMatchs);		
		return results;
	}
	
	/**
	 * 解析比赛数据
	 * 
	 * @param document
	 * @param matchBds
	 */
	protected void parseBdMatchList(Document document, String issue, MatchList baseMatchs, MatchItemList matchBds)
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
			
			Elements elements = element.select("tbody tr");
			for (Element el : elements)
			{
				String ord = el.attr("i");
				String t = el.attr("t");
				Date closeTime = DateUtil.tryToParseDate(t);

				MatchBd match = new MatchBd();
				Match baseMatch = new Match();
				
				match.setBdno(issue);
				match.setOrdinary(ord);
				match.setIssue(LotteryUtil.getLotteryIssue(closeTime));
				match.setRqopened(true);
				match.setClosetime(closeTime);

				parseMatchInfo(element, baseMatch, match);
				matchBds.add(match);
				baseMatchs.add(baseMatch);
			}
		}
	}
	
	/**
	 * 解析比赛信息数据
	 * 
	 * @param match 北单比赛数据
	 * @param baseMatch 基础比赛数据
	 * @param el 数据元素
	 */
	protected void parseMatchInfo(Element el, Match baseMatch, IssueMatch match)
	{
		Elements els = el.select("td");
		for (Element element : els)
		{
			if (element.hasClass("wh-1"))		//序号
			{
			}
			else if (element.hasClass("wh-2"))	//联赛名称及编号，当比赛已经截止之后，只有联赛名称
			{
				Element el0 = element.child(0);
				if("a".equalsIgnoreCase(el0.nodeName()))
				{
					String lid = NumberUtil.parseLastIntegerString(el0.attr("href"));
					baseMatch.setLid(lid);
				}
				else
				{
					League league = getLeague(el0.text().trim());
					if(league != null)
					{
						baseMatch.setLid(league.getLid());
					}
				}
			}
			else if (element.hasClass("wh-3"))	//截止时间和比赛时间，当比赛已经截止之后，没有比赛时间
			{
				Elements spans = element.children();
				if(spans != null && spans.size() >= 3)
				{
					Date matchTime = DateUtil.tryToParseDate(spans.get(2).text());
					if(matchTime != null)
					{
						match.setMatchtime(matchTime);
						baseMatch.setMatchtime(matchTime);
					}
				}
			}
			else if (element.hasClass("wh-4"))	//主队名称、球队编号
			{
			}
			else if (element.hasClass("wh-5"))	//比分：或“VS.”
			{
			}
			else if (element.hasClass("wh-6"))
			{
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
				baseMatch.setMid(mid);
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
