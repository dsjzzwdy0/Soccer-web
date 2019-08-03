/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooPageCreator.java   
 * @Package com.loris.soccer.data.okooocom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.okooo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.loris.client.fetcher.util.HttpUtil;
import com.loris.client.model.WebPage;
import com.loris.common.util.DateUtil;
import com.loris.common.util.EncodingUtil;
import com.loris.common.util.NumberUtil;
import com.loris.common.util.URLUtil;
import com.loris.soccer.constant.SoccerConstants;
import static com.loris.soccer.data.okooo.OkoooConstants.*;

/**   
 * @ClassName:  OkoooPageCreator.java   
 * @Description: 澳客足彩数据页面创建管理器
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OkoooPageCreator
{
	/** 通用的页面编码 */
	protected static String encoding = EncodingUtil.ENCODING_GB2312;
	
	/** 主机地址 */
	protected static String host = "www.okooo.com";
	
	/** 数据类型与基础页面的对应工具 */
	public static Map<String, Double> PAGE_PRIORITIES = new HashMap<>();
	public static Map<String, String> PAGE_BASE_URLS = new HashMap<>();
	
	static
	{
		PAGE_PRIORITIES.put(PAGE_LOTTERY_BD, 3500.0);
		PAGE_PRIORITIES.put(PAGE_LOTTERY_JC, 3500.0);
		PAGE_PRIORITIES.put(PAGE_ODDS_OP, 1000.0);
		PAGE_PRIORITIES.put(PAGE_ODDS_YP, 1000.0);
		
		PAGE_BASE_URLS.put(PAGE_LOTTERY_BD, 			"http://www.okooo.com/danchang/");    	//&issue=
		PAGE_BASE_URLS.put(PAGE_LOTTERY_JC, 			"http://www.okooo.com/jingcai/");
		PAGE_BASE_URLS.put(PAGE_ODDS_OP,      		"http://www.okooo.com/soccer/match/"); 	// http://www.okooo.com/soccer/match/1061305/odds/
		PAGE_BASE_URLS.put(PAGE_ODDS_YP,      		"http://www.okooo.com/soccer/match/"); 	// http://www.okooo.com/soccer/match/1061305/ah/
		PAGE_BASE_URLS.put(PAGE_ODDS_OP_CHILD, 		"http://www.okooo.com/soccer/match/");	// http://www.okooo.com/soccer/match/1049273/odds/ajax/?page=5&trnum=150&companytype=BaijiaBooks&type=0
		PAGE_BASE_URLS.put(PAGE_ODDS_YP_CHILD, 		"http://www.okooo.com/soccer/match/");	// http://www.okooo.com/soccer/match/1049273/ah/ajax/?page=2&trnum=60&companytype=BaijiaBooks
	}
	
	/**
	 * 创建数据下载页面
	 * @param type 类型
	 * @param params 参数
	 * @return 数据页面
	 * @throws IllegalArgumentException 如果type没有定义或参数错误，则抛出异常值
	 */
	public static WebPage createOkoooWebPage(String type) throws IllegalArgumentException
	{
		return createOkoooWebPage(type, null);
	}
	
	/**
	 * 创建数据下载页面
	 * @param type 类型
	 * @param params 参数
	 * @return 数据页面
	 * @throws IllegalArgumentException 如果type没有定义或参数错误，则抛出异常值
	 */
	public static WebPage createOkoooWebPage(String type, Map<String, String> params) throws IllegalArgumentException
	{
		return createOkoooWebPage(type, encoding, params);
	}
	
	/**
	 * 创建数据下载页面
	 * @param type 类型
	 * @param params 参数
	 * @return 数据页面
	 * @throws IllegalArgumentException 如果type没有定义或参数错误，则抛出异常值
	 */
	public static WebPage createOkoooWebPage(String type, String encoding, Map<String, String> params) throws IllegalArgumentException
	{
		if(!PAGE_BASE_URLS.containsKey(type))
		{
			throw new IllegalArgumentException("The type '" + type + "' is not defined in this PageCreator.");
		}
		
		WebPage page = new WebPage();
		page.setType(type);
		page.setCreatetime(new Date());
		page.setSource(SoccerConstants.SOURCE_OKOOO);		
		page.setPriority(getDefaultPriority(type, params));
		page.setEncoding(encoding);
		page.setMethod(getHttpMethod(type));
		page.setUrl(createURL(type, params));
		page.setParams(params);
		setHeaders(page);
		
		return page;
	}
	
	/**
	 * 获得页面的优先等级值
	 * @param type 页面类型
	 * @return 页面优先等级值
	 */
	protected static Double getDefaultPriority(String type, Map<String, String> params)
	{
		if(PAGE_PRIORITIES.containsKey(type))
		{
			double priority = PAGE_PRIORITIES.get(type);
			switch (type)
			{
			case PAGE_ODDS_OP:
			case PAGE_ODDS_YP:
				Date matchTime = DateUtil.tryToParseDate(params.get(SoccerConstants.NAME_FIELD_MATCHTIME));
				if(matchTime != null)
				{
					priority += -((double)(matchTime.getTime() - System.currentTimeMillis())) / 3600000.0;
				}
				break;
			}
			return priority;
		}
		else
			return 1.0;
	}
	
	/**
	 * 获得页面方法类型
	 * @param type 页面类型
	 * @return
	 */
	protected static String getHttpMethod(String type)
	{
		switch (type)
		{
		default:
			return HttpUtil.HTTP_METHOD_GET;
		}
	}
	
	/**
	 * 设置页面的头部信息
	 * @param type
	 * @param headers
	 */
	protected static void setHeaders(WebPage page)
	{
		Map<String, String> headers = null;
		String referer = null;
		
		switch (page.getType())
		{
		case PAGE_ODDS_OP:
		case PAGE_ODDS_YP:
			/*headers = page.getHeaders();
			referer = createURL(OkoooConstants.PAGE_SCORE_BD, page.getParams());
			headers.put("Host", host);
			headers.put("Referer", referer);
			page.setHeaders(headers);*/
			break;
		case PAGE_ODDS_OP_CHILD:
			headers = page.getHeaders();
			referer = createURL(OkoooConstants.PAGE_ODDS_OP, page.getParams());
			headers.put("Host", host);
			headers.put("Referer", referer);
			headers.put("X-Requested-With", "XMLHttpRequest");			
			break;
		case PAGE_ODDS_YP_CHILD:
			headers = page.getHeaders();
			referer = createURL(OkoooConstants.PAGE_ODDS_YP, page.getParams());
			headers.put("Host", host);
			headers.put("Referer", referer);
			headers.put("X-Requested-With", "XMLHttpRequest");
			break;
		/*case PAGE_LEAGUE_LEAGUE_ROUND:
			Map<String, String> headers = page.getHeaders();
			String baseUrl = PAGE_BASE_URLS.get(page.getType());
			String host = URLUtil.getHost(baseUrl);
			String referer = baseUrl + "/soccer/league/" + page.getParam(ZgzcwConstants.NAME_FIELD_SOURCE_LID)
				+ "/" + page.getParam(SoccerConstants.NAME_FIELD_SEASON);
			headers.put("Host", host);
			headers.put("Origin", baseUrl);
			headers.put("Referer", referer);
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			break;*/
		default:
			break;
		}
	}
	
	/**
	 * 创建页面访问地址
	 * @param type
	 * @param params
	 * @return
	 */
	protected static String createURL(String type, Map<String, String> params)
	{
		String baseURL = PAGE_BASE_URLS.get(type);
		String page = null;
		int pageIndex = 0;
		int perPage = 30;
		switch (type)
		{
		case PAGE_ODDS_OP:				//百家OP页面
			checkParams(params, SoccerConstants.NAME_FIELD_MID);
			baseURL += params.get(SoccerConstants.NAME_FIELD_MID) + "/odds/";
			break;
		case PAGE_ODDS_YP:
			checkParams(params, SoccerConstants.NAME_FIELD_MID);	//亚盘
			baseURL += params.get(SoccerConstants.NAME_FIELD_MID)  + "/ah/";
			break;
		case PAGE_ODDS_YP_CHILD:
			page = params.get(SoccerConstants.NAME_FIELD_PAGE);
			if(StringUtils.isNotEmpty(page))  pageIndex = NumberUtil.parseInt(page);
			if(pageIndex < 0) pageIndex = 0;
			baseURL += params.get(SoccerConstants.NAME_FIELD_MID)  + "/ah/ajax/?";
			baseURL += "page=" + pageIndex + "&trnum=" + (pageIndex * perPage) + "&companytype=BaijiaBooks";
			break;
		case PAGE_ODDS_OP_CHILD:
			page = params.get(SoccerConstants.NAME_FIELD_PAGE);
			if(StringUtils.isNotEmpty(page))  pageIndex = NumberUtil.parseInt(page);
			if(pageIndex < 0) pageIndex = 0;
			baseURL += params.get(SoccerConstants.NAME_FIELD_MID)  + "/odds/ajax/?";
			baseURL += "page=" + pageIndex + "&trnum=" + (pageIndex * perPage) + "&companytype=BaijiaBooks&type=0";
			break;
		default:
			baseURL = URLUtil.makeDefaultUrl(baseURL, params);
			break;
		}
		return baseURL;
	}

	/**
	 * 数据编码，一般同一网站的数据编码规则一致
	 * 
	 * @return 编码类型
	 */
	public static String getDefaultEncoding()
	{
		return encoding;
	}

	/**
	 * 设置编码类型
	 * 
	 * @param encoding
	 *            编码
	 */
	public static void setDefaultEncoding(String encoding1)
	{
		encoding = encoding1;
	}
	
	/**
	 * 设置页面类型的优先等级值
	 * @param type 类型
	 * @param priority 优先等级值
	 */
	public static void setDefaultPriority(String type, Double priority)
	{
		PAGE_PRIORITIES.put(type, priority);
	}
	
	/**
	 * 设置页面类型默认的网页地址
	 * @param type 类型
	 * @param url 基础地址
	 */
	public static void setDefaultBasicUrl(String type, String url)
	{
		PAGE_BASE_URLS.put(type, url);
	}
	
	/**
	 * 检测数据参数
	 * @param params 参数
	 * @param key 数据内容
	 */
	public static void checkParams(Map<String, String> params, String key)
	{
		if(params == null || StringUtils.isBlank(params.get(key)))
		{
			throw new IllegalArgumentException("The params is null or the key '" + key + "' value is not set.");
		}
	}
}
