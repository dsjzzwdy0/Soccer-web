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
package com.loris.soccer.plugin.zgzcw.util;

import static com.loris.soccer.plugin.zgzcw.util.ZgzcwConstants.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.loris.client.fetcher.util.HttpUtil;
import com.loris.client.model.WebPage;
import com.loris.common.util.EncodingUtil;
import com.loris.common.util.URLBuilder;
import com.loris.soccer.constant.SoccerConstants;

/**
 * @ClassName: League
 * @Description: 建立中国足彩网的数据下载页面
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ZgzcwPageCreator
{	
	/** 通用的页面编码 */
	protected static String encoding = EncodingUtil.ENCODING_UTF8;

	/** 页面的类型与基础网址的对应关系表. 
	public static final String[][] PAGE_TYPES =
	{
		{PAGE_CENTER, 				"http://saishi.zgzcw.com/soccer/"}, 	// 数据主页面
		{PAGE_LEAGUE_LEAGUE,    	"http://saishi.zgzcw.com/soccer/league/"},  // "cup/51/2017-2018/" 杯赛类型的数据
		{PAGE_LEAGUE_CUP,    		"http://saishi.zgzcw.com/soccer/cup/"},  	// "cup/51/2017-2018/" 杯赛类型的数据
		{PAGE_LEAGUE_LEAGUE_ROUND, 	"http://saishi.zgzcw.com/summary/liansaiAjax.action" }, // ?source_league_id=8&currentRound=3&season=2017-2018&seasonType=";//联赛类型的数据
		{PAGE_LOTTERY_BD,        	"http://cp.zgzcw.com/lottery/bdplayvsforJsp.action?lotteryId=200"}, // &issue=80401 // 北单足彩
		{PAGE_LOTTERY_JC,        	"http://cp.zgzcw.com/lottery/jchtplayvsForJsp.action?lotteryId=47&type=jcmini"}, // &issue=2018-03-25 // 竞彩足球
		{PAGE_LOTTERY_ZC,       	"http://cp.zgzcw.com/lottery/zcplayvs.action?lotteryId=13"}, // &issue=300&v=2018-02-22" 足彩足球
		{PAGE_SCORE_BD, 			"http://cp.zgzcw.com/lottery/bdplayvsforJsp.action?lotteryId=250"},    //&issue=
		{PAGE_SCORE_JC, 			"http://cp.zgzcw.com/lottery/jcplayvsForJsp.action?lotteryId=23"},
		{PAGE_ODDS_OP,      		"http://fenxi.zgzcw.com/"}, 			// 2249815/bjop /mid/bjop
		{PAGE_ODDS_YP,      		"http://fenxi.zgzcw.com/"}, 			// 2249815/ypdb /mid/ypdb
		{PAGE_ODDS_NUM,      		"http://fenxi.zgzcw.com/"}, 			// 2249815/ypdb /mid/dxdb
		{"season",                  "http://saishi.zgzcw.com/soccer/"}, 	// "cup/51/2017-2018/" 杯赛类型的数据
		{"rank",                    "http://saishi.zgzcw.com/soccer/league/"}, // lid 联赛球队排名数据获取
		{"team",                    "http://saishi.zgzcw.com/soccer/team/"}, // tid 球队主页信息
		{"calendar",                "http://cp.zgzcw.com/lottery/queryCPRL.action"}, // 足球数据竞彩日历页面 //?date=2018-01-01&length=90
		{"opzhishu",                "http://fenxi.zgzcw.com/"}, 			// 2373979/bjop/zhishu?company_id=115&company=%E5%A8%81%E5%BB%89%E5%B8%8C%E5%B0%94
		{"ypzhishu",                "http://fenxi.zgzcw.com/"}, 			// 2373979/ypdb/zhishu?company_id=115&company=%E5%A8%81%E5%BB%89%E5%B8%8C%E5%B0%94
		{"history",                 "http://fenxi.zgzcw.com/"}, 			// 2282960/bsls	
		{"leaguem",                 "http://saishi.zgzcw.com/soccer/"}, 	// 数据主页面
		{"live",  					"http://live.zgzcw.com/"}				// 实时数据页面 //ls/AllData.action
	};*/
	
	/** 数据类型与基础页面的对应工具 */
	public static Map<String, Double> PAGE_PRIORITIES = new HashMap<>();
	public static Map<String, String> PAGE_BASE_URLS = new HashMap<>();
	
	static
	{
		PAGE_PRIORITIES.put(PAGE_CENTER, 100.0);
		PAGE_PRIORITIES.put(PAGE_LEAGUE_LEAGUE, 20.0);
		PAGE_PRIORITIES.put(PAGE_LEAGUE_CUP, 20.0);
		PAGE_PRIORITIES.put(PAGE_LEAGUE_LEAGUE_ROUND, 19.0);
		PAGE_PRIORITIES.put(PAGE_LOTTERY_BD, 10.0);
		PAGE_PRIORITIES.put(PAGE_LOTTERY_JC, 10.0);
		PAGE_PRIORITIES.put(PAGE_LOTTERY_ZC, 10.0);
		PAGE_PRIORITIES.put(PAGE_SCORE_BD, 10.0);
		PAGE_PRIORITIES.put(PAGE_SCORE_JC, 10.0);
		PAGE_PRIORITIES.put(PAGE_ODDS_OP, 1.0);
		PAGE_PRIORITIES.put(PAGE_ODDS_YP, 1.0);
		PAGE_PRIORITIES.put(PAGE_ODDS_YP, 1.0);		
		
		PAGE_BASE_URLS.put(PAGE_CENTER, 			"http://saishi.zgzcw.com/soccer/");	// 数据主页面
		PAGE_BASE_URLS.put(PAGE_LEAGUE_LEAGUE,    	"http://saishi.zgzcw.com/soccer/league/");  // "cup/51/2017-2018/" 杯赛类型的数据
		PAGE_BASE_URLS.put(PAGE_LEAGUE_CUP,    		"http://saishi.zgzcw.com/soccer/cup/");  	// "cup/51/2017-2018/" 杯赛类型的数据
		PAGE_BASE_URLS.put(PAGE_LEAGUE_LEAGUE_ROUND, 	"http://saishi.zgzcw.com/summary/liansaiAjax.action" ); // ?source_league_id=8&currentRound=3&season=2017-2018&seasonType=";//联赛类型的数据
		PAGE_BASE_URLS.put(PAGE_LOTTERY_BD,        	"http://cp.zgzcw.com/lottery/bdplayvsforJsp.action?lotteryId=200"); // &issue=80401 // 北单足彩
		PAGE_BASE_URLS.put(PAGE_LOTTERY_JC,        	"http://cp.zgzcw.com/lottery/jchtplayvsForJsp.action?lotteryId=47&type=jcmini"); // &issue=2018-03-25 // 竞彩足球
		PAGE_BASE_URLS.put(PAGE_LOTTERY_ZC,       	"http://cp.zgzcw.com/lottery/zcplayvs.action?lotteryId=13"); // &issue=300&v=2018-02-22" 足彩足球
		PAGE_BASE_URLS.put(PAGE_SCORE_BD, 			"http://cp.zgzcw.com/lottery/bdplayvsforJsp.action?lotteryId=250");    //&issue=
		PAGE_BASE_URLS.put(PAGE_SCORE_JC, 			"http://cp.zgzcw.com/lottery/jcplayvsForJsp.action?lotteryId=23");
		PAGE_BASE_URLS.put(PAGE_ODDS_OP,      		"http://fenxi.zgzcw.com/"); 			// 2249815/bjop /mid/bjop
		PAGE_BASE_URLS.put(PAGE_ODDS_YP,      		"http://fenxi.zgzcw.com/"); 			// 2249815/ypdb /mid/ypdb
		PAGE_BASE_URLS.put(PAGE_ODDS_NUM,      		"http://fenxi.zgzcw.com/"); 
	}
	
	/**
	 * 创建数据下载页面
	 * @param type 类型
	 * @param params 参数
	 * @return 数据页面
	 * @throws IllegalArgumentException 如果type没有定义或参数错误，则抛出异常值
	 */
	public static WebPage createZgzcwWebPage(String type) throws IllegalArgumentException
	{
		return createZgzcwWebPage(type, null);
	}
	
	/**
	 * 创建数据下载页面
	 * @param type 类型
	 * @param params 参数
	 * @return 数据页面
	 * @throws IllegalArgumentException 如果type没有定义或参数错误，则抛出异常值
	 */
	public static WebPage createZgzcwWebPage(String type, Map<String, String> params) throws IllegalArgumentException
	{
		return createZgzcwWebPage(type, HttpUtil.HTTP_METHOD_GET, encoding, params);
	}
	
	/**
	 * 创建数据下载页面
	 * @param type 类型
	 * @param params 参数
	 * @return 数据页面
	 * @throws IllegalArgumentException 如果type没有定义或参数错误，则抛出异常值
	 */
	public static WebPage createZgzcwWebPage(String type, String method, String encoding, Map<String, String> params) throws IllegalArgumentException
	{
		if(!PAGE_BASE_URLS.containsKey(type))
		{
			throw new IllegalArgumentException("The type '" + type + "' is not defined in this PageCreator.");
		}
		
		WebPage page = new WebPage();
		page.setEncoding(encoding);
		page.setType(type);
		page.setCreatetime(new Date());
		page.setSource(SOURCE_ZGZCW);
		page.setMethod(method);
		page.setUrl(createURL(type, params));
		page.setPriority(getDefaultPriority(type));
		if (params != null)
		{
			page.setParams(params);
		}
		return page;
	}
	
	/**
	 * 获得页面的优先等级值
	 * @param type 页面类型
	 * @return 页面优先等级值
	 */
	protected static Double getDefaultPriority(String type)
	{
		if(PAGE_PRIORITIES.containsKey(type))
		{
			return PAGE_PRIORITIES.get(type);
		}
		else
			return 1.0;
	}
	
	/**
	 * 创建页面访问地址
	 * @param type
	 * @param params
	 * @return
	 */
	protected static String createURL(String type, Map<String, String> params)
	{
		String basicUrl = PAGE_BASE_URLS.get(type);
		switch (type)
		{
		case PAGE_LEAGUE_LEAGUE:
		case PAGE_LEAGUE_CUP:
			basicUrl += params.get(SoccerConstants.NAME_FIELD_LID);
			break;
		case PAGE_ODDS_OP:				//百家OP页面
			basicUrl += params.get(SoccerConstants.NAME_FIELD_MID) + "/bjop";
			break;
		case PAGE_ODDS_YP:				//百家OP页面
			basicUrl += params.get(SoccerConstants.NAME_FIELD_MID)  + "/ybdb";
			break;
		case PAGE_ODDS_NUM:
			basicUrl += params.get(SoccerConstants.NAME_FIELD_MID) + "/dxdb";
			break;
		default:
			basicUrl = URLBuilder.makeDefaultUrl(basicUrl, params);
			break;
		}
		return basicUrl;
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
}
