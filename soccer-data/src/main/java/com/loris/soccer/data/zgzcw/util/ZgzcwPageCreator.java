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
package com.loris.soccer.data.zgzcw.util;

import static com.loris.soccer.data.zgzcw.constant.ZgzcwConstants.*;

import java.util.Date;
import java.util.Map;

import com.loris.client.fetcher.util.HttpUtil;
import com.loris.client.page.WebPage;
import com.loris.common.util.EncodingUtil;
import com.loris.common.util.URLBuilder;

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

	/** 页面的类型与基础网址的对应关系表. */
	public static final String[][] PAGE_TYPES =
	{
		{PAGE_CENTER, 				"http://saishi.zgzcw.com/soccer/"}, 	// 数据主页面
		{PAGE_LEAGUE_LEAGUE, 		"http://saishi.zgzcw.com/summary/liansaiAjax.action" }, // ?source_league_id=8&currentRound=3&season=2017-2018&seasonType=";//联赛类型的数据
		{PAGE_LEAGUE_CUP,    		"http://saishi.zgzcw.com/soccer/"},  	// "cup/51/2017-2018/" 杯赛类型的数据
		{PAGE_LOTTERY_BD,        	"http://cp.zgzcw.com/lottery/bdplayvsforJsp.action?lotteryId=200"}, // &issue=80401 // 北单足彩
		{PAGE_LOTTERY_JC,        	"http://cp.zgzcw.com/lottery/jchtplayvsForJsp.action?lotteryId=47&type=jcmini"}, // &issue=2018-03-25 // 竞彩足球
		{PAGE_LOTTERY_ZC,       	"http://cp.zgzcw.com/lottery/zcplayvs.action?lotteryId=13"}, // &issue=300&v=2018-02-22" 足彩足球
		{PAGE_ODDS_OP,      		"http://fenxi.zgzcw.com/"}, 			// 2249815/bjop /mid/bjop
		{PAGE_ODDS_YP,      		"http://fenxi.zgzcw.com/"}, 			// 2249815/ypdb /mid/ypdb
		{"season",                  "http://saishi.zgzcw.com/soccer/"}, 	// "cup/51/2017-2018/" 杯赛类型的数据
		{"rank",                    "http://saishi.zgzcw.com/soccer/league/"}, // lid 联赛球队排名数据获取
		{"team",                    "http://saishi.zgzcw.com/soccer/team/"}, // tid 球队主页信息
		{"calendar",                "http://cp.zgzcw.com/lottery/queryCPRL.action"}, // 足球数据竞彩日历页面 //?date=2018-01-01&length=90
		{"opzhishu",                "http://fenxi.zgzcw.com/"}, 			// 2373979/bjop/zhishu?company_id=115&company=%E5%A8%81%E5%BB%89%E5%B8%8C%E5%B0%94
		{"ypzhishu",                "http://fenxi.zgzcw.com/"}, 			// 2373979/ypdb/zhishu?company_id=115&company=%E5%A8%81%E5%BB%89%E5%B8%8C%E5%B0%94
		{"history",                 "http://fenxi.zgzcw.com/"}, 			// 2282960/bsls	
		{"leaguem",                 "http://saishi.zgzcw.com/soccer/"}, 	// 数据主页面
		{"live",  					"http://live.zgzcw.com/"}				// 实时数据页面 //ls/AllData.action
	};
	
	/**
	 * 创建数据下载页面
	 * @param type 类型
	 * @param params 参数
	 * @return 数据页面
	 * @throws IllegalArgumentException 如果type没有定义或参数错误，则抛出异常值
	 */
	public static WebPage createZgzcwWebPage(String type) throws IllegalArgumentException
	{
		return createZgzcwWebPage(getPageTypeIndex(type), null);
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
		return createZgzcwWebPage(getPageTypeIndex(type), params);
	}
	
	/**
	 * 创建数据下载页面
	 * @param type 类型
	 * @param params 参数
	 * @return 数据页面
	 * @throws IllegalArgumentException 如果type没有定义或参数错误，则抛出异常值
	 */
	public static WebPage createZgzcwWebPage(String type, String method, Map<String, String> params) throws IllegalArgumentException
	{
		return createZgzcwWebPage(getPageTypeIndex(type), method, params);
	}
	

	/**
	 * 基本信息，共同具有的特征
	 * 
	 * @param page
	 */
	public static WebPage createZgzcwWebPage(int typeIndex, Map<String, String> params) throws IllegalArgumentException
	{
		return createZgzcwWebPage(typeIndex, HttpUtil.HTTP_METHOD_GET, params);
	}
	
	/**
	 * 基本信息，共同具有的特征
	 * 
	 * @param page
	 */
	public static WebPage createZgzcwWebPage(int typeIndex, String method, Map<String, String> params) throws IllegalArgumentException
	{
		if(typeIndex >= PAGE_TYPES.length)
		{
			throw new IllegalArgumentException("The type index '" + typeIndex + "' is overflowed the Page types defined in this PageCreator.");
		}
		WebPage page = new WebPage();
		page.setEncoding(encoding);
		page.setType(PAGE_TYPES[typeIndex][0]);
		page.setCreatetime(new Date());
		page.setSource(SOURCE_ZGZCW);
		page.setMethod(method);
		if (params != null)
		{
			page.setParams(params);
		}

		String basicUrl = PAGE_TYPES[typeIndex][1];
		String type = PAGE_TYPES[typeIndex][0];
		switch (type)
		{
		case PAGE_ODDS_OP:				//百家OP页面
			basicUrl += params.get("mid") + "/bjop";
			break;
		default:
			basicUrl = URLBuilder.makeDefaultUrl(basicUrl, params);
			break;
		}
		page.setUrl(basicUrl);
		return page;
	}
	

	/**
	 * 获得页面的类型
	 * 
	 * @param type
	 *            页面类型
	 * @return 序号
	 * @throws IllegalArgumentException
	 *             错误的参数
	 */
	protected static int getPageTypeIndex(String type) throws IllegalArgumentException
	{
		for (int i = 0; i < PAGE_TYPES.length; i++)
		{
			if (PAGE_TYPES[i][0].equalsIgnoreCase(type))
			{
				return i;
			}
		}
		throw new IllegalArgumentException("There are no type of '" + type + "' defined in this PageCreator.");
	}

	/**
	 * 数据编码，一般同一网站的数据编码规则一致
	 * 
	 * @return 编码类型
	 */
	public static String getEncoding()
	{
		return encoding;
	}

	/**
	 * 设置编码类型
	 * 
	 * @param encoding
	 *            编码
	 */
	public static void setEncoding(String encoding1)
	{
		encoding = encoding1;
	}
}
