/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooUtil.java   
 * @Package com.loris.soccer.data.okooocom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.okooo.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.fetcher.impl.HtmlUnitFetcher;
import com.loris.client.model.WebPage;
import com.loris.client.task.util.ThreadUtil;
import com.loris.common.model.TableRecords;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.OkoooPageCreator;
import com.loris.soccer.data.okooo.OkoooPageParser;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.OkoooOddsOp;
import com.loris.soccer.model.OkoooOddsYp;

/**   
 * @ClassName:  OkoooUtil.java   
 * @Description: 澳客网的通用工具类
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooUtil
{
	private static Logger logger = Logger.getLogger(OkoooUtil.class);
	
	/**
	 * 下载澳客网赔率数据页面
	 * @param fetcher
	 * @param page
	 * @return
	 * @throws IOException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 * @throws WebParserException
	 */
	public static TableRecords downloadOkoooOddsPage(HtmlUnitFetcher fetcher, WebPage page) 
			throws IOException, HostForbiddenException, UrlFetchException, WebParserException
	{
		return downloadOkoooOddsPage(fetcher, page, 30);
	}
	
	/**
	 * 下载澳客网赔率数据页面
	 * @param fetcher 下载工具
	 * @param page 页面
	 * @param pageNum 每一页包含的数据量
	 * @return
	 * @throws IOException
	 * @throws HostForbiddenException
	 * @throws UrlFetchException
	 * @throws WebParserException
	 */
	public static TableRecords downloadOkoooOddsPage(HtmlUnitFetcher fetcher, WebPage page, int pageNum) 
			throws IOException, HostForbiddenException, UrlFetchException, WebParserException
	{
		TableRecords results = null;
		int totalComps = 0;				//总的单位家数
		String type = null;
		switch (page.getType())
		{
		case OkoooConstants.PAGE_ODDS_YP:
			type = OkoooConstants.PAGE_ODDS_YP_CHILD;
			break;
		case OkoooConstants.PAGE_ODDS_OP:
			type = OkoooConstants.PAGE_ODDS_OP_CHILD;
			break;
		case OkoooConstants.PAGE_ODDS_OP_CHILD:
		case OkoooConstants.PAGE_ODDS_YP_CHILD:
			type = page.getType();
			break;
		default:
			logger.info("The page type is '" + page.getType() + "' is not supported by the downloadOkoooOddsPage method.");
			return null;
		}
		
		int currentPageIndex = 0;
		Map<String, String> params = page.getParams();
		WebPage pageSource = null;
		
		while(fetcher.download(pageSource = createCurrentWebPage(type, params, currentPageIndex)))
		{
			TableRecords records = OkoooPageParser.parseWebPage(pageSource);
			
			//如果是记录数据为空，则跳出循环
			if(records == null || records.isEmpty())
			{
				break;
			}
			
			if(results == null)
			{
				results = records;
			}
			else
			{
				combineOkoooOddsTableRecords(results, records);
			}
			
			if(totalComps <= 0)
			{
				totalComps = getCompsTotalNum(records);
			}
			
			currentPageIndex ++;
			if(totalComps > 0 && currentPageIndex * pageNum >= totalComps)
			{
				break;
			}
			
			//没有下载到全部的数据，则停止下载
			if(getCurrentOddsNum(records) < pageNum)
			{
				break;
			}
			
			//为了模拟人为操作，需要进行等待
			ThreadUtil.sleep(1000);
		}

		return results;
	}
	
	/**
	 * 合并两个数据记录
	 * @param dest 
	 * @param newRecords
	 */
	@SuppressWarnings("unchecked")
	protected static void combineOkoooOddsTableRecords(TableRecords dest, TableRecords newRecords)
	{
		for (String key : newRecords.keySet())
		{
			Object value = newRecords.get(key);
			switch (key)
			{
			case SoccerConstants.SOCCER_DATA_CASINO_OKOOO_COMP_LIST:
				List<CasinoComp> newComps = (List<CasinoComp>)value;
				DataList<CasinoComp> destComps = (DataList<CasinoComp>) dest.get(key);
				
				if(destComps == null)
				{
					dest.put(key, value);
				}
				else
				{
					combineList(destComps, newComps);
				}
				break;
			case SoccerConstants.SOCCER_DATA_ODDS_OKOOO_OP_LIST:
				List<OkoooOddsOp> newOps = (List<OkoooOddsOp>)value;
				DataList<OkoooOddsOp> destOps = (DataList<OkoooOddsOp>)dest.get(key);
				if(destOps == null)
				{
					dest.put(key, value);
				}
				else
				{
					combineList(destOps, newOps);
				}
				break;
			case SoccerConstants.SOCCER_DATA_ODDS_OKOOO_YP_LIST:
				List<OkoooOddsYp> newYps = (List<OkoooOddsYp>)value;
				DataList<OkoooOddsYp> destYps = (DataList<OkoooOddsYp>)dest.get(key);
				if(destYps == null)
				{
					dest.put(key, value);
				}
				else
				{
					combineList(destYps, newYps);
				}
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * 添加数据到现有的记录中
	 * @param source
	 * @param dest
	 */
	protected static <T> void combineList(DataList<T> dest, List<T> source)
	{
		for (T object : source)
		{
			dest.add(object);
		}
	}
	
	/**
	 * 获得总的单位数
	 * @param records 记录值
	 * @return 数据量
	 */
	protected static int getCompsTotalNum(TableRecords records)
	{
		Integer t = (Integer)records.get(OkoooConstants.NAME_FIELD_CORP_TOTAL_NUM);
		return t == null ? 0 : t;
	}
	
	/**
	 * 获得当前下载的赔率数据的总数
	 * @param records 数据记录表
	 * @return 数量，如果没有则返回0
	 */
	protected static int getCurrentOddsNum(TableRecords records)
	{
		List<?> oddsList = null;
		oddsList = (List<?>)records.get(SoccerConstants.SOCCER_DATA_ODDS_OKOOO_OP_LIST);
		if(oddsList != null)
		{
			return oddsList.size() / 2;
		}
		oddsList = (List<?>) records.get(SoccerConstants.SOCCER_DATA_ODDS_OKOOO_YP_LIST);
		if(oddsList != null)
		{
			return oddsList.size() / 2;
		}
		return 0;
	}
	
	/**
	 * 创建当前下载页面
	 * @param type
	 * @param params
	 * @param pageIndex
	 * @return
	 */
	protected static WebPage createCurrentWebPage(String type, Map<String, String> params, int pageIndex)
	{
		params.put(SoccerConstants.NAME_FIELD_PAGE, Integer.toString(pageIndex));
		return OkoooPageCreator.createOkoooWebPage(type, params);
	}
}
