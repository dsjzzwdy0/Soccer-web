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
import java.util.Date;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.log4j.Logger;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.fetcher.util.CookieManager;
import com.loris.client.fetcher.util.DashBoard;
import com.loris.client.fetcher.util.Monitor;
import com.loris.client.page.WebPage;

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
		byte[] bs = fetchByteData(page, setting);
		
		page.setContent(uncompress(page.getEncoding(), bs, page.getZiptype()));
		
		page.setCompleted(true);
		page.setLoadtime(new Date());
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
	protected static byte[] fetchByteData(WebPage page, FetcherSetting setting) throws UrlFetchException, HostForbiddenException
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
			if (HTTP_METHOD_POST.equalsIgnoreCase(method))
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
			int result = httpclient.executeMethod(httpMethod);
			
			//计算页面获取的时间
			long endTime = System.currentTimeMillis();
			DashBoard.add(urlString, endTime - startTime);
			
			page.setHttpstatus(result);
			if (result == HttpStatus.SC_FORBIDDEN)
			{
				throw new HostForbiddenException(urlString);
			}
			else if(result != HttpStatus.SC_OK)
			{
				throw new UrlFetchException("Error code is '" + result + "', Failed to fetch page: " + page.getUrl());
			}

			content = httpMethod.getResponseBody();

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

			//处理跳转的问题，在这里与网络爬虫不同，为了提高效率，不再抓取跳转的页面
			Header locationHeader = httpMethod.getResponseHeader("location");
			if (locationHeader != null)
			{
				String info = "Error, the url is redirect to : " + locationHeader.getValue();
				logger.info(info);
				throw new UrlFetchException(info);
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
		return content;
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
