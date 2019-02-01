/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  HttpCommonFetcher.java   
 * @Package com.loris.client.fetcher.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月31日 下午8:31:06   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.fetcher.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.log4j.Logger;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.fetcher.util.CookieManager;
import com.loris.client.fetcher.util.DashBoard;
import com.loris.client.fetcher.util.HttpUtil;
import com.loris.client.page.WebPage;
import com.loris.client.util.Monitor;

/**
 * @ClassName: HttpCommonFetcher
 * @Description: 这是通过HttpClient包下载网页数据的类
 * @author: 东方足彩
 * @date: 2019年1月31日 下午8:31:06
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class HttpCommonFetcher extends AbstractWebFetcher
{
	private static Logger logger = Logger.getLogger(HttpCommonFetcher.class);

	// -- Reusable conneciton manager.
	private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

	
	/**
	 * Create a new instance of HttpCommonFetcher. 
	 * @param setting 数据配置信息
	 */
	public HttpCommonFetcher(FetcherSetting setting)
	{
		setFetcherSetting(setting);
	}
	
	/**
	 * 数据页面下载
	 * 
	 * @param page
	 *            数据页面
	 * @return 返回是否下载成功的标志
	 * @see com.loris.client.fetcher.WebFetcher#download(com.loris.client.page.WebPage)
	 */
	@Override
	public boolean download(WebPage page) throws UrlFetchException, IOException, HostForbiddenException
	{
		return fetch(page, setting);
	}

	/**
	 * Fetch the page data from web.
	 * 
	 * @param page
	 * @return
	 * @throws UrlFetchException
	 */
	public boolean fetch(WebPage page, FetcherSetting setting) throws IOException, UrlFetchException, HostForbiddenException
	{
		logger.debug("Http '" + page.getMethod() + "' URL: " + page.getUrl());
		fetchByteData(page, setting);		
		
		return true;
	}

	/**
	 * 数据解码，按照数据编码的方式对数据进行解析
	 * 
	 * @param encoding
	 *            数据字符编码
	 * @param bytes
	 *            字节串
	 * @return 解码后的数据字符串
	 * @throws IOException
	 *             输入输出流异常
	 */
	private static String uncompress(String encoding, byte[] bytes, String ziptype) throws IOException
	{
		if ("gzip".equalsIgnoreCase(ziptype))
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			GZIPInputStream gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0)
			{
				out.write(buffer, 0, n);
			}
			// toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
			if (StringUtils.isNotEmpty(encoding))
			{
				return out.toString(encoding);
			}
			else
			{
				return out.toString();
			}
		}
		else
		{
			return new String(bytes, encoding);
		}
	}

	/**
	 * 
	 * @param urlString
	 * @return
	 * @throws UrlFetchException
	 */
	protected void fetchByteData(WebPage page, FetcherSetting setting) throws UrlFetchException, HostForbiddenException
	{
		String urlString = page.getUrl();
		byte[] content = null;

		// Prepare HTTP client instance
		HttpClient httpclient = new HttpClient(connectionManager);
		HttpConnectionParams managerParams = httpclient.getHttpConnectionManager().getParams();
		managerParams.setConnectionTimeout(setting.getConnectionTimeout());
		httpclient.setState(CookieManager.getHttpState(urlString));

		// Prepare HTTP GET method
		HttpMethod httpMethod = null;
		try
		{
			String method = page.getMethod();
			if (HttpUtil.HTTP_METHOD_POST.equalsIgnoreCase(method))
			{
				httpMethod = new UTF8PostMethod(page.getUrl());

				// 这里是添加请求参数数据
				Map<String, String> params = page.getParams();
				if (params != null)
				{
					int paramSize = params.size();
					NameValuePair[] postData = new NameValuePair[paramSize];

					int i = 0;
					for (String key : params.keySet())
					{
						postData[i++] = new NameValuePair(key, params.get(key));
					}
					((PostMethod) httpMethod).addParameters(postData);
				}
			}
			else
			{
				httpMethod = new GetMethod(urlString);
			}
		}
		catch (IllegalArgumentException ex1)
		{
			ex1.printStackTrace();
			throw new UrlFetchException();
		}
		
		//设置浏览器的类型
		setBrowser(httpMethod, setting.getBrowserVersion());

		// 系统统一配置的数据请求头
		Map<String, String> defaultHeaders = setting.getHeaders();
		if (defaultHeaders != null && defaultHeaders.size() > 0)
		{
			addHeaderParameters(httpMethod, defaultHeaders);
		}

		// 请求某页面特殊的请求数据头
		Map<String, String> pageHeaders = page.getHeaders();
		if (pageHeaders != null && pageHeaders.size() > 0)
		{
			addHeaderParameters(httpMethod, pageHeaders);
		}
		
		try
		{
			long startTime = System.currentTimeMillis();
			
			// 执行页面下载
			int statusCode = httpclient.executeMethod(httpMethod);
			
			//计算页面获取的时间
			long endTime = System.currentTimeMillis();
			DashBoard.add(urlString, endTime - startTime);			
			
			//处理跳转的问题，在这里与网络爬虫不同，为了提高效率，不再抓取跳转的页面
			Header locationHeader = httpMethod.getResponseHeader("location");
			if (locationHeader != null)
			{
				String info = "Error, the url is redirect to : " + locationHeader.getValue();
				throw new UrlFetchException(info);
			}
			
			page.setHttpstatus(statusCode);		
			
			//检测数据页面返回的状态值
			checkStatusCode(page, statusCode);
			
			content = httpMethod.getResponseBody();			
			page.setContent(uncompress(page.getEncoding(), content, page.getZiptype()));

			// Save the cookies
			Cookie[] cookies = httpclient.getState().getCookies();
			for (int i = 0; i < cookies.length; i++)
			{
				CookieManager.addCookie(cookies[i]);
			}

			// MonitorThread.calculateCurrentSpeed();
			synchronized (Monitor.watch)
			{
				Monitor.fetchedCounter++;
			}

			
		}
		catch (Exception ex)
		{
			throw (new UrlFetchException(ex));
		}
		finally
		{
			// Release current connection to the connection pool once you are
			httpMethod.releaseConnection();
		}
	}
	
	/**
	 * 设置客户端的类型
	 * @param httpMethod
	 * @param browserVersion
	 */
	protected static void setBrowser(HttpMethod httpMethod, BrowserVersion browserVersion)
	{
		httpMethod.addRequestHeader("Accept", browserVersion.getHtmlAcceptHeader());
		httpMethod.addRequestHeader("User-Agent", browserVersion.getUserAgent());
		httpMethod.addRequestHeader("Connection", "keep-alive");
		httpMethod.addRequestHeader("Accep-language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
		httpMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	}

	/**
	 * 添加客户端请求数据的头部数据
	 * 
	 * @param httpMethod
	 *            请求方法
	 * @param headers
	 *            头部数据
	 */
	protected static void addHeaderParameters(HttpMethod httpMethod, Map<String, String> headers)
	{
		if (headers == null || headers.size() == 0)
		{
			return;
		}
		String value;
		for (String key : headers.keySet())
		{
			value = headers.get(key);
			httpMethod.addRequestHeader(key, value);
		}
	}
	
	// Inner class for UTF-8 support
	static class UTF8PostMethod extends PostMethod
	{
		final String Charset = "utf-8";
		public UTF8PostMethod(String url)
		{
			super(url);
		}

		@Override
		public String getRequestCharSet()
		{
			return Charset;
		}
	}
}
