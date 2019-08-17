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
package com.loris.soccer.data.zgzcw.parser;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractLotteryMatchWebPageParser;
import com.loris.soccer.model.BetBdOdds;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Match;

/**   
 * @ClassName:  LotteryBdWebPageParser  
 * @Description: 解析北单数据页面
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class LotteryBdWebPageParser extends AbstractLotteryMatchWebPageParser
{		
	/**
	 * Create a new instance of LotteryWebPageParser.
	 */
	public LotteryBdWebPageParser()
	{
		super(ZgzcwConstants.PAGE_LOTTERY_BD, SoccerConstants.SOCCER_DATA_MATCH_BD_LIST);
	}
	
	/**
	 * 解析比赛数据
	 * 
	 * @param document
	 * @param matchBds
	 */
	@Override
	protected void parseMatchList(Document document, String issue, List<Match> baseMatchs, 
			List<IssueMatch> matchBds, TableRecords results)
	{
		DataList<BetBdOdds> oddsList = new DataList<>();
		
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
				String ord = el.attr("i");
				String t = el.attr("t");
				Date closeTime = DateUtil.tryToParseDate(t);
				
				//System.out.println(closeTime);

				IssueMatch match = new IssueMatch();
				Match baseMatch = new Match();
				BetBdOdds odds = new BetBdOdds();
				
				odds.setType(SoccerConstants.LOTTERY_BD);
				odds.setOpentime(new Date());
				match.setType(SoccerConstants.LOTTERY_BD);

				match.setIssueno(issue);
				match.setOrdinary(ord);
				match.setIssue(dateStr);
				match.setClosetime(closeTime);

				parseMatchAndBetOdds(el, baseMatch, match, odds);
				
				if(StringUtils.isNotBlank(match.getMid()) && !"0".equals(match.getMid()))
				{
					matchBds.add(match);
					baseMatchs.add(baseMatch);
					
					if(DateUtil.compareDate(match.getMatchtime(), odds.getOpentime()) > 0)
					{
						oddsList.add(odds);
					}
				}
			}
		}
		if(oddsList.size() > 0)
		{
			results.put(SoccerConstants.SOCCER_DATA_BETODDS_BD_LIST, oddsList);
		}
	}
	
	/**
	 * 解析比赛信息数据
	 * 
	 * @param match 北单比赛数据
	 * @param baseMatch 基础比赛数据
	 * @param el 数据元素
	 */
	protected void parseMatchAndBetOdds(Element el, Match baseMatch, IssueMatch match, BetBdOdds odds)
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
				
				if(StringUtils.isBlank(baseMatch.getLid()))
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
					String string = parseFirstDateString(spans.get(2).attr("title"));
					//System.out.println("MatchTime: " + string);
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
			else if (element.hasClass("wh-4"))	//主队名称、球队编号
			{
				baseMatch.setHomeid(parseTeamId(element));				
			}
			else if (element.hasClass("wh-5"))	//比分：或“VS.”
			{
			}
			else if (element.hasClass("wh-6"))
			{
				baseMatch.setClientid(parseTeamId(element));	
			}
			else if (element.hasClass("wh-8"))
			{
				Element rq = element.selectFirst(".rq");
				Elements oddslist = element.select(".bets-area a");
				String rqvalue = rq.text();
				String win = oddslist.get(0).text();
				String draw = oddslist.get(1).text();
				String lose = oddslist.get(2).text();
				
				odds.setRqnum(Integer.valueOf(rqvalue));
				odds.setWinodds(Float.valueOf(win));
				odds.setDrawodds(Float.valueOf(draw));
				odds.setLoseodds(Float.valueOf(lose));
			}
			else if (element.hasClass("wh-10"))
			{
				String mid = element.attr("newplayid");
				match.setMid(mid);
				baseMatch.setMid(mid);
				odds.setMid(mid);
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
