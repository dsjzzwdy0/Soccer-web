/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooOddsOpChildPageParser.java   
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
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.OkoooOddsOp;

/**   
 * @ClassName:  OkoooOddsOpChildPageParser.java   
 * @Description: 澳客网欧赔数据下载解析器(子页面)  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooOddsOpChildPageParser extends OkoooOddsOpPageParser
{
	private static Logger logger = Logger.getLogger(OkoooOddsOpChildPageParser.class);
	
	/**
	 * @param acceptType
	 */
	public OkoooOddsOpChildPageParser()
	{
		super(OkoooConstants.PAGE_ODDS_OP_CHILD);
	}

	/* (non-Javadoc)
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
		
		try
		{
			String content = scriptEl.data();
			String dataStr = getDataStr(content, ";|\n", "var data_str");	
		
			if(StringUtils.isNoneBlank(dataStr))
			{
				DataList<OkoooOddsOp> okoooOddsOps = new DataList<>();
				DataList<CasinoComp> comps = new DataList<>();
				comps.setOverwrite(false);
				
				results.put(SoccerConstants.SOCCER_DATA_ODDS_OKOOO_OP_LIST, okoooOddsOps);
				results.put(SoccerConstants.SOCCER_DATA_CASINO_COMP_LIST, comps);

				parseOddsOpListJson(dataStr, mid, DateUtil.tryToParseDate(matchtime), okoooOddsOps, comps);
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
		catch (Exception e) {
			logger.info("Error when parse the Odds records values.");
		}
		return results;
	}

	/**
	 * 解析对象与数据
	 * @param json Json字符串
	 */
	protected void parseOddsOpListJson(String json, String mid, Date matchTime, List<OkoooOddsOp> ops, 
			List<CasinoComp> comps)
	{
		JSONArray array = JSON.parseArray(json);
		for (Object object : array)
		{
			if(object instanceof JSONObject)
			{
				try
				{
					parseOddsOp((JSONObject)object, mid, matchTime, ops, comps);
				}
				catch(Exception e)
				{
				}
			}
		}
	}
	
	/**
	 * 解析亚盘记录数据
	 * @param object
	 */
	protected void parseOddsOp(JSONObject object, String mid, Date matchTime, List<OkoooOddsOp> ops, List<CasinoComp> comps)
	{
		OkoooOddsOp firstOp = new OkoooOddsOp();
		OkoooOddsOp lastOp = new OkoooOddsOp();
		CasinoComp comp = new CasinoComp();
		
		firstOp.setMid(mid);
		lastOp.setMid(mid);
		
		comp.setSource(OkoooConstants.SOURCE_OKOOO);
		comp.setType(SoccerConstants.ODDS_TYPE_OP);
		
		
		for (String key : object.keySet())
		{
			Object value = object.get(key);
			if("Start".equalsIgnoreCase(key))
			{
				parseFirstOdds(firstOp, (JSONObject)value);
			}
			else if("CompanyName".equalsIgnoreCase(key))
			{
				//firstOp.setGname(value.toString());
				//lastOp.setCorpid(corpid);
				comp.setName(value.toString());
			}
			else if("MakerID".equalsIgnoreCase(key))
			{
				String corpid = value.toString();
				firstOp.setCorpid(corpid);
				lastOp.setCorpid(corpid);
				comp.setCorpid(corpid);
			}
			else if("End".equalsIgnoreCase(key))
			{
				parseLastOdds(firstOp, (JSONObject)value);
			}
			else if("startUpdatetime".equalsIgnoreCase(key))
			{
				int l =((Integer)value).intValue();
				if(matchTime != null)
				{
					Date d = DateUtil.add(matchTime, -l * 1000);
					firstOp.setOpentime(d);
				}
			}
			else if("Updatetime".equalsIgnoreCase(key))
			{
				int l = ((Integer)value).intValue();
				if(matchTime != null)
				{
					Date d = DateUtil.add(matchTime, l * 1000);
					lastOp.setOpentime(d);
					//firstOp.setLasttime(DateUtil.DATE_TIME_FORMAT.format(d));
				}
			}
			else if("Createtime".equalsIgnoreCase(key))
			{
				long l = NumberUtil.parseLong(value.toString()) * 1000;
				firstOp.setOpentime(new Date(l));
				//firstOp.setFirsttime(DateUtil.formatDate(l));
			}
			else if("Kelly".equalsIgnoreCase(key))
			{
				parseKelly(firstOp, (JSONObject)value);
				lastOp.setWinkelly(firstOp.getWinkelly());
				lastOp.setDrawkelly(firstOp.getDrawkelly());
				lastOp.setLosekelly(firstOp.getLosekelly());
			}
			else if("Radio".equalsIgnoreCase(key))
			{
				parseProb(firstOp, (JSONObject)value);
				lastOp.setWinprob(firstOp.getWinprob());
				lastOp.setDrawprob(firstOp.getDrawprob());
				lastOp.setLoseprob(firstOp.getLoseprob());
			}
			else if("Payoff".equalsIgnoreCase(key))
			{
				firstOp.setLossratio(NumberUtil.parseFloat(value.toString()));
				lastOp.setLossratio(firstOp.getLossratio());
			}
		}
		ops.add(firstOp);
		ops.add(lastOp);
		comps.add(comp);
	}
	
	/**
	 * 解析最新的亚盘数据
	 * @param op 亚盘记录
	 * @param object 数据对象
	 */
	protected void parseLastOdds(OkoooOddsOp op, JSONObject object)
	{
		for (String key : object.keySet())
		{
			Object value = object.get(key);
			if("draw".equalsIgnoreCase(key))
			{
				op.setDrawodds(NumberUtil.parseFloat(value.toString()));
			}
			else if("home".equalsIgnoreCase(key))
			{
				op.setWinodds(NumberUtil.parseFloat(value.toString()));
			}
			else if("away".equalsIgnoreCase(key))
			{
				op.setLoseodds(NumberUtil.parseFloat(value.toString()));
			}
		}
	}
	
	/**
	 * 解析初始亚盘数据
	 * @param op 亚盘数据
	 * @param object 数据对象
	 */
	protected void parseFirstOdds(OkoooOddsOp op, JSONObject object)
	{
		for (String key : object.keySet())
		{
			Object value = object.get(key);
			if("draw".equalsIgnoreCase(key))
			{
				op.setDrawodds(NumberUtil.parseFloat(value.toString()));
			}
			else if("home".equalsIgnoreCase(key))
			{
				op.setWinodds(NumberUtil.parseFloat(value.toString()));
			}
			else if("away".equalsIgnoreCase(key))
			{
				op.setLoseodds(NumberUtil.parseFloat(value.toString()));
			}
		}
	}
	
	/**
	 * 解析凯利数据
	 * @param yp 亚盘数据
	 * @param object 数据对象
	 */
	protected void parseKelly(OkoooOddsOp yp, JSONObject object)
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
	protected void parseProb(OkoooOddsOp yp, JSONObject object)
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
	 * 解析欧赔数据的总公司数量
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
