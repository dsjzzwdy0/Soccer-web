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
package com.loris.soccer.zgzcw.parser;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.MatchJc;
import com.loris.soccer.zgzcw.parser.base.AbstractLotteryWebPageParser;
import com.loris.soccer.zgzcw.util.ZgzcwConstants;

/**
 * @ClassName: LotteryJcWebPageParser
 * @Description: 解析竞彩比赛的数据
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LotteryJcWebPageParser extends AbstractLotteryWebPageParser
{
	/**
	 * Create a new instance of LotteryJcWebPageParser.
	 */
	public LotteryJcWebPageParser()
	{
		super(ZgzcwConstants.PAGE_LOTTERY_JC);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage,
	 *      org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		String issue = page.getParams().get(SoccerConstants.NAME_FIELD_ISSUE);
		if (StringUtils.isEmpty(issue))
		{
			issue = parseIssueElement(document);
		}

		MatchItemList matchJcs = new MatchItemList();
		parseJcMatchList(document, issue, matchJcs);

		results.put(SoccerConstants.SOCCER_DATA_MATCH_JC_LIST, matchJcs);

		return results;
	}

	/**
	 * 解析竞彩比赛数据列表
	 * 
	 * @param document
	 *            文档
	 * @param issue
	 *            期号
	 * @param matchJcs
	 *            数据表
	 */
	protected void parseJcMatchList(Document document, String issue, MatchItemList matchJcs)
	{
		Elements elements = document.select(".tz-wap .tz-body table tbody tr");
		for (Element element : elements)
		{
			MatchJc match = new MatchJc();
			String t = element.attr("t");
			Date closeTime = DateUtil.tryToParseDate(t);

			match.setClosetime(closeTime);
			match.setIssue(issue);
			parseJcMatch(element, match);
			
			matchJcs.add(match);
		}
	}

	/**
	 * Parse the match info.
	 * 
	 * @param match
	 * @param element
	 */
	protected void parseJcMatch(Element el, MatchJc match)
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
						}
					}
				}
			}
			else if (element.hasClass("wh-4")) // 主队信息与排名
			{

			}
			else if (element.hasClass("wh-5")) // 比分
			{

			}
			else if (element.hasClass("wh-6")) // 客队信息与排名
			{

			}
			else if (element.hasClass("wh-8")) // 赔率与让球赔率
			{
				Elements els = element.select("div");
				processOddsValue(els.get(0), 0, match);
				processOddsValue(els.get(1), 1, match);
			}
			else if (element.hasClass("wh-10")) // 比赛编号与欧亚盘等
			{
				String mid = element.attr("newPlayid");
				match.setMid(mid); // 比赛的ID编号
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
	protected void processOddsValue(Element element, int type, MatchJc match)
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
				match.setOpened(false);
			}
			else
			{
				match.setRqopened(false);
			}
			return;
		}
		else
		{
			if (type == 0)
			{
				match.setOpened(true);
			}
			else
			{
				match.setRqopened(true);
			}
		}
		draw = es.get(1).text();
		lose = es.get(2).text();

		if (type == 0)
		{
			boolean danguan = (e2 != null) && e2.hasClass("dg");			
			match.setDanguan(danguan);
			match.setWinodds(Float.valueOf(win));
			match.setDrawodds(Float.valueOf(draw));
			match.setLoseodds(Float.valueOf(lose));
		}
		else
		{
			match.setRqnum(Integer.valueOf(value));
			match.setRqwinodds(Float.valueOf(win));
			match.setRqdrawodds(Float.valueOf(draw));
			match.setRqloseodds(Float.valueOf(lose));
		}
	}
}
