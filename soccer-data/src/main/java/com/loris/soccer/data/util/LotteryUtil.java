/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  LotteryUtil.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.util;

import java.util.Calendar;
import java.util.Date;

import com.loris.common.util.DateUtil;

/**
 * @ClassName: LotteryUtil
 * @Description: 竞彩比赛数据的应用类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LotteryUtil
{
	/**
	 * 静态使用类
	 */
	private LotteryUtil()
	{
	}

	/**
	 * 获得比赛的期号：在11：30之前，则为前一天的比赛，在11：30之后的比赛，为当日的比赛
	 * 
	 * @param matchTime
	 *            比赛日期
	 * @return 比赛期号
	 */
	public static String getLotteryIssue(Date matchTime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(matchTime);
		if (calendar.get(Calendar.HOUR_OF_DAY) < 12)
		{
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			return DateUtil.formatDay(calendar.getTime());
		}
		else
		{
			return DateUtil.formatDay(calendar.getTime());
		}
	}

	/**
	 * 解析投注截止时间
	 * 
	 * @param matchTime
	 *            比赛时间
	 * @param closeTimeStr
	 *            关闭时间
	 * @return 截止时间
	 */
	public static Date parseCloseTime(Date matchTime, String closeTimeStr)
	{
		Date closeTime = DateUtil.parse(closeTimeStr, "hh:mm:ss");
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(closeTime);
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		int m = calendar.get(Calendar.MINUTE);

		calendar.setTime(matchTime);
		int mh = calendar.get(Calendar.HOUR_OF_DAY);

		calendar.set(Calendar.HOUR_OF_DAY, h);
		calendar.set(Calendar.MINUTE, m);
		if (h > mh)
		{
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		return calendar.getTime();
	}
}
