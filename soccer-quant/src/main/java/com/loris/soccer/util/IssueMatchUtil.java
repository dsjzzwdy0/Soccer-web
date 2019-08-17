/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  IssueMatchUtil.java   
 * @Package com.loris.soccer.util   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.util;

import java.util.Calendar;
import java.util.Date;

import com.loris.common.util.DateUtil;

/**   
 * @ClassName:  IssueMatchUtil    
 * @Description: 博彩期号工具  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class IssueMatchUtil
{
	/**
	 * 获得当前比赛时间期号
	 * @return 比赛期号
	 */
	public static String getCurrentIssue()
	{
		return getIssueDay(new Date());
	}
	
	/**
	 * 获得比赛的投注日期
	 * @param matchtime
	 * @return
	 */
	public static String getIssueDay(String matchtime)
	{
		Date date = DateUtil.tryToParseDate(matchtime);
		if(date == null)
		{
			return "";
		}
		return getIssueDay(date);
	}
	
	public static String getIssueDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		
		if(hour < 11 || (hour == 11) && (minute < 30))
		{
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date d = calendar.getTime();
			return DateUtil.DAY_FORMAT.format(d);
		}
		else
		{
			return DateUtil.DAY_FORMAT.format(calendar.getTime());
		}
	}
	
	/**
	 * 获得竞彩比赛的结束时间
	 * @param issus 比赛的期号
	 * @return 结束时间
	 */
	public static String getEndTime(String issus)
	{
		Date date = DateUtil.tryToParseDate(issus);
		date = DateUtil.add(date, Calendar.DAY_OF_MONTH, 1);
		String string = DateUtil.formatDay(date) + " 11:30";
		return string;
	}
	
	/**
	 * 竞彩比赛数据的开始时间
	 * @param issue 竞彩比赛的编号
	 * @return 开始时间
	 */
	public static String getStartTime(String issue)
	{
		return issue + " 9:30";
	}
}
