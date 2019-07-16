/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooPageFilter.java   
 * @Package com.loris.soccer.data.okooo.filtercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.okooo.filter;

import com.loris.client.model.WebPage;
import com.loris.soccer.filter.WebPageFilter;

/**   
 * @ClassName:  OkoooPageFilter.java   
 * @Description: 澳客网页面的过滤器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OkoooPageFilter extends WebPageFilter
{
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.filter.WebPageFilter#accept(com.loris.client.model.WebPage, java.lang.Object)
	 */
	@Override
	public <T> boolean accept(WebPage page, T source)
	{
		return false;
	}

}
