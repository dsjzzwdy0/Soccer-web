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
import com.loris.soccer.collection.CasinoCompList;
import com.loris.soccer.collection.OddsNumList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.OddsNum;

/**   
 * @ClassName:  League   
 * @Description: 解析进球数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsNumWebPageParser extends OddsYpWebPageParser
{
	/**
	 * Create a new instance of OddsYpWebPageParser
	 */
	public OddsNumWebPageParser()
	{
		super(ZgzcwConstants.PAGE_ODDS_NUM);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, 
	 * 		org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		Element element = document.selectFirst(".main #data-body");
		if(element == null)
		{
			throw new WebParserException("The document is not a validate Soccer Match num page.");
		}
		
		Elements elements = element.select("table tbody tr");
		if(elements == null || elements.size() <= 0)
		{
			return null;
		}

		String mid = page.getParams().get(SoccerConstants.NAME_FIELD_MID);
		String mathTime = page.getParams().get(SoccerConstants.NAME_FIELD_MATCHTIME);
		Date time = DateUtil.tryToParseDate(mathTime);

		OddsNumList nums = new OddsNumList();
		CasinoCompList comps = new CasinoCompList();
		for (Element element2 : elements)
		{
			parseNum(document, element2, mid, time, nums, comps);
		}
		results.put(SoccerConstants.SOCCER_DATA_ODDS_NUM_LIST, nums);
		results.put(SoccerConstants.SOCCER_DATA_CASINO_COMP_LIST, comps);
		return results;
	}
	
	/**
	 * 解析大小球对比数据
	 * @param element Dom元素
	 * @param mid 比赛编号
	 * @param matchTime 比赛时间
	 * @param nums 大小球列表
	 */
	protected void parseNum(Document document, Element element, String mid, Date matchTime, List<OddsNum> nums, CasinoCompList comps)
	{
		Elements elements = element.select("td");
		int size = elements.size();
		if (size < 14)
		{
			return;
		}

		String first = element.attr("firsttime");
		Date firstTime = DateUtil.tryToParseDate(first);
		
		String name = elements.get(1).text();
		float firstwinyp = parseDataAttr(elements.get(2));
		String firstGoal = elements.get(3).attr(dataAttr);
		float firstloseyp = parseDataAttr(elements.get(4));
		float lastwinyp = parseDataAttr(elements.get(5));
		String compid = elements.get(5).attr(compIdAttr);
		String lastGoal = elements.get(6).attr(dataAttr);

		float lastloseyp = parseDataAttr(elements.get(7));
		String updatetime = elements.get(8).selectFirst("em").attr("title");
		float homeprob = parseDataAttr(elements.get(9));
		float guestprob = parseDataAttr(elements.get(10));
		float homekelly = parseDataAttr(elements.get(11));
		float guestkelly = parseDataAttr(elements.get(12));
		float lossratio = parseDataAttr(elements.get(13));
		
		Element detailElement = getCorpElements(document, compid);
		if(detailElement == null)
		{
			OddsNum firstOdds = new OddsNum(mid);
			OddsNum odds = new OddsNum(mid);
			CasinoComp comp = new CasinoComp();
	
			firstOdds.setSource(ZgzcwConstants.SOURCE_ZGZCW);
			odds.setSource(ZgzcwConstants.SOURCE_ZGZCW);
			comp.setType(SoccerConstants.ODDS_TYPE_NUM);
			comp.setSource(ZgzcwConstants.SOURCE_ZGZCW);
			
			firstOdds.setCorpid(compid);
			//firstOdds.setCorpname(name);
			firstOdds.setOpentime(firstTime);
			firstOdds.setWinodds(firstwinyp);
			firstOdds.setGoalnum(getGoalNum(firstGoal));
			firstOdds.setLoseodds(firstloseyp);
			firstOdds.setWinprob(homeprob);
			firstOdds.setLoseprob(guestprob);
			firstOdds.setWinkelly(homekelly);
			firstOdds.setLosekelly(guestkelly);
			firstOdds.setLossratio(lossratio);
	
			odds.setCorpid(compid);
			//odds.setCorpname(name);
			odds.setOpentime(getOpenTime(matchTime, updatetime));
			odds.setWinodds(lastwinyp);
			odds.setGoalnum(getGoalNum(lastGoal));
			odds.setLoseodds(lastloseyp);
			odds.setWinprob(homeprob);
			odds.setLoseprob(guestprob);
			odds.setWinkelly(homekelly);
			odds.setLosekelly(guestkelly);
			odds.setLossratio(lossratio);
			
			comp.setCorpid(compid);
			comp.setName(name);
			comp.setIsmain(false);
	
			nums.add(firstOdds);
			nums.add(odds);
			comps.add(comp);
		}
		else
		{
			parseCorpNums(nums, comps, detailElement, mid, compid, name, homeprob, guestprob, homekelly, guestkelly, lossratio);
		}
	}
	
	/**
	 * 解析亚盘的详细数据
	 * @param element
	 * @param corpid
	 * @param corpname
	 * @param winprob
	 * @param loseprob
	 * @param winkelly
	 * @param losekelly
	 * @param lossratio
	 */
	protected void parseCorpNums(List<OddsNum> nums, List<CasinoComp> comps, Element element, String mid, String compid, String name, 
			float homeprob, float guestprob, float homekelly, float guestkelly, float lossratio)
	{
		Elements elements = element.select("li");
		for (Element ypElement : elements)
		{
			Elements valueEls = ypElement.select("i");			
			if(valueEls == null || valueEls.size() < 3)
			{
				continue;
			}
			
			long opemtime = NumberUtil.parseLong(ypElement.attr("timestamp"));
			float winodds = NumberUtil.parseFloat(getElementValue(valueEls.get(0)));
			String goal = getElementValue(valueEls.get(1));
			float loseodds = NumberUtil.parseFloat(getElementValue(valueEls.get(2)));
			
			OddsNum odds = new OddsNum(mid);
			CasinoComp comp = new CasinoComp();
			comp.setType(SoccerConstants.ODDS_TYPE_NUM);
			comp.setSource(ZgzcwConstants.SOURCE_ZGZCW);
			
			odds.setSource(ZgzcwConstants.SOURCE_ZGZCW);
			odds.setOpentime(new Date(opemtime));
			odds.setCorpid(compid);
			//odds.setCorpname(name);
			odds.setWinodds(winodds);
			odds.setGoalnum(getGoalNum(goal));
			odds.setLoseodds(loseodds);
			odds.setWinprob(homeprob);
			odds.setLoseprob(guestprob);
			odds.setWinkelly(homekelly);
			odds.setLosekelly(guestkelly);
			odds.setLossratio(lossratio);
			
			comp.setCorpid(compid);
			comp.setName(name);
			comp.setIsmain(false);
			
			nums.add(odds);
			comps.add(comp);
		}
	}
	
	/**
	 * 解析进球数
	 * @param value 进球数
	 * @return 球数
	 */
	private String getGoalNum(String value)
	{
		value = value.replace("球", "");
		return value;
	}
}
