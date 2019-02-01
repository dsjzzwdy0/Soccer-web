/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebPage.java   
 * @Package com.loris.net   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月29日 下午5:57:51   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.page;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.client.fetcher.util.HttpUtil;
import com.loris.soccer.bean.AutoIdEntity;

/**   
 * @ClassName:  WebPage   
 * @Description: 网络数据页面   
 * @author: 东方足彩
 * @date:   2019年1月29日 下午5:57:51   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@TableName("download_web_page")
public class WebPage extends AutoIdEntity
{
	/***/
	private static final long serialVersionUID = 1L;
	
	public static final String ENCODING_UTF8 = "utf-8";
	public static final String ENCODING_GBK = "GBK";
	public static final String ENCODING_GB2312 = "GB2312";
		
	protected String pageid;
	protected String url;
	protected String encoding;
	protected String protocol;
	protected String host;
	protected String port;
	protected String method;
	protected String type;
	protected int httpstatus;					//页面下载状态信息
	protected Date createtime;					//创建时间
	protected Date loadtime;					//下载时间
	protected String ziptype;
	protected boolean completed;				//是否已经下载完成
	
	@TableField(exist=false)
	protected String content;			//页面内容	
	@TableField(exist=false)
	protected Map<String, String> headers =new LinkedHashMap<>();			//页面头信息
	@TableField(exist=false)
	protected Map<String, String> params = new LinkedHashMap<>();			//访问参数
	
	/**
	 * 创建一个默认的数据下载页面
	 */
	public WebPage()
	{
		encoding = ENCODING_UTF8;
		protocol = "http";
		createtime = new Date();
		completed = false;
		method = HttpUtil.HTTP_METHOD_GET;
		port = "80";
	}
	
	/**
	 * 创建一个WebPage页面
	 * @param url
	 */
	public WebPage(String url)
	{
		this();
		pageid = url;
		this.url = url;
	}
	
	public String getPageid()
	{
		return pageid;
	}
	public void setPageid(String pageid)
	{
		this.pageid = pageid;
	}
	public Date getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getMethod()
	{
		return method;
	}
	public void setMethod(String method)
	{
		this.method = method;
	}
	public Map<String, String> getHeaders()
	{
		return headers;
	}
	public void setHeaders(Map<String, String> headers)
	{
		this.headers = headers;
	}
	public Map<String, String> getParams()
	{
		return params;
	}
	public void setParams(Map<String, String> params)
	{
		this.params = params;
	}
	public String getProtocol()
	{
		return protocol;
	}
	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
	}
	public String getHost()
	{
		return host;
	}
	public void setHost(String host)
	{
		this.host = host;
	}
	public String getPort()
	{
		return port;
	}
	public void setPort(String port)
	{
		this.port = port;
	}
	public Date getLoadtime()
	{
		return loadtime;
	}
	public void setLoadtime(Date loadtime)
	{
		this.loadtime = loadtime;
	}
	public boolean isCompleted()
	{
		return completed;
	}
	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getEncoding()
	{
		return encoding;
	}
	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getZiptype()
	{
		return ziptype;
	}
	public void setZiptype(String ziptype)
	{
		this.ziptype = ziptype;
	}
	public int getHttpstatus()
	{
		return httpstatus;
	}
	public void setHttpstatus(int httpstatus)
	{
		this.httpstatus = httpstatus;
	}
	/**
	 * 获得存储内容的唯一名字，实现的子类需要继承
	 * @return
	 */
	public String getContentFileName()
	{
		Date date = loadtime;
		if(date == null)
			throw new UnsupportedOperationException("The loadtime is null or invalid value.");
		return "" + date.getTime();
	}
	
	/**
	 * 获得目录地址：默认的按照年、月来组织
	 * @return 目录地址
	 */
	public String getPathName()
	{
		Date date = createtime;
		if(date == null)
		{
			throw new UnsupportedOperationException("The createtime is null or invalid value.");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return "" + calendar.get(Calendar.YEAR) + File.separator + calendar.get(Calendar.MONTH) + File.separator;		
	}
}
