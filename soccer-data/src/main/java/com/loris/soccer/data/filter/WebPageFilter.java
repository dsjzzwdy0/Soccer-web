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
import com.loris.common.filter.Filter;

/**   
 * @ClassName:  WebPageFilter    
 * @Description: 网络页面过滤器   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class WebPageFilter implements Filter<WebPage>
{
	protected boolean initialized = false;
	
	/**
	 * 过滤器的初始化
	 * @return 是否成功
	 */
	public abstract boolean initialize();
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.Filter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(WebPage obj)
	{
		return false;
	}

	public boolean isInitialized()
	{
		return initialized;
	}
}
