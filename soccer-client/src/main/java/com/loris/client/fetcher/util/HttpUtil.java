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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

/**   
 * @ClassName:  League   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class HttpUtil
{
	protected static Logger log = Logger.getLogger(HttpUtil.class);

	// 200 - ok
	public final static int OK_200 = 200;

	// 300 - redirect
	public final static int PARTIAL_CONTENT_206 = 206;
	public final static int MOVED_PERMANENTLY_301 = 301;
	public final static int FOUND_302 = 302;
	public final static int SEE_OTHER_303 = 303;
	public final static int TEMPORARY_REDIRECT_307 = 307;

	// 400 - client error
	public final static int NOT_FOUND_404 = 404;
	public final static int REQUESTED_RANGE_NOT_SATISFIABLE_416 = 416;
	
	
	/**
	 * 截取基础数据页面URI地址
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public static String getBaseUriFromUrl(String url) throws MalformedURLException
	{
		URL javaURL = new URL(url);
		String path = javaURL.getPath();
		int index = path.lastIndexOf("/");
		if (index == -1)
		{
			return "";
		}
		else
		{
			return path.substring(0, index);
		}

	}

	/**
	 * URLs, in anchors, can come in three flavours:
	 * <li>Canonical (begining with "http://")
	 * <li>Absolute, non-canonical (begining with "/")
	 * <li>Relative (not begining with either "http" or "/")
	 * 
	 * @param domain
	 * @param baseUrl
	 * @param link
	 * @return
	 */
	public static String canonizeURL(String domain, String baseUrl, String link)
	{
		link = link.trim();
		String ret = "";

		if (link.startsWith("javascript") || link.startsWith("mailto:"))
		{
			ret = ""; // Illegal URL
		}
		else if (link.startsWith("http"))
		{
			ret = link;
		}
		else if (link.startsWith("www."))
		{
			ret = "http://" + link;
		}
		else if (link.startsWith("/"))
		{
			int indx = 0;
			if (domain.endsWith("/"))
			{
				indx = 1;
			}
			ret = domain.substring(indx) + link;
		}
		else
		{
			String slash2 = "/";

			if (!domain.endsWith("/"))
				domain = domain + "/";
			if (baseUrl.startsWith("/"))
				baseUrl = baseUrl.substring(1);
			if (link.startsWith("/"))
				link = link.substring(1);
			if (baseUrl.equals(""))
			{
				slash2 = "";
			}
			if (baseUrl.endsWith("/"))
			{
				slash2 = "";
			}
			if (link.equals(""))
			{
				slash2 = "";
			}
			ret = domain + baseUrl + slash2 + link;
		}
		return ret;
	}

	public static String getDomainFromUrl(String url) throws MalformedURLException
	{
		URL javaURL = new URL(url);
		return javaURL.getProtocol() + "://" + javaURL.getHost()
				+ (javaURL.getPort() != -1 ? ":" + javaURL.getPort() : "");
	}
}
