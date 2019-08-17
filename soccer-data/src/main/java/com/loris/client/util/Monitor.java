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
package com.loris.client.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**   
 * @ClassName:  League   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class Monitor
{
	/**
	 * Number of processed URLs. The modulus of this is, actually, used
	 * separated by number of threads, to determine which thread should
	 * process the current url.
	 */
	private static volatile long counter = 0;
	
	/** The start time.*/
	private static long startTime;

	/** The flag to stop crawler. */
	public static boolean stopCrawler = false;

	/** The fetcher counter. */
	public static volatile long fetchedCounter = 0;

	/** The num of active thread. */
	//public static volatile long numOfActiveThreads = 0;
	
	/** The num of main scheduler. */
	public static volatile long numOfMainSchedulerThread = 0;
		
	/** Date format. */
	private static DateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
	
	public static final Object watch = new Integer(0);
	public static final Object cookieWatch = new Integer(0);
	/**
	 * Ok, why do we need this property? To speed things up.
	 * The story is that we do not want a contains() method
	 * to be called on getUrls() while somebody is putting
	 * something in that Set. if we use synchronize(), however,
	 * to contains() calls will, also, wait for each-other
	 * which is absolutely unnecessary and as the size of the
	 * urls set grows - becomes a serious performance bottleneck.
	 */
	public static boolean checking = false;
	
	/** The settings value. */
	//private static Settings settings = null;
	
	/**
	 * Returns current time in a human-readable way with the precision of
	 * milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTime()
	{		
		Date date = new Date(System.currentTimeMillis());

		return sdf.format(date);
	}
	
	
	/**
	 * Get the Counter value.
	 * @return
	 */
	public static long getCounter()
	{
		return Monitor.counter;
	}

	/**
	 * Increase the counter - number of processed URLs
	 */
	public static void incCounter()
	{
		Monitor.counter++;
	}

	public static long getStartTime()
	{
		return Monitor.startTime;
	}
	
	public static void setStartTime(long startTime)
	{
		Monitor.startTime = startTime;
	}

}
