/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooOddsYpChildPageParser.java   
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.dict.HandicapDict;
import com.loris.soccer.model.OkoooCasinoComp;
import com.loris.soccer.model.OkoooOddsYp;

/**   
 * @ClassName:  OkoooOddsYpChildPageParser.java   
 * @Description: 澳客亚盘子页面解析工具   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooOddsYpChildPageParser extends OkoooOddsYpPageParser
{
	private static Logger logger = Logger.getLogger(OkoooOddsYpChildPageParser.class);
	
	/**
	 * @param acceptType
	 */
	public OkoooOddsYpChildPageParser()
	{
		super(OkoooConstants.PAGE_ODDS_YP_CHILD);
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
		
		Element scriptEl = document.selectFirst("script");
		if(scriptEl == null || StringUtils.isEmpty(scriptEl.data()))
		{
			logger.info("The script value is null.");
			return null;
		}
		
		//logger.info(page.getContent());
		try
		{
			String content = scriptEl.data();
			String dataStr = getDataStr(content, ";|\n", "var data_str");
			
			if(StringUtils.isNoneBlank(dataStr))
			{
				DataList<OkoooOddsYp> okoooOddsYps = new DataList<>();
				DataList<OkoooCasinoComp> comps = new DataList<>();
				comps.setOverwrite(false);
				
				results.put(SoccerConstants.SOCCER_DATA_ODDS_OKOOO_YP_LIST, okoooOddsYps);
				results.put(SoccerConstants.SOCCER_DATA_CASINO_OKOOO_COMP_LIST, comps);
				
				parseJson(dataStr, mid, DateUtil.tryToParseDate(matchtime), okoooOddsYps, comps);
			}
			
			String staticStr = getDataStr(content, ";|\n", "var static_str");
			if(StringUtils.isNotEmpty(staticStr))
			{
				int total = parseTotalCount(staticStr);
				if(total > 0)
				{
					results.put(OkoooConstants.NAME_FIELD_CORP_TOTAL_NUM, total);
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Error when parse OddsYp: " + e.toString());
			return null;
		}
		
		return results;
	}
	
	/**
	 * 解析对象与数据
	 * @param json Json字符串
	 */
	protected void parseJson(String json, String mid, Date matchTime, List<OkoooOddsYp> yps, List<OkoooCasinoComp> comps)
	{
		JSONArray array = JSON.parseArray(json);
		for (Object object : array)
		{
			if(object instanceof JSONObject)
			{
				parseOddsYp((JSONObject)object, mid, matchTime, yps, comps);
			}
		}
	}
	
	/**
	 * 解析亚盘记录数据
	 * @param object
	 */
	protected void parseOddsYp(JSONObject object, String mid, Date matchTime, List<OkoooOddsYp> yps, List<OkoooCasinoComp> comps)
	{
		OkoooOddsYp firstYp = new OkoooOddsYp();
		OkoooOddsYp lastYp = new OkoooOddsYp();
		OkoooCasinoComp comp = new OkoooCasinoComp();
		
		comp.setType(SoccerConstants.ODDS_TYPE_YP);
		comp.setSource(OkoooConstants.SOURCE_OKOOO);
		
		firstYp.setMid(mid);
		lastYp.setMid(mid);
		
		for (String key : object.keySet())
		{
			Object value = object.get(key);
			if("Start".equalsIgnoreCase(key))
			{
				parseFirstOdds(firstYp, (JSONObject)value);
			}
			else if("CompanyName".equalsIgnoreCase(key))
			{
				comp.setName(value.toString());
			}
			else if("MakerID".equalsIgnoreCase(key))
			{
				String corpid = value.toString();
				comp.setCorpid(corpid);
				firstYp.setCorpid(corpid);
				lastYp.setCorpid(corpid);
			}
			else if("End".equalsIgnoreCase(key))
			{
				parseLastOdds(lastYp, (JSONObject)value);
			}
			else if("Updatetime".equalsIgnoreCase(key))
			{
				int l = ((Integer)value).intValue();
				if(matchTime != null)
				{
					Date d = DateUtil.add(matchTime, l);
					lastYp.setOpentime(d);
					//firstYp.setLasttime(DateUtil.DATE_TIME_FORMAT.format(d));
				}
			}
			else if("Createtime".equalsIgnoreCase(key))
			{
				long l = NumberUtil.parseLong(value.toString()) * 1000;
				firstYp.setOpentime(new Date(l));
				//firstYp.setFirsttime(DateUtil.formatDate(l));
			}
			else if("Kelly".equalsIgnoreCase(key))
			{
				parseKelly(firstYp, (JSONObject)value);
				lastYp.setWinkelly(firstYp.getWinkelly());
				lastYp.setLosekelly(firstYp.getLosekelly());
			}
			else if("Radio".equalsIgnoreCase(key))
			{
				parseProb(firstYp, (JSONObject)value);
				lastYp.setWinprob(firstYp.getWinprob());
				lastYp.setLoseprob(firstYp.getLoseprob());
			}
			else if("Payoff".equalsIgnoreCase(key))
			{
				firstYp.setLossratio(NumberUtil.parseFloat(value.toString()));
				lastYp.setLossratio(firstYp.getLossratio());
			}
			/*else if("DisplayOrder".equalsIgnoreCase(key))
			{
				yp.setOrdinary(value.toString());
			}*/
		}
		yps.add(firstYp);
		yps.add(lastYp);
		comps.add(comp);
	}
	
	/**
	 * 解析最新的亚盘数据
	 * @param yp 亚盘记录
	 * @param object 数据对象
	 */
	protected void parseLastOdds(OkoooOddsYp yp, JSONObject object)
	{
		for (String key : object.keySet())
		{
			Object value = object.get(key);
			if("BoundaryCnName".equalsIgnoreCase(key))
			{
				float handicap = HandicapDict.getHandicapValue(value.toString());
				yp.setHandicap(handicap);
			}
			else if("home".equalsIgnoreCase(key))
			{
				yp.setWinodds(NumberUtil.parseFloat(value.toString()));
			}
			else if("away".equalsIgnoreCase(key))
			{
				yp.setLoseodds(NumberUtil.parseFloat(value.toString()));
			}
		}
	}
	
	/**
	 * 解析初始亚盘数据
	 * @param yp 亚盘数据
	 * @param object 数据对象
	 */
	protected void parseFirstOdds(OkoooOddsYp yp, JSONObject object)
	{
		for (String key2 : object.keySet())
		{
			Object value = object.get(key2);
			if("BoundaryCnName".equalsIgnoreCase(key2))
			{
				float handicap = HandicapDict.getHandicapValue(value.toString());
				yp.setHandicap(handicap);
			}
			else if("home".equalsIgnoreCase(key2))
			{
				yp.setWinodds(NumberUtil.parseFloat(value.toString()));
			}
			else if("away".equalsIgnoreCase(key2))
			{
				yp.setLoseodds(NumberUtil.parseFloat(value.toString()));
			}
		}
	}
	
	/**
	 * 解析凯利数据
	 * @param yp 亚盘数据
	 * @param object 数据对象
	 */
	protected void parseKelly(OkoooOddsYp yp, JSONObject object)
	{
		for (String key : object.keySet())
		{
			Object value = object.get(key);
			if("home".equalsIgnoreCase(key))
			{
				yp.setWinkelly(NumberUtil.parseFloat(value.toString()));
			}
			else if("away".equalsIgnoreCase(key))
			{
				yp.setLosekelly(NumberUtil.parseFloat(value.toString()));
			}
		}
	}
	
	/**
	 * 解析概率值数据
	 * @param yp 亚盘数据
	 * @param object 数据对象
	 */
	protected void parseProb(OkoooOddsYp yp, JSONObject object)
	{
		for (String key : object.keySet())
		{
			Object value = object.get(key);
			if("home".equalsIgnoreCase(key))
			{
				yp.setWinprob(NumberUtil.parseFloat(value.toString()));
			}
			else if("away".equalsIgnoreCase(key))
			{
				yp.setLoseprob(NumberUtil.parseFloat(value.toString()));
			}
		}
	}

	/**
	 * 解析亚盘数据的总公司数量
	 * @param staticStr 字符串
	 * @return 数量
	 */
	protected int parseTotalCount(String staticStr)
	{
		try
		{
			JSONObject object = JSON.parseObject(staticStr);
			if(object != null)
			{
				return NumberUtil.parseInt(object.get("count"));
			}
		}
		catch (Exception e) {
			logger.info("Error when parse the yp total number.");
		}		
		return -1;
	}
}
