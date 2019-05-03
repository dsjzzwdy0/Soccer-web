/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ZgzcwRoundPageFilter.java   
 * @Package com.loris.soccer.data.zgzcw.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data.zgzcw.filter;

import com.loris.client.model.WebPage;
import com.loris.soccer.data.filter.WebPageFilter;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;

/**   
 * @ClassName: ZgzcwRoundPageFilter   
 * @Description: 足彩网数据下载轮次下载的页面 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ZgzcwRoundPageFilter extends WebPageFilter
{
	/**
	 * Create a new insance of ZgzcwRoundPageFilter.
	 */
	public ZgzcwRoundPageFilter()
	{
		this.source = ZgzcwConstants.SOURCE_ZGZCW;
		types.add(ZgzcwConstants.PAGE_LEAGUE_CUP);
		types.add(ZgzcwConstants.PAGE_LEAGUE_LEAGUE_ROUND);
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.data.filter.WebPageFilter#accept(com.loris.client.model.WebPage, java.lang.Object)
	 */
	@Override
	public <T> boolean accept(WebPage page, T source)
	{
		return false;
	}
}
