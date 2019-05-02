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

import com.loris.client.model.WebPage;
import com.loris.client.service.WebPageService;
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

	/** 网络服务 */
	protected WebPageService pageService;

	/**
	 * 过滤器的初始化
	 * 
	 * @return 是否成功
	 */
	public abstract boolean initialize();

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
}
