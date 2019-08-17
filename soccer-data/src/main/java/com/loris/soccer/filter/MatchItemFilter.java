/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MatchItemFilter.java   
 * @Package com.loris.soccer.filtercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.filter;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.filter.ObjectFilter;
import com.loris.soccer.model.base.MatchItem;

/**   
 * @ClassName:  MatchItemFilter.java   
 * @Description: 比赛元素的过滤器
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class  MatchItemFilter<T extends MatchItem> extends ObjectFilter<T>
{
	protected String mid;
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.AbstractFilter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(T obj)
	{
		if(obj == value) return true;
		if(value != null && value.equals(obj)) return true;
		return StringUtils.equals(mid, obj.getMid());
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.Filter#setValue(java.lang.Object)
	 */
	public void setValue(T value)
	{
		this.mid = value.getMid();
		this.value = value;
	}
	
	/**
	 * 设置比赛编号
	 * @param mid
	 */
	public void setMid(String mid)
	{
		this.mid = mid;
	}
}
