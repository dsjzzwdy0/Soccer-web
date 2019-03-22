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
	
	/**
	 * Create a new instance of DownloadedWebPageFilter.
	 */
	public DownloadedWebPageFilter()
	{
	}
	
	/**
	 * Create a new instance of DownloadedWebPageFilter.
	 * @param types
	 * @param source
	 */
	public DownloadedWebPageFilter(List<String> types, String source)
	{
		this.types.addAll(types);
		this.source = source;
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
	 * 过滤网络页面
	 * @param page 网络页面
	 * @return 是否接受的标志
	 */
	@Override
	public boolean accept(WebPage page)
	{
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
		
		
		return false;
	}
}
