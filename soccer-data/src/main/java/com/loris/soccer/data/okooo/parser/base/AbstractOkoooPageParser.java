/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @AbstractOkoooPageParser.java   
 * @Package com.loris.soccer.data.okooo.parser.basecom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.okooo.parser.base;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.loris.client.exception.WebParserException;
import com.loris.client.model.WebPage;
import com.loris.client.parser.impl.AbstractWebPageParser;
import com.loris.common.util.DateUtil;
import com.loris.soccer.constant.SoccerConstants;

/**   
 * @ClassName:  AbstractOkoooPageParser.java   
 * @Description: 澳客网数据下载解析器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public abstract class AbstractOkoooPageParser extends AbstractWebPageParser
{
	/** 接受的类型 */
	protected String acceptType = null;
	
	/**
	 * Create a new instance of AbstractZgzcwWebPageParser
	 * @param acceptType
	 */
	public AbstractOkoooPageParser(String acceptType)
	{
		this.acceptType = acceptType;
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
		
		if(!SoccerConstants.SOURCE_OKOOO.equalsIgnoreCase(page.getSource()))
		{
			return false;
		}
		
		if(StringUtils.isBlank(acceptType) || !acceptType.equalsIgnoreCase(page.getType()))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 获得截止时间
	 * 
	 * @param matchtime 比赛时间
	 * @param closetime 截止小时
	 * @return 关闭的时间
	 */
	protected Date getCloseTime(Date matchtime, String closetime)
	{
		closetime = DateUtil.getYear() + "-" + closetime;
		return DateUtil.tryToParseDate(closetime);
	}
	
	/**
	 * Get the Lid value.
	 * 
	 * @param url
	 * @return
	 */
	protected String getLeadueId(String url)
	{
		String[] values = url.split(RIGHT_SPLASH);
		int size = values.length;
		String tid = values[size - 1];
		return tid;
	}

	/**
	 * 获得比赛时间
	 * 
	 * @param matchtime
	 * @return
	 */
	protected Date getMatchTime(String matchtime)
	{
		String str = matchtime.replace("比赛时间：", "");
		str = str.replace("比赛时间:", "");
		return DateUtil.tryToParseDate(str);
	}

	/**
	 * 
	 * @param matchtrends
	 * @return
	 */
	protected String getMatchId(String matchtrends)
	{
		String[] values = matchtrends.split(RIGHT_SPLASH);
		int size = values.length;
		String mid = values[size - 2];
		return mid;
	}
	
	/**
	 * 解析得到JSON字符串
	 * @param text 输入的字符串
	 * @return 正确的结果
	 */
	public static String getDataStr(String content, String splitStr, String field)
	{
		String[] strings = content.split(splitStr);
		for (String string : strings)
		{
			if(StringUtils.isNotEmpty(string))
			{
				string = string.trim();
				if(string.startsWith(field))
				{
					string = string.replace(field, "");
					string = string.replaceAll("=", "");
					string = string.replace("'", "");
					return string;
				}
			}
		}				
		return "";
	}
}
