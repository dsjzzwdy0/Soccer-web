/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchItemFilter.java   
 * @Package com.loris.soccer.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.filter;

import com.loris.common.filter.Filter;
import com.loris.soccer.model.base.MatchItem;

/**   
 * @ClassName: MatchItemFilter   
 * @Description: 比赛数据的过滤器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchItemFilter<T extends MatchItem> implements Filter<T>
{
	T matchItem;
	
	/**
	 * 
	 */
	public MatchItemFilter()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 数据过滤器
	 * @param item
	 */
	public MatchItemFilter(T item)
	{
		this.matchItem = item;
	}
	
	/**
	 * @param matchItem the matchItem to set
	 */
	public void setMatchItem(T matchItem)
	{
		this.matchItem = matchItem;
	}

	/**
	 *  (non-Javadoc)
	 * @see cn.hutool.core.lang.Filter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(T t)
	{
		return t != null && matchItem != null && t.getMid().equals(matchItem.getMid());
	}
}
