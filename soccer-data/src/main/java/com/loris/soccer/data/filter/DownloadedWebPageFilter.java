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

/**   
 * @ClassName:  WebPageFilter    
 * @Description: 已经下载的网络页面过滤器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class DownloadedWebPageFilter extends WebPageFilter
{
	/** 网络服务 */
	protected WebPageService pageService;
	
	/** 网络页面类型 */
	protected List<String> types = new ArrayList<>();
	
	/** 数据来源 */
	protected String source;
	
	/** 开始时间 */
	protected Date start;
	
	/** 结束时间 */
	protected Date end;
	
	/** 已经下载的数据 */
	List<WebPage> existWebPages = null;
	
	/**
	 * Create a new instance of DownloadedWebPageFilter.
	 */
	public DownloadedWebPageFilter()
	{
	}
	
	/**
	 * Create a new instance of DownloadedWebPageFilter.
	 * @param types 数据类型
	 * @param source 数据来源
	 * @param start 开始日期
	 * @param end 结束日期
	 */
	public DownloadedWebPageFilter(List<String> types, String source, Date start, Date end)
	{
		this.types.addAll(types);
		this.source = source;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * 添加页面的类型
	 * @param type
	 */
	public void addPageType(String type)
	{
		types.add(type);
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
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.filter.WebPageFilter#accept(com.loris.client.model.WebPage, java.lang.Object)
	 */
	@Override
	public <T> boolean accept(WebPage page, T source)
	{
		String type = page.getType();
		if(!types.contains(type)) return true;		
		if(existWebPages == null || existWebPages.size() == 0) return true;
		
		for (WebPage existPage : existWebPages)
		{
			if(page.equals(existPage) && needToReject(page, existPage, source))
			{
				return false;
			}
		}		
		return true;
	}
	
	/**
	 * 是否需要对页面进行拒绝
	 * @param page 当前页面
	 * @param existPage 存在的页面
	 * @param source 数据来源
	 * @return 是否存在的标示 
	 */
	protected<T> boolean needToReject(WebPage page, WebPage existPage, T source)
	{
		Date loadtime = existPage.getLoadtime();
		if(loadtime == null)
		{
			return false;
		}
		return false;
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.filter.WebPageFilter#initialize()
	 */
	@Override
	public boolean initialize()
	{
		if(pageService == null)
		{
			pageService = ApplicationContextHelper.getBean(WebPageService.class);
		}
		if(pageService == null)
		{
			throw new IllegalArgumentException("The WebPageService is null, can't initialize the DownloadedWebPageFilter.");
		}
		existWebPages = pageService.getWebPage(source, types, start, end);		
		//System.out.println("There are total " + existWebPages.size() + " pages in database.");
		initialized = true;
		return true;
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
}
