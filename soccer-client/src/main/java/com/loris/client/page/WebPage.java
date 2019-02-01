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

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
	protected boolean completed = false;		//是否已经下载完成
	
	@TableField(exist=false)
	protected String content;			//页面内容	
	@TableField(exist=false)
	protected Map<String, String> headers =new LinkedHashMap<>();			//页面头信息
	@TableField(exist=false)
	protected Map<String, String> params = new LinkedHashMap<>();			//访问参数
		
	
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
}
