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
package com.loris.client.fetcher.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.model.WebPage;

/**   
 * @ClassName:  FetcherSetting   
 * @Description: 数据下载器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractWebFetcher implements WebFetcher
{
	private static Logger logger = Logger.getLogger(AbstractWebFetcher.class);
	
	/** FetcherSetting 设置信息*/
	protected FetcherSetting setting;

	/** 
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{		
	}

	/** 
	 * (non-Javadoc)
	 * @see com.loris.client.fetcher.WebFetcher#init()
	 */
	@Override
	public void init() throws IOException
	{
		logger.info("Initializing the WebFetcher...");
	}

	/** 
	 * (non-Javadoc)
	 * @see com.loris.client.fetcher.WebFetcher#setFetcherSetting(com.loris.client.fetcher.setting.FetcherSetting)
	 */
	@Override
	public void setFetcherSetting(FetcherSetting setting)
	{
		this.setting = setting;		
	}

	/** 
	 * (non-Javadoc)
	 * @see com.loris.client.fetcher.WebFetcher#download(com.loris.client.model.WebPage)
	 */
	@Override
	public abstract boolean download(WebPage page) throws IOException, UrlFetchException, HostForbiddenException;
	
	/**
	 * 检查页面返回的代码数据
	 * @param page
	 * @param statusCode
	 * @throws UrlFetchException
	 * @throws HostForbiddenException
	 */
	protected static void checkStatusCode(WebPage page, int statusCode) throws UrlFetchException, HostForbiddenException
	{
		if(statusCode == HttpStatus.SC_OK)
		{
			//do nothing.
			page.setCompleted(true);
			page.setLoadtime(new Date());
		}
		else if (statusCode == HttpStatus.SC_FORBIDDEN)
		{
			throw new HostForbiddenException(page.getUrl());
		}
		else
		{
			throw new UrlFetchException("Error code is '" + statusCode + "', Failed to fetch page: " + page.getUrl());
		}
	}
}
