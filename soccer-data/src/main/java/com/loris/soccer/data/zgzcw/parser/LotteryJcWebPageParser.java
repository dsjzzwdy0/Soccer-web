/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LotteryJcWebPageParser.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.parser;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.BetJcOddsList;
import com.loris.soccer.collection.IssueMatchList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractLotteryMatchWebPageParser;
import com.loris.soccer.model.BetJcOdds;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Match;

/**
 * @ClassName: LotteryJcWebPageParser
 * @Description: 解析竞彩比赛的数据
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LotteryJcWebPageParser extends AbstractLotteryMatchWebPageParser
{
	/**
	 * Create a new instance of LotteryJcWebPageParser.
	 */
	public LotteryJcWebPageParser()
	{
		super(ZgzcwConstants.PAGE_LOTTERY_JC, SoccerConstants.SOCCER_DATA_MATCH_JC_LIST);
	}

	/**
	 * 解析竞彩比赛数据列表
	 * 
	 * @param document 文档
	 * @param issue 期号
	 * @param matchJcs 数据表
	 */
	@Override
	protected void parseMatchList(Document document, String issue, MatchList baseMatchs, 
			IssueMatchList matchJcs, TableRecords results)
	{
		BetJcOddsList oddsList = new BetJcOddsList();
		
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
			Element dateEl = el0.selectFirst(".ps strong");
			String dateStr = "";
			if (dateEl != null)
			{
				dateStr = dateEl.text();
				dateStr = DateUtil.findDateFromString(dateStr, dateFormat);
			}
			
			Elements elements = el1.select("tbody tr");
			for (Element el : elements)
			{
				IssueMatch match = new IssueMatch();
				Match baseMatch = new Match();
				BetJcOdds odds = new BetJcOdds();
				
				String t = el.attr("t");
				Date closeTime = DateUtil.tryToParseDate(t);
				
				odds.setType(SoccerConstants.LOTTERY_JC);
				odds.setOpentime(new Date());
				
				match.setType(SoccerConstants.LOTTERY_JC);
				match.setClosetime(closeTime);
				match.setIssue(dateStr);
				match.setIssueno(dateStr);
				parseMatchInfo(el, baseMatch, match, odds);
				
				if(StringUtils.isNotBlank(match.getMid()) && !"0".equals(match.getMid()))
				{
					matchJcs.add(match);
					baseMatchs.add(baseMatch);
					
					if(DateUtil.compareDate(match.getMatchtime(), odds.getOpentime()) > 0)
					{
						oddsList.add(odds);
					}
				}
			}
		}
		
		/*Elements elements = document.select(".tz-wap .tz-body table tbody tr");
		for (Element element : elements)
		{
			IssueMatch match = new IssueMatch();
			Match baseMatch = new Match();
			BetJcOdds odds = new BetJcOdds();
			
			String t = element.attr("t");
			Date closeTime = DateUtil.tryToParseDate(t);
			
			odds.setType(SoccerConstants.LOTTERY_JC);
			odds.setOpentime(new Date());
			
			match.setType(SoccerConstants.LOTTERY_JC);
			match.setClosetime(closeTime);
			match.setIssue(issue);
			match.setIssueno(issue);
			parseMatchInfo(element, baseMatch, match, odds);
			
			if(StringUtils.isNotBlank(match.getMid()) && !"0".equals(match.getMid()))
			{
				matchJcs.add(match);
				baseMatchs.add(baseMatch);
				
				if(DateUtil.compareDate(match.getMatchtime(), odds.getOpentime()) > 0)
				{
					oddsList.add(odds);
				}
			}
		}*/
		if(oddsList.size() > 0)
		{
			results.put(SoccerConstants.SOCCER_DATA_BETODDS_JC_LIST, oddsList);
		}
	}

	/**
	 * Parse the match info.
	 * 
	 * @param match
	 * @param element
	 */
	protected void parseMatchInfo(Element el, Match baseMatch, IssueMatch match, BetJcOdds odds)
	{
		Elements elements = el.select("td");
		for (Element element : elements)
		{
			if (element.hasClass("wh-1")) // 序号
			{
				String ord = element.selectFirst("i").text();
				match.setOrdinary(ord);
			}
			else if (element.hasClass("wh-2")) // 联赛
			{
				Element el0 = element.child(0);
				if("a".equalsIgnoreCase(el0.nodeName()))
				{
					String lid = NumberUtil.parseLastIntegerString(el0.attr("href"));
					baseMatch.setLid(lid);
				}
				
				if(StringUtils.isBlank(baseMatch.getLid()))
				{
					League league = getLeague(el0.text().trim());
					if(league != null)
					{
						baseMatch.setLid(league.getLid());
					}
				}
			}
			else if (element.hasClass("wh-3")) // 比赛时间和截止时间
			{
				Elements spans = element.children();
				if(spans != null && spans.size() >= 3)
				{
					String string = parseFirstDateString(spans.get(2).attr("title"));
					if(StringUtils.isNotBlank(string))
					{
						Date matchTime = DateUtil.tryToParseDate(string);
						if(matchTime != null)
						{
							match.setMatchtime(matchTime);
							baseMatch.setMatchtime(matchTime);
						}
					}
				}
			}
			else if (element.hasClass("wh-4")) // 主队信息与排名
			{
				baseMatch.setHomeid(parseTeamId(element));
			}
			else if (element.hasClass("wh-5")) // 比分
			{
			}
			else if (element.hasClass("wh-6")) // 客队信息与排名
			{
				baseMatch.setClientid(parseTeamId(element));	
			}
			else if (element.hasClass("wh-8")) // 赔率与让球赔率
			{
				Elements els = element.select("div");
				processOddsValue(els.get(0), 0, odds);
				processOddsValue(els.get(1), 1, odds);
			}
			else if (element.hasClass("wh-10")) // 比赛编号与欧亚盘等
			{
				String mid = element.attr("newPlayid");
				match.setMid(mid); // 比赛的ID编号
				baseMatch.setMid(mid);
				odds.setMid(mid);
			}
			else if (element.hasClass("wh-11")) // 主队信息
			{
			}
		}
	}

	/**
	 * 解析赔率数据与比赛信息
	 * @param match 比赛数据
	 * @param type 类型，0表示未让球、1表示让球的赔率
	 * @param element DOM元素
	 */
	protected void processOddsValue(Element element, int type, BetJcOdds odds)
	{
		Element e2 = element.selectFirst("em");
		String value = e2.text();
		Elements es = element.select("a");
		String win, draw, lose;
		win = es.get(0).text();
				
		if ("未开售".equals(win))
		{
			if (type == 0)
			{
				odds.setIsopen(false);
			}
			else
			{
				odds.setIsrqopen(false);
			}
			return;
		}
		else
		{
			if (type == 0)
			{
				odds.setIsopen(true);
			}
			else
			{
				odds.setIsrqopen(true);
			}
		}
		draw = es.get(1).text();
		lose = es.get(2).text();

		if (type == 0)
		{
			boolean danguan = (e2 != null) && e2.hasClass("dg");			
			odds.setIsdanguan(danguan);
			odds.setWinodds(Float.valueOf(win));
			odds.setDrawodds(Float.valueOf(draw));
			odds.setLoseodds(Float.valueOf(lose));
		}
		else
		{
			odds.setRqnum(Integer.valueOf(value));
			odds.setRqwinodds(Float.valueOf(win));
			odds.setRqdrawodds(Float.valueOf(draw));
			odds.setRqloseodds(Float.valueOf(lose));
		}
	}
}
