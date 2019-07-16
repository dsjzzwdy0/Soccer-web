/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooOddsYpPageParser.java   
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
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.parser.base.AbstractOkoooPageParser;
import com.loris.soccer.dict.HandicapDict;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.OkoooOddsYp;

/**   
 * @ClassName:  OkoooOddsYpPageParser.java   
 * @Description: 澳客亚盘数据下载解析器   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooOddsYpPageParser extends AbstractOkoooPageParser
{
	private static Logger logger = Logger.getLogger(OkoooOddsYpPageParser.class);
	
	/**
	 * @param acceptType
	 */
	public OkoooOddsYpPageParser(String acceptType)
	{
		super(acceptType);
	}
	
	/**
	 * 
	 */
	public OkoooOddsYpPageParser()
	{
		this(OkoooConstants.PAGE_ODDS_YP);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#accept(com.loris.client.model.WebPage)
	 */
	@Override
	protected boolean accept(WebPage page) throws WebParserException
	{
		if(!super.accept(page))
		{
			return false;
		}
		
		if(StringUtils.isBlank(page.getParam(SoccerConstants.NAME_FIELD_MID)))
		{
			logger.info("There are no mid value set.");
			return false;
		}
		
		if(StringUtils.isBlank(page.getParam(SoccerConstants.NAME_FIELD_MATCHTIME)))
		{
			logger.info("The match time is not set.");
			return false;
		}

		return true;
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		String mid = page.getParam(SoccerConstants.NAME_FIELD_MID);
		String matchtime = page.getParam(SoccerConstants.NAME_FIELD_MATCHTIME);
		
		Element element = document.selectFirst(".wrap .container_wrapper .noBberBottom  tbody");
		if(element == null)
		{
			logger.info("The root element is not exist, there are no data, parse error.");
			return null;
		}
		
		//logger.info(page.getContent());
		
		DataList<OkoooOddsYp> okoooOddsYps = new DataList<>();
		DataList<CasinoComp> comps = new DataList<>();
		
		comps.setOverwrite(false);
		
		results.put(SoccerConstants.SOCCER_DATA_ODDS_OKOOO_YP_LIST, okoooOddsYps);
		results.put(SoccerConstants.SOCCER_DATA_CASINO_COMP_LIST, comps);
		
		Elements elements = element.select("tr");
		Date date = DateUtil.tryToParseDate(matchtime);
		
		//解析数据
		for (Element element2 : elements)
		{
			parseOddsRecord(element2, mid, date, okoooOddsYps, comps);
		}
		
		//parseOddsList(element, mid,DateUtil.tryToParseDate(matchtime), okoooOddsYps, comps);
		//解析一共有多少家公司
		Element totalCompNumEl = document
				.selectFirst("#data_footer_float .noBberBottom #matchNum");
		if (totalCompNumEl != null)
		{
			String val = totalCompNumEl.text();
			int corpNum = NumberUtil.parseInt(val);

			results.put(OkoooConstants.NAME_FIELD_CORP_TOTAL_NUM, corpNum);
		}
		
		return results;
	}

	
	/**
	 * 解析亚盘数据记录
	 * @param element 元素
	 */
	protected void parseOddsRecord(Element element, String mid, Date matchTime, List<OkoooOddsYp> yps, List<CasinoComp> comps)
	{
		Elements elements = element.children();
		if(elements.size() < 13)
		{
			//记录数据不正确
			return;
		}
		
		String gid;
		String gname;
		String firsttime;
		String firstwinodds;
		String firsthandicap;
		String firstloseodds;
		
		String time;
		String winodds;
		String handicap;
		String loseodds;
		
		String winkelly;
		String losekelly;
		String lossratio;
		
		Date d;
		
		long t = 0;
		gid = elements.get(0).selectFirst("input").attr("value");
		gname = elements.get(1).text();
		firsttime = elements.get(2).attr("title");
		firstwinodds = elements.get(2).text();
		firsthandicap = elements.get(3).text();
		firstloseodds = elements.get(4).text();
		
		winodds = elements.get(5).text();
		handicap = elements.get(6).text();
		loseodds = elements.get(7).text();
		time = elements.get(8).attr("attr");
		
		winkelly = elements.get(10).text();
		losekelly = elements.get(11).text();
		lossratio = elements.get(12).text();
		
		OkoooOddsYp firstYp = new OkoooOddsYp();
		OkoooOddsYp lastYp = new OkoooOddsYp();
		
		firstYp.setMid(mid);
		firstYp.setCorpid(gid);
		
		lastYp.setMid(mid);
		lastYp.setCorpid(gid);
		
		//firstYp.setGname(gname);
		if(StringUtils.isNotEmpty(firsttime))
		{
			t = getTime(firsttime);
			d = DateUtil.add(matchTime, -t);
			firstYp.setOpentime(d);
			//firsttime = DateUtil.DATE_TIME_FORMAT.format(d);
		}
		//firstYp.setOpentime(firsttime);
		firstYp.setWinodds(NumberUtil.parseFloatFromString(firstwinodds));
		firstYp.setHandicap(HandicapDict.getHandicapValue(firsthandicap));
		firstYp.setLoseodds(NumberUtil.parseFloatFromString(firstloseodds));
		
		
		if(StringUtils.isNotEmpty(time))
		{
			t = getTime(time);
			d = DateUtil.add(matchTime, -t);
			lastYp.setOpentime(d);
			//time = DateUtil.DATE_TIME_FORMAT.format(d);
		}
		lastYp.setWinodds(NumberUtil.parseFloatFromString(winodds));
		lastYp.setHandicap(HandicapDict.getHandicapValue(handicap));
		lastYp.setLoseodds(NumberUtil.parseFloatFromString(loseodds));
		
		firstYp.setWinkelly(NumberUtil.parseFloatFromString(winkelly));
		firstYp.setLosekelly(NumberUtil.parseFloatFromString(losekelly));
		firstYp.setLossratio(NumberUtil.parseFloatFromString(lossratio));
		
		lastYp.setWinkelly(NumberUtil.parseFloatFromString(winkelly));
		lastYp.setLosekelly(NumberUtil.parseFloatFromString(losekelly));
		lastYp.setLossratio(NumberUtil.parseFloatFromString(lossratio));
		
		yps.add(firstYp);
		yps.add(lastYp);
		
		CasinoComp comp = new CasinoComp();
		comp.setCorpid(gid);
		comp.setName(gname);
		comp.setType(SoccerConstants.ODDS_TYPE_YP);
		comp.setSource(OkoooConstants.SOURCE_OKOOO);
		comps.add(comp);
	}
	
	/**
	 * 处理开出的赔率的时间
	 * @param time 时间字符串
	 * @return 时间毫秒数
	 */
	/**
	 * 解析时间，其格式为: 开赔时间：赛前61时27分
	 *  
	 * @param time 时间值
	 * @return 整型时间
	 */
	protected long getTime(String time)
	{
		Integer[] ts = NumberUtil.parseAllIntegerFromString(time);
		if(ts == null || ts.length < 2)
		{
			return 0;
		}
		else
		{
			long t = ts[0] * 3600 + ts[1] * 60;
			return t * 1000;
		}
	}
}
