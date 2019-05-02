/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebPageFilter.java   
 * @Package com.loris.soccer.data.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.loris.client.model.WebPage;
import com.loris.client.service.WebPageService;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.common.filter.Filter;

/**
 * @ClassName: WebPageFilter
 * @Description: 网络页面过滤器
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public abstract class WebPageFilter implements Filter<WebPage>
{
	/** 初始化的标志 */
	protected boolean initialized = false;
	
	/** 网络页面类型 */
	protected List<String> types = new ArrayList<>();
	
	/** 数据来源 */
	protected String source;
	
	/** 已经下载的数据 */
	protected List<WebPage> existWebPages = null;

	/** 网络服务 */
	protected WebPageService pageService;
	
	/** 开始时间 */
	protected Date start;
	
	/** 结束时间 */
	protected Date end;

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.filter.WebPageFilter#initialize()
	 */
	public boolean initialize()
	{
		if(pageService == null)
		{
			pageService = ApplicationContextHelper.getBean(WebPageService.class);
		}
		if(pageService == null)
		{
			throw new IllegalArgumentException("The WebPageService is null, can't initialize the ZgzcwWebPageFilter.");
		}
		existWebPages = pageService.getWebPage(source, types, start, end);
		initialized = true;
		return true;
	}

	/**
	 * 检测是否满足条件
	 * 
	 * @param page 页面内容
	 * @param source 数据来源
	 * @return 是否满足条件
	 */
	abstract public <T> boolean accept(WebPage page, T source);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.common.filter.Filter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(WebPage obj)
	{
		return accept(obj, null);
	}

	/**
	 * 检测是否已经初始化
	 * 
	 * @return
	 */
	public boolean isInitialized()
	{
		return initialized;
	}
	
	public Date getStart()
	{
		return start;
	}

	public void setStart(Date start)
	{
		this.start = start;
	}

	public Date getEnd()
	{
		return end;
	}

	public void setEnd(Date end)
	{
		this.end = end;
	}

	public String getSource()
	{
		return source;
	}
	
	/**
	 * 设置数据来源
	 * @param source
	 */
	public void setSource(String source)
	{
		this.source = source;
	}
	
	/**
	 * 添加页面的类型
	 * @param type
	 */
	public void addAcceptPageType(String type)
	{
		types.add(type);
	}
	
	public void setAcceptPageTypes(List<String> types)
	{
		this.types.addAll(types);
	}
}
