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
package com.loris.client.fetcher.setting;

import java.util.HashMap;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**   
 * @ClassName:  FetcherSetting   
 * @Description: 下载器的基本配置信息  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class FetcherSetting
{
	private int interval = 4000;
	private int monitorInterval;
	private int connectionTimeout = 30000;
	
	private BrowserVersion browser = BrowserVersion.FIREFOX_60;
	
	private Map<String, String> headers;
	
	/**
	 * Create a new intance of FetcherSetting.
	 */
	public FetcherSetting()
	{
		this.headers = new HashMap<String, String>();
	}
	
	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public int getMonitorInterval()
	{
		return monitorInterval;
	}

	public void setMonitorInterval(int monitorInterval)
	{
		this.monitorInterval = monitorInterval;
	}

	public int getConnectionTimeout()
	{
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout)
	{
		this.connectionTimeout = connectionTimeout;
	}

	public Map<String, String> getHeaders()
	{
		return headers;
	}

	public void setHeaders(Map<String, String> headers)
	{
		this.headers = headers;
	}

	public BrowserVersion getBrowserVersion()
	{
		return browser;
	}

	public void setBrowserVersion(BrowserVersion browser)
	{
		this.browser = browser;
	}
	
	public void setBrowser(String browser)
	{
		switch (browser)
		{
		case "FIREFOX_60":
			this.browser = BrowserVersion.FIREFOX_60;
			break;
		case "FIREFOX_52":
			this.browser = BrowserVersion.FIREFOX_52;
			break;
		case "INTERNET_EXPLORER":
			this.browser = BrowserVersion.INTERNET_EXPLORER;
			break;
		case "CHROME":
			this.browser = BrowserVersion.CHROME;
			break;
		case "EDGE":
			this.browser = BrowserVersion.EDGE;
			break;
		default:
			break;
		}
	}
}
