/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  HttpCommonSender.java   
 * @Package com.loris.client.sender.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.sender.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loris.client.sender.HttpSender;
import com.loris.common.web.wrapper.Rest;

/**   
 * @ClassName:  HttpCommonSender    
 * @Description: 向服务器提交数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class HttpWebSender implements HttpSender
{
	/** 服务器地址 */
	protected String url;
	
	protected String encoding = "UTF-8";
	public static String Content_Type_Json = "application/json";	
	public static String Content_Type_Form = "application/x-www-form-urlencoded; charset=utf-8";
	
	/**
	 * Create a new instance of HttpCommonSender
	 */
	public HttpWebSender()
	{
	}
	
	/**
	 * Create a new instance of HttpCommonSender
	 * @param url
	 */
	public HttpWebSender(String url)
	{
		this.setUrl(url);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.sender.HttpSender#send(com.lang.Object)
	 */
	@Override
	public Rest send(Object records) throws IOException, HttpException
	{
		return send(null, records);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.sender.HttpSender#send(com.lang.Object)
	 */
	@Override
	public Rest send(String key, Object records) throws IOException, HttpException
	{
		return send(url, encoding, key, records);
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	/**
	 * 发送数据记录
	 * @param url
	 * @param contentType
	 * @param encoding
	 * @param key
	 * @param records
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static Rest send(String url, String encoding, String key, Object records)
			throws IOException, HttpException
	{
		String json = JSON.toJSONString(records);
		
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		
		if(StringUtils.isNotBlank(key))
		{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair(key, json));
	        post.addHeader("Content-type", Content_Type_Form);
	        post.setEntity(new UrlEncodedFormEntity(params, encoding));
		}
		else
		{
			StringEntity s = new StringEntity(json, encoding);
			s.setContentEncoding(encoding);
			s.setContentType(Content_Type_Json); // 发送json数据需要设置contentType
			post.setEntity(s);
		}
		
		HttpResponse res = httpclient.execute(post);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		{
			String result = EntityUtils.toString(res.getEntity());// 返回json格式：
			return Rest.okData(JSONObject.parse(result));
		}
		else
		{
			return Rest.failure("Status code is: " + res.getStatusLine().getStatusCode());
		}
	}
}
