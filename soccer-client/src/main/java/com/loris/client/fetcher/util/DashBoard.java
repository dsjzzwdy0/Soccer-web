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
package com.loris.client.fetcher.util;


import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**   
 * @ClassName:  League   
 * @Description: 主要用于记录下载网页过程中的下载时间 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */

public class DashBoard
{
	public static final int SIZE = 20;
	public static final Object watch = new String("w");

	public static SortedSet<UrlRecord> urlList = new TreeSet<UrlRecord>();

	/**
	 * Add a new URL and its time to the dashboard. It will only get into the
	 * dashboard if it is slow-enough to deserve, such an honor, of course :)
	 * 
	 * @param url
	 *            String
	 * @param time
	 *            int
	 */
	public static void add(String url, long time)
	{

		UrlRecord newSlowUrl = new UrlRecord(url, time);

		if (urlList.size() < DashBoard.SIZE)
		{
			urlList.add(newSlowUrl);
		}
		else
		{
			UrlRecord minElement = (UrlRecord) urlList.first();
			long minTime = minElement.processTime;
			if (time < minTime)
				return;

			synchronized (DashBoard.watch)
			{
				urlList.remove(minElement);
				urlList.add(newSlowUrl);
			}
		}
	}

	/**
	 * Return the string containing the list of record URLs
	 */
	public static String print()
	{
		String ret = "";

		synchronized (DashBoard.watch)
		{
			if (urlList != null)
			{
				Iterator<UrlRecord> it = DashBoard.urlList.iterator();
				while (it.hasNext())
				{
					UrlRecord u = (UrlRecord) it.next();
					String url = u.url;
					long time = u.processTime;
					ret += (" Time: " + time + " URL: " + url + "\r\n");
				}
			}
			else
			{
				ret += (" List of the slowest URLs is empty ");
			}
		}
		return ret;
	}
}

class UrlRecord implements Comparable<UrlRecord>
{
	public String url;
	public long processTime;

	public UrlRecord(String url, long processTime)
	{
		this.url = url;
		this.processTime = processTime;
	}

	public int compareTo(UrlRecord o) throws ClassCastException
	{
		long k = (int) this.processTime - ((UrlRecord) o).processTime;

		if (k > 0)
		{
			return 1;
		}
		else if (k < 0)
		{
			return -1;
		}
		else
			return 0;

	}

	public boolean equals(Object o)
	{
		return (processTime == ((UrlRecord) o).processTime);
	}
}
