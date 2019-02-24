/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
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
import com.loris.common.util.NumberUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dict.HandicapDict;
import com.loris.soccer.model.OddsYp;
import com.loris.soccer.plugin.zgzcw.ZgzcwConstants;
import com.loris.soccer.plugin.zgzcw.parser.base.AbstractZgzcwMatchWebPageParser;

/**
 * @ClassName: League
 * @Description: 解析亚盘数据记录值
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class OddsYpWebPageParser extends AbstractZgzcwMatchWebPageParser
{
	/**
	 * Create a new instance of OddsYpWebPageParser
	 */
	public OddsYpWebPageParser()
	{
		super(ZgzcwConstants.PAGE_ODDS_YP);
	}

	/**
	 * Create a new instance of OddsYpWebPageParser
	 */
	public OddsYpWebPageParser(String acceptType)
	{
		super(acceptType);
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.soccer.plugin.zgzcw.parser.base.AbstractZgzcwWebPageParser#accept(com.loris.client.model.WebPage)
	 */
	@Override
	protected boolean accept(WebPage page) throws WebParserException
	{
		if (!super.accept(page))
		{
			return false;
		}

		String mathTime = page.getParams().get(SoccerConstants.NAME_FIELD_MATCHTIME);
		if (StringUtils.isEmpty(mathTime))
		{
			throw new WebParserException("Error occured, the Page hasn't set the 'matchtime' param.");
		}

		return true;
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
		Elements elements = document.select(".main #data-body table tbody tr");
		if (elements == null || elements.size() <= 0)
		{
			throw new WebParserException("The document is not a validate Soccer Match yp page.");
		}

		String mid = page.getParams().get(SoccerConstants.NAME_FIELD_MID);
		String mathTime = page.getParams().get(SoccerConstants.NAME_FIELD_MATCHTIME);
		Date time = DateUtil.tryToParseDate(mathTime);

		List<OddsYp> yps = new ArrayList<>();
		for (Element element2 : elements)
		{
			parseYp(element2, mid, time, yps);
		}
		results.put(SoccerConstants.SOCCER_DATA_OP_LIST, yps);
		return results;
	}

	/**
	 * 解析亚盘数据
	 * @param element 亚盘数据元素
	 * @param mid 比赛编号
	 * @param matchTime 比赛时间
	 * @param yps 亚盘数据列表
	 */
	protected void parseYp(Element element, String mid, Date matchTime, List<OddsYp> yps)
	{
		Elements elements = element.select("td");
		int size = elements.size();
		if (size < 14)
		{
			return;
		}

		OddsYp firstOdds = new OddsYp(mid);
		OddsYp odds = new OddsYp(mid);

		firstOdds.setSource(ZgzcwConstants.SOURCE_ZGZCW);
		odds.setSource(ZgzcwConstants.SOURCE_ZGZCW);

		String first = element.attr("firsttime");
		Date firstTime = DateUtil.tryToParseDate(first);
		
		String name = elements.get(1).text();
		float firstwinyp = parseDataAttr(elements.get(2));
		String firsthandicap = elements.get(3).attr(dataAttr);
		float firstloseyp = parseDataAttr(elements.get(4));
		float lastwinyp = parseDataAttr(elements.get(5));
		String compid = elements.get(5).attr(compIdAttr);
		String lasthandicap = elements.get(6).attr(dataAttr);

		float lastloseyp = parseDataAttr(elements.get(7));
		String updatetime = elements.get(8).selectFirst("em").attr("title");
		float homeprob = parseDataAttr(elements.get(9));
		float guestprob = parseDataAttr(elements.get(10));
		float homekelly = parseDataAttr(elements.get(11));
		float guestkelly = parseDataAttr(elements.get(12));
		float lossratio = parseDataAttr(elements.get(13));

		firstOdds.setCorpid(compid);
		firstOdds.setCorpname(name);
		firstOdds.setOpentime(firstTime.getTime());		
		firstOdds.setWinodds(firstwinyp);
		firstOdds.setHandicap(HandicapDict.getHandicapValue(firsthandicap));
		firstOdds.setLoseodds(firstloseyp);
		firstOdds.setWinprob(homeprob);
		firstOdds.setLoseprob(guestprob);
		firstOdds.setWinkelly(homekelly);
		firstOdds.setLosekelly(guestkelly);
		firstOdds.setLossratio(lossratio);

		odds.setCorpid(compid);
		odds.setCorpname(name);
		odds.setOpentime(getOpenTime(matchTime, updatetime));
		odds.setWinodds(lastwinyp);
		odds.setHandicap(HandicapDict.getHandicapValue(lasthandicap));
		odds.setLoseodds(lastloseyp);
		odds.setWinprob(homeprob);
		odds.setLoseprob(guestprob);
		odds.setWinkelly(homekelly);
		odds.setLosekelly(guestkelly);
		odds.setLossratio(lossratio);

		yps.add(firstOdds);
		yps.add(odds);
	}

	/**
	 * 解析赔率等值的数据
	 * 
	 * @param element
	 * @return
	 */
	protected float parseDataAttr(Element element)
	{
		return NumberUtil.parseFloat(element.attr(dataAttr));
	}

	/**
	 * 解析时间，其格式为: 开赔时间：赛前61时27分
	 * 
	 * @param time
	 *            时间值
	 * @return 整型时间
	 */
	protected long getOpenTime(Date matchTime, String time)
	{
		int[] ts = NumberUtil.parseAllIntegerFromString(time);
		long t = 0;
		if (ts == null)
		{
			t = 0;
		}
		else if (ts.length == 1)
		{
			t = ts[0] * 60;
		}
		else
		{
			t = ts[0] * 3600 + ts[1] * 60;
		}
		t *= 1000;
		Date d = DateUtil.add(matchTime, -t);
		return d.getTime();
	}
}
