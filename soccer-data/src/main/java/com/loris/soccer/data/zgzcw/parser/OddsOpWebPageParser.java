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

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.common.util.ToolUtil;
import com.loris.common.util.URLParser;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.base.AbstractZgzcwMatchWebPageParser;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.RecordOddsOp;

/**   
 * @ClassName:  League   
 * @Description: 解析比赛的数据与最新的欧赔数据
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsOpWebPageParser extends AbstractZgzcwMatchWebPageParser
{
	/**
	 * Create a new instance of OddsOpWebPageParser
	 */
	public OddsOpWebPageParser()
	{
		super(ZgzcwConstants.PAGE_ODDS_OP);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.parser.impl.AbstractWebPageParser#parse(com.loris.client.model.WebPage, org.jsoup.nodes.Document, com.loris.common.model.TableRecords)
	 */
	@Override
	protected TableRecords parse(WebPage page, Document document, TableRecords results) throws WebParserException
	{
		Element element = document.selectFirst(".main #data-body");
		if(element == null)
		{
			throw new WebParserException("The document is not a validate Soccer Match op page.");
		}
		
		Elements elements = element.select("table tbody tr");
		if(elements == null || elements.size() <= 0)
		{
			return null;
		}
		
		String mid = page.getParams().get(SoccerConstants.NAME_FIELD_MID);
		DataList<OddsOp> ops = new DataList<>();
		DataList<CasinoComp> comps = new DataList<>();
		DataList<RecordOddsOp> recordOps = new DataList<>();
		
		recordOps.setOverwrite(true);
		
		for (Element element2 : elements)
		{
			parseOdds(element2, mid, ops, recordOps, comps);
		}
		results.put(SoccerConstants.SOCCER_DATA_ODDS_OP_LIST, ops);
		results.put(SoccerConstants.SOCCER_DATA_CASINO_COMP_LIST, comps);
		results.put(SoccerConstants.SOCCER_DATA_RECORD_ODDS_OP_LIST, recordOps);
		
		return results;
	}
	
	/**
	 * Parse the value.
	 * 
	 * @param element
	 * @throws DateParseException 
	 */
	protected void parseOdds(Element element, String mid, 
			List<OddsOp> ops, List<RecordOddsOp> recordOps, List<CasinoComp> comps)
	{
		OddsOp firstOdds = new OddsOp(mid);
		OddsOp odds = new OddsOp(mid);
		CasinoComp comp = new CasinoComp();
		
		comp.setSource(SoccerConstants.SOURCE_ZGZCW);
		comp.setType(SoccerConstants.ODDS_TYPE_OP);
		firstOdds.setSource(SoccerConstants.SOURCE_ZGZCW);
		odds.setSource(SoccerConstants.SOURCE_ZGZCW);
		
		String first = element.attr("firsttime");
		String last = element.attr("lasttime");
		
		Date firstTime = DateUtil.tryToParseDate(first);		
		firstOdds.setOpentime(firstTime);
		if(StringUtils.isNotBlank(last))
			odds.setOpentime(new Date(Long.parseLong(last)));
		
		Elements elements = element.select("td");
		int size = elements.size();
		
		//单位
		parseGamingCompany(comp, firstOdds, odds, elements.get(0), elements.get(1), elements.get(5));
		
		Element[] els = new Element[6];
		els[0] = elements.get(2);
		els[1] = elements.get(3);
		els[2] = elements.get(4);
		els[3] = elements.get(5);
		els[4] = elements.get(6);
		els[5] = elements.get(7);			
		parseOddsValue(firstOdds, odds, els, elements.get(8));      //初始赔率、最新赔率、赔率时间
		
		//解析欧盘
		if(size == 17)
		{	
			els[0] = elements.get(9);
			els[1] = elements.get(10);
			els[2] = elements.get(11);
			els[3] = elements.get(12);
			els[4] = elements.get(13);
			els[5] = elements.get(14);
		}
		else if(size == 14)
		{			
			els[0] = elements.get(9);
			els[1] = elements.get(10);
			els[2] = null;
			els[3] = elements.get(11);
			els[4] = elements.get(12);
			els[5] = null;
		}
		else 
		{
			//其它的情况下无法解析数据
			return;
		}
		parseProbAndKelly(firstOdds, odds, els, elements.get(13));    //概率与凯利指数、赔付率
		
		ops.add(firstOdds);
		ops.add(odds);
		comps.add(comp);
		
		if(ToolUtil.isNotEmpty(firstOdds.getOpentime()) && ToolUtil.isNotEmpty(odds.getOpentime()))
		{
			RecordOddsOp recordOp = new RecordOddsOp();
			recordOp.setFirstOddsOp(firstOdds);
			recordOp.setLastOddsOp(odds);
			recordOps.add(recordOp);
		}
	}
	
	/**
	 * Parse the company. 
	 * 
	 * @param odds
	 * @param name
	 * @param id
	 */
	private void parseGamingCompany(CasinoComp comp, OddsOp firstOdds, OddsOp odds, Element idx, Element name, Element id)
	{
		String gname;
		String corpid;
		boolean isMain = false;
		
		gname = name.text();
		corpid = getGid(id.select("a").first());
		isMain = isMainCompany(name);
		
		firstOdds.setCorpid(corpid);
		//firstOdds.setCorpname(gname);
		odds.setCorpid(corpid);
		//odds.setCorpname(gname);
		
		comp.setCorpid(corpid);
		comp.setName(gname);
		comp.setIsmain(isMain);
		//odds.setOrdinary(index);
		//odds.setGid(gid);
		//odds.setIsmain(isMain);
		//odds.setGname(gname);
	}
	
	/**
	 * Parse the odds value.
	 * 
	 * @param odds
	 * @param oddsEls
	 */
	private void parseOddsValue(OddsOp firstOdds, OddsOp odds, Element[] oddsEls, Element time)
	{
		float wodds, dodds, lodds;
		
		wodds = getOddsValueAsAttr(oddsEls[0]);
		dodds = getOddsValueAsAttr(oddsEls[1]);
		lodds = getOddsValueAsAttr(oddsEls[2]);
		
		//odds.setFirstwinodds(wodds);
		//odds.setFirstdrawodds(dodds);
		//odds.setFirstloseodds(lodds);
		firstOdds.setWinodds(wodds);
		firstOdds.setDrawodds(dodds);
		firstOdds.setLoseodds(lodds);
		
		wodds = getOddsValueAsAttr(oddsEls[3]);
		dodds = getOddsValueAsAttr(oddsEls[4]);
		lodds = getOddsValueAsAttr(oddsEls[5]);
		
		odds.setWinodds(wodds);
		odds.setDrawodds(dodds);
		odds.setLoseodds(lodds);
	}
	
	/**
	 * Parse the probability and Kelly value.
	 * 
	 * @param odds
	 * @param oddsEls
	 * @param el
	 */
	private void parseProbAndKelly(OddsOp firstOdds, OddsOp odds, Element[] oddsEls, Element el)
	{
		float l1, l2, l3;
		l1 = getOddsValueAsAttr(oddsEls[0]);
		l2 = getOddsValueAsAttr(oddsEls[1]);
		l3 = getOddsValueAsAttr(oddsEls[2]);
		
		firstOdds.setWinprob(l1);
		firstOdds.setDrawprob(l2);
		firstOdds.setLoseprob(l3);
		
		odds.setWinprob(l1);
		odds.setDrawprob(l2);
		odds.setLoseprob(l3);
		
		l1 = getOddsValueAsAttr(oddsEls[3]);
		l2 = getOddsValueAsAttr(oddsEls[4]);
		l3 = getOddsValueAsAttr(oddsEls[5]);
		
		firstOdds.setWinkelly(l1);
		firstOdds.setDrawkelly(l2);
		firstOdds.setLosekelly(l3);
		
		odds.setWinkelly(l1);
		odds.setDrawkelly(l2);
		odds.setLosekelly(l3);
		
		float loss = getOddsValueAsAttr(el);
		firstOdds.setLossratio(loss);
		odds.setLossratio(loss);
	}

	/**
	 * Get the odds value.
	 * 
	 * @param el
	 * @return
	 */
	private float getOddsValueAsAttr(Element el)
	{
		if(el != null)
		{
			return NumberUtil.parseFloat(el.attr(dataAttr));
		}
		return 0.0f;
	}
	
	/**
	 * Check there are the hot label.
	 * 
	 * @param element
	 * @return
	 */
	private boolean isMainCompany(Element element)
	{
		Element el = element.select(".hot").first();
		return el == null ? false : true;
	}
	
	/**
	 * Get the Gambling Company.
	 * 
	 * @param element
	 * @return
	 */
	private String getGid(Element element)
	{
		Element detail = element.select("a").first();
		String url = detail.attr("href");		
		try
		{
			return URLParser.fromURL(url).compile().getParameter("company_id");
		}
		catch(Exception e)
		{			
		}
		return "";
	}
}
