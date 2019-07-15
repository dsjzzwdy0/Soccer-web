/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooOddsOpPageParser.java   
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
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.OkoooOddsOp;

/**   
 * @ClassName:  OkoooOddsOpPageParser.java   
 * @Description: 澳客数据页面下载解析器   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooOddsOpPageParser extends AbstractOkoooPageParser
{
	private static Logger logger = Logger.getLogger(OkoooOddsOpPageParser.class);
	
	/**
	 * @param acceptType
	 */
	public OkoooOddsOpPageParser(String acceptType)
	{
		super(acceptType);
	}
	
	/**
	 * @param acceptType
	 */
	public OkoooOddsOpPageParser()
	{
		this(OkoooConstants.PAGE_ODDS_OP);
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
		
		//解析比赛信息
		Element matchEl = document.selectFirst(".matchnavboxbg .qbox");
		if (matchEl == null)
		{
			return null;
		}
		
		DataList<OkoooOddsOp> okoooOddsOps = new DataList<>();
		DataList<CasinoComp> comps = new DataList<>();
		comps.setOverwrite(false);
		
		results.put(SoccerConstants.SOCCER_DATA_ODDS_OKOOO_OP_LIST, okoooOddsOps);
		results.put(SoccerConstants.SOCCER_DATA_CASINO_COMP_LIST, comps);
		
		//解析百家欧赔数据
		Elements oddsEls = document.select("#data_main_content table tbody tr");
		for (Element element : oddsEls)
		{
			try
			{
				parseOkoooOddsOpRecords(element, mid, DateUtil.tryToParseDate(matchtime), okoooOddsOps, comps);
			}
			catch (Exception e)
			{
			}
		}
				
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
	 * 解析欧赔数据记录
	 * @param okoooOddsOps 澳客欧赔数据
	 * @param element 元素
	 */
	protected void parseOkoooOddsOpRecords(Element element, String mid, Date matchTime, List<OkoooOddsOp> okoooOddsOps,
			DataList<CasinoComp> comps)
	{
		int firsttime;
		String gid;
		String gname;
		//String ordinary;
		
		float firstwinodds;
		float firstdrawodds;
		float firstloseodds;
		
		Elements cols = element.select("td");
		if(cols.size() < 10)
		{
			return;
		}
		firsttime = NumberUtil.parseInt(element.attr("data-time"));
		gid = cols.get(0).selectFirst("input").attr("value");
		//ordinary = cols.get(0).select("label span").text();
		gname = cols.get(1).text();
		
		//以下为初始赔率值
		firstwinodds = getOddsValue(cols.get(2).text());
		firstdrawodds = getOddsValue(cols.get(3).text());
		firstloseodds = getOddsValue(cols.get(4).text());
		
		OkoooOddsOp firstOp = new OkoooOddsOp();
		OkoooOddsOp lastOp = new OkoooOddsOp();
		CasinoComp comp = new CasinoComp();
		
		comp.setSource(OkoooConstants.SOURCE_OKOOO);
		comp.setCorpid(gid);
		comp.setName(gname);
		comp.setType(SoccerConstants.ODDS_TYPE_OP);
		
		firstOp.setCorpid(gid);
		firstOp.setMid(mid);
		
		lastOp.setMid(mid);
		lastOp.setCorpid(gid);
		
		//firstOp.setGname(gname);
		firstOp.setWinodds(firstwinodds);
		firstOp.setDrawodds(firstdrawodds);
		firstOp.setLoseodds(firstloseodds);
		
		Date d = DateUtil.add(matchTime, - ((long) firsttime) * 1000);
		firstOp.setOpentime(d);
		//ops.add(op);
		
		//以下为最新赔率值
		float winodds;
		float drawodds;
		float loseodds;
		float winprob;
		float drawprob;
		float loseprob;
		float winkelly;
		float drawkelly;
		float losekelly;
		float lossratio;		
		String time;
		long t = 0;
		
		winodds = getOddsValue(cols.get(5).text());
		drawodds = getOddsValue(cols.get(6).text());
		loseodds = getOddsValue(cols.get(7).text());		
		time = cols.get(8).attr("attr");
		if(StringUtils.isNotEmpty(time))
		{
			t = getTime(time);
			d = DateUtil.add(matchTime, -t);
			//time = DateUtil.DATE_TIME_FORMAT.format(d);
			lastOp.setOpentime(d);
		}
		
		winprob = getOddsValue(cols.get(9).text());
		drawprob = getOddsValue(cols.get(10).text());
		loseprob = getOddsValue(cols.get(11).text());
		winkelly = getOddsValue(cols.get(12).text());
		drawkelly = getOddsValue(cols.get(13).text());
		losekelly = getOddsValue(cols.get(14).text());
		lossratio = getOddsValue(cols.get(15).text());
		
		//op = new OkoooOp();
		lastOp.setWinodds(winodds);
		lastOp.setDrawodds(drawodds);
		lastOp.setLoseodds(loseodds);
		
		firstOp.setWinprob(winprob);
		firstOp.setDrawprob(drawprob);
		firstOp.setLoseprob(loseprob);
		firstOp.setWinkelly(winkelly);
		firstOp.setDrawkelly(drawkelly);
		firstOp.setLosekelly(losekelly);
		firstOp.setLossratio(lossratio);
		
		lastOp.setWinprob(winprob);
		lastOp.setDrawprob(drawprob);
		lastOp.setLoseprob(loseprob);
		lastOp.setWinkelly(winkelly);
		lastOp.setDrawkelly(drawkelly);
		lastOp.setLosekelly(losekelly);
		lastOp.setLossratio(lossratio);
		//op.setIsend(false);
		//op.setIsfirst(false);
		//firstOp.setOddstype("op");
		//op.setSource(SoccerConstants.DATA_SOURCE_OKOOO);
				
		okoooOddsOps.add(firstOp);
		okoooOddsOps.add(lastOp);
		comps.add(comp);
	}
	
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
	
	/**
	 * 解析赔率数据
	 * @param value
	 * @return
	 */
	protected float getOddsValue(String value)
	{
		return NumberUtil.parseFloatFromString(value);
	}
}
