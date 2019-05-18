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
package com.loris.client.task.util;

import com.loris.common.util.RandomUtil;

/**   
 * @ClassName:  ThreadUtil  
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ThreadUtil
{	
	/**
	 * 处理线程闲置时间
	 * @param millis
	 */
	public static void sleep(long millis)
	{
		if(millis <= 0)
		{
			return;
		}
		try
		{
			Thread.sleep(millis);
		}
		catch(Exception e)
		{
		}
	}
	
	/**
	 * 处理线程闲置时间
	 * @param millis
	 */
	public static void sleep(long millis, int randTimeSeed)
	{
		if(millis <= 0)
		{
			return;
		}
		millis += randTimeSeed > 0 ? RandomUtil.getRandom(randTimeSeed) : 0;		
		try
		{
			Thread.sleep(millis);
		}
		catch(Exception e)
		{
		}
	}
}
