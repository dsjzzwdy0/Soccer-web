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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.fetcher.util.DashBoard;
import com.loris.client.fetcher.util.HttpUtil;
import com.loris.client.page.WebPage;

/**
 * @ClassName: HtmlUnitFetcher
 * @Description: 使用HtmlUnit进行数据的下载
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class HtmlUnitFetcher extends AbstractWebFetcher
{
	private static Logger logger = Logger.getLogger(HtmlUnitFetcher.class);

	/** 基础起始页面 */
	private WebPage basePage;

	/** 使用基础页面 */
	private boolean hasInitPage;

	/** 等待后台JavaScript执行的时间 */
	int waitForBackgroundJavaScript = 10000;

	/** 这里采用单线程处理的方式 */
	WebClient client = null;

	/**
	 * Create a new instance of HtmlUnitFetcher.
	 */
	public HtmlUnitFetcher(FetcherSetting setting)
	{
		setFetcherSetting(setting);
	}

	/**
	 * 创建HtmlNunit下载器，使用基础页面
	 * 
	 * @param basePage
	 */
	public HtmlUnitFetcher(FetcherSetting setting, WebPage basePage)
	{
		this(setting);
		this.basePage = basePage;
		hasInitPage = true;
	}

	/**
	 * 抓取起始页面
	 * @throws IOException
	 */
	@Override
	public void init() throws IOException
	{
		createWebClient();
		if (!hasInitPage || basePage == null)
		{
			return;
		}
		try
		{
			// 这里需要有一个起始页面，原因是为了获取起始页面的Cookie值
			logger.info("Fetching Base Page: " + basePage.getUrl());
			fetch(basePage);			
			//logger.info("Content: " + basePage.getContent());
		}
		catch (Exception exception)
		{
			logger.info("Error when create Fetcher: " + exception.toString());
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.client.fetcher.impl.AbstractWebFetcher#download(com.loris.client.page.WebPage)
	 */
	@Override
	public boolean download(WebPage page) throws UrlFetchException, HostForbiddenException
	{
		return fetch(page);
	}
	
	/**
	 * 创建网络请求数据类
	 * @param page
	 * @return
	 * @throws MalformedURLException
	 */
	protected static WebRequest createWebRequest(WebPage page) throws MalformedURLException
	{
		WebRequest request = new WebRequest(new URL(page.getUrl()));		
		if(HttpUtil.HTTP_METHOD_POST.equalsIgnoreCase(page.getMethod()))
		{
			request.setHttpMethod(HttpMethod.POST);	
			Map<String, String> params = page.getParams();
			List<NameValuePair> requestParameters = new ArrayList<>();
			if (params != null && params.size() > 0)
			{
				for (Entry<String, String> param : params.entrySet())
				{
					requestParameters.add(new NameValuePair(param.getKey(), param.getValue()));
				}
			}
			request.setRequestParameters(requestParameters);
		}
		else
		{
			request.setHttpMethod(HttpMethod.GET);
		}
		return request;
	}

	/**
	 * 下载网络数据
	 * 
	 * @param page
	 * @param client
	 * @param time
	 * @return
	 * @throws IOException
	 */
	protected static Page excuteWebRequest(WebRequest request, WebClient client, int time) throws IOException
	{
		try
		{
			synchronized (client)
			{
				HtmlPage p = client.getPage(request);
				return p;
			}
		}
		catch (com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException e)
		{
			// e.printStackTrace();
			logger.info("Error occured '" + e.getStatusCode() + "' when get: " + request.getUrl()
					+ ", Retrieve the page again. Time is" + time);
			time++;
			if (time >= 3)
			{
				return null;
			}
			return excuteWebRequest(request, client, time);
		}
	}

	/**
	 * 下载一个数据页面
	 * 
	 * @param page
	 *            网络页
	 * @return 是否下载成功的标志
	 */
	public boolean fetch(WebPage page) throws HostForbiddenException, UrlFetchException
	{
		logger.debug("Http '" + page.getMethod() + "' URL: " + page.getUrl());
		if (page.getHeaders() != null && page.getHeaders().size() > 0)
		{
			addHeaders(page.getHeaders(), client);
		}
		try
		{
			WebRequest request = createWebRequest(page);
			
			long startTime = System.currentTimeMillis();			
			Page htmlPage = excuteWebRequest(request, client, 0);			
			//计算页面获取的时间
			long endTime = System.currentTimeMillis();
			DashBoard.add(page.getUrl(), endTime - startTime);
			
			int statusCode = htmlPage.getWebResponse().getStatusCode();			
			page.setHttpstatus(statusCode);
			
			//检测数据页面返回的状态值
			checkStatusCode(page, statusCode);

			page.setContent(((HtmlPage)htmlPage).asXml());			
			return true;

		}
		catch (IOException e)
		{
			logger.info("Error occured when loading " + page.getUrl() + ".");
			return false;
		}
		finally
		{
			// 删除请求头数据支持项
			if (page.getHeaders() != null && page.getHeaders().size() > 0)
			{
				removeHeaders(page.getHeaders());
			}
		}
	}

	/**
	 * 创建下载客户端
	 */
	protected void createWebClient()
	{
		client = new WebClient(BrowserVersion.CHROME);
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setCssEnabled(false);
		client.getCookieManager().setCookiesEnabled(true);
		client.setAjaxController(new NicelyResynchronizingAjaxController());
		client.getOptions().setThrowExceptionOnScriptError(false);            	// js运行错误时，是否抛出异常
		client.getOptions().setTimeout(setting.getConnectionTimeout()); 		// 设置连接超时时间// ，这里是10S。如果为0，则无限期等;
		client.waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	}

	/**
	 * 加入数据头部请求参数
	 * 
	 * @param headers
	 * @param client
	 */
	protected static void addHeaders(Map<String, String> headers, WebClient client)
	{
		// 添加网络请求头数据支持项
		if (headers != null)
		{
			if (headers != null)
			{
				for (String key : headers.keySet())
				{
					String value = headers.get(key);
					client.addRequestHeader(key, value);
				}
			}
		}
	}

	/**
	 * 删除已经添加的头请求数据
	 * 
	 * @param headers
	 */
	protected void removeHeaders(Map<String, String> headers)
	{
		if (headers == null)
		{
			return;
		}
		for (String key : headers.keySet())
		{
			client.removeRequestHeader(key);
		}
	}

	/**
	 * 设置等待javascript的执行时间
	 * 
	 * @param millseconds
	 *            毫秒数
	 */
	public void waitForBackgroundJavaScript(int millseconds)
	{
		if (client != null)
			client.waitForBackgroundJavaScript(millseconds);
	}

	public int getWaitForBackgroundJavaScript()
	{
		return waitForBackgroundJavaScript;
	}

	public void setWaitForBackgroundJavaScript(int waitForBackgroundJavaScript)
	{
		this.waitForBackgroundJavaScript = waitForBackgroundJavaScript;
		waitForBackgroundJavaScript(waitForBackgroundJavaScript);
	}
	
	/**
	 * 关闭数据下载客户端
	 * 
	 */
	@Override
	public void close()
	{
		if(client != null)
		{
			client.close();
		}
	}
}
