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

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.log4j.Logger;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.fetcher.WebFetcher;
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
public class HttpCommonFetcher implements WebFetcher
{
	private static Logger logger = Logger.getLogger(HttpCommonFetcher.class);

	public static final String Charset = "utf-8";

	// Inner class for UTF-8 support
	public static class UTF8PostMethod extends PostMethod
	{
		public UTF8PostMethod(String url)
		{
			super(url);
		}

		@Override
		public String getRequestCharSet()
		{
			// return super.getRequestCharSet();
			return Charset;
		}
	}

	// -- Reusable conneciton manager.
	public static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

	/**
	 * 初始化页面
	 * 
	 * @see com.loris.client.fetcher.WebFetcher#init()
	 */
	@Override
	public void init()
	{
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
	public boolean download(WebPage page) throws UrlFetchException
	{
		return false;
	}

	/**
	 * Close the Fetcher.
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		// Do nothing.
	}

	/**
	 * Fetch the page data from web.
	 * 
	 * @param page
	 * @return
	 * @throws UrlFetchException
	 */
	public static boolean fetch(WebPage page) throws UrlFetchException
	{
		logger.debug("Downloading page: " + page);

		// String urlString = page.getURL();
		String method = page.getMethod();
		byte[] bs;
		if ("post".equalsIgnoreCase(method))
		{
			bs = postByteData(page);
		}
		else
		{
			bs = fetchByteData(page);
		}

		try
		{
			page.setContent(uncompress(page.getEncoding(), bs, page.getZiptype()));
		}
		catch (Exception e)
		{
			logger.warn("Error in Encoding " + page.getUrl() + ": " + e);
			return false;
		}

		// page.setBytes(bs);
		page.setCompleted(true);
		page.setLoadtime(DateUtil.getCurTimeStr());
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
	protected static byte[] fetchByteData(WebPage page) throws UrlFetchException
	{
		String urlString = page.getFullURL();
		log.debug("Fetching URL " + urlString);

		byte[] content = null;

		// Prepare HTTP client instance
		HttpClient httpclient = new HttpClient(connectionManager);
		// HttpClientConnectionManager connectionManager = new
		// MultiThreadedHttpConnectionManager();
		// CloseableHttpClient httpClient2 =
		// HttpClients.custom().setConnectionManager(connectionManager).build();
		// httpclient.setHttpConnectionManager(connectionManager);

		HttpConnectionParams managerParams = httpclient.getHttpConnectionManager().getParams();
		// RequestConfig requestConfig =
		// RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
		// .build();
		managerParams.setConnectionTimeout(ConfigParser.getSettings().getConnectionTimeout());
		// managerParams.setParameter(ClientPNames.COOKIE_POLICY,
		// CookiePolicy.BEST_MATCH);

		// httpclient.setConnectionTimeout(ConfigParser.getSettings().getConnectionTimeout());
		httpclient.setState(CookieManager.getHttpState(urlString));

		// Prepare HTTP GET method
		GetMethod httpget = null;
		try
		{
			httpget = new GetMethod(urlString);
		}
		catch (IllegalArgumentException ex1)
		{
			ex1.printStackTrace();
			throw new UrlFetchException();
		}

		// 系统统一配置的数据请求头
		Map<String, String> headers = ConfigParser.getSettings().getHeaders();
		if (headers != null)
		{
			addHeaderParameters(httpget, headers);
		}

		// 请求某页面特殊的请求数据头
		headers = page.getHeaders();
		if (page.isHasMoreHeader() && headers != null)
		{
			addHeaderParameters(httpget, headers);
		}

		// Execute HTTP GET
		int result = 0;
		try
		{

			long startTime = System.currentTimeMillis();
			result = httpclient.executeMethod(httpget);
			page.setHttpstatus(result);

			if (result == HttpUtil.NOT_FOUND_404)
			{
				throw (new UrlFetchException("Page not Found."));
			}
			else if (result == HttpStatus.SC_FORBIDDEN)
			{
				page.setHttpstatus(result);
			}

			content = httpget.getResponseBody();

			// byte[] bs = httpget.getResponseBody();
			// System.out.println(new String(bs));
			// httpget.get
			// System.out.println(httpget.getResponseCharSet());

			long endTime = System.currentTimeMillis();
			DashBoard.add(urlString, endTime - startTime);

			// log.debug( "Content: " );
			// log.debug( content );

			// Save the cookies
			Cookie[] cookies = httpclient.getState().getCookies();
			for (int i = 0; i < cookies.length; i++)
			{
				CookieManager.addCookie(cookies[i]);
			}

			MonitorThread.calculateCurrentSpeed();

			synchronized (Monitor.watch)
			{
				Monitor.fetchedCounter++;
			}
			// log.info("FETCHED " + Crawler.fetchedCounter + "th URL: " +
			// urlString + " " + result);
			// log.debug ( "Response code: " + result );
			// CookieManager.printAllCookies();

			String redirectLocation;
			Header locationHeader = httpget.getResponseHeader("location");
			if (locationHeader != null)
			{
				redirectLocation = locationHeader.getValue();
				log.debug("Redirect Location: " + redirectLocation);

				if (redirectLocation != null)
				{
					// Perform Redirect!
					content = fetchByteArray(redirectLocation);
				}
				else
				{
					// The response is invalid and did not provide the new
					// location for
					// the resource. Report an error or possibly handle the
					// response
					// like a 404 Not Found error.
					log.error("Error redirecting");
				}

			}

		}
		catch (Exception ex)
		{
			throw (new UrlFetchException(ex));
		}
		finally
		{
			// Release current connection to the connection pool once you are
			// done
			httpget.releaseConnection();
		}

		return content;
	}

	/**
	 * Get the ajax method information
	 * 
	 * @param page
	 * @return
	 */
	protected static byte[] postByteData(WebPage page) throws UrlFetchException
	{
		byte[] content = null;
		HttpClient client = new HttpClient();

		HttpConnectionParams managerParams = client.getHttpConnectionManager().getParams();
		// RequestConfig requestConfig =
		// RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
		// .build();
		managerParams.setConnectionTimeout(ConfigParser.getSettings().getConnectionTimeout());
		// managerParams.setParameter(ClientPNames.COOKIE_POLICY,
		// CookiePolicy.BEST_MATCH);

		// httpclient.setConnectionTimeout(ConfigParser.getSettings().getConnectionTimeout());
		client.setState(CookieManager.getHttpState(page.getUrl()));

		// Prepare HTTP GET method
		PostMethod httppost = null;
		try
		{
			httppost = new UTF8PostMethod(page.getUrl());
		}
		catch (IllegalArgumentException ex1)
		{
			ex1.printStackTrace();
			throw new UrlFetchException(ex1.toString());
		}

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
			httppost.addParameters(postData);
		}

		// PostMethod httppost = new PostMethod(page.getURL());
		// httppost.addParameters(postData);
		Map<String, String> headers = ConfigParser.getSettings().getHeaders();
		if (headers != null)
		{
			addHeaderParameters(httppost, headers);
		}

		// 头部请求数据自定义
		headers = page.getHeaders();
		if (page.isHasMoreHeader() && headers != null)
		{
			addHeaderParameters(httppost, headers);
		}

		try
		{
			// Execute the method.
			int statusCode = client.executeMethod(httppost);
			page.setHttpstatus(statusCode);

			if (statusCode == HttpStatus.SC_NOT_FOUND)
			{
				throw (new UrlFetchException("Page not Found."));
			}
			else if (statusCode == HttpStatus.SC_FORBIDDEN)
			{
				page.setHttpstatus(statusCode);
			}
			else if (statusCode != HttpStatus.SC_OK)
			{
				logger.info("Method failed: " + httppost.getStatusLine());

			}

			// Read the response body.
			content = httppost.getResponseBody();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new UrlFetchException(e);
		}
		finally
		{
			try
			{
				httppost.releaseConnection();

			}
			catch (Exception e)
			{
			}
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
		if (headers == null)
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

}
