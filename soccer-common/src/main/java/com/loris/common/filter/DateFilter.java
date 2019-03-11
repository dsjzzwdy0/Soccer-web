/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  DateFilter.java   
 * @Package com.loris.soccer.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.filter;

import java.util.Date;

import com.loris.common.filter.type.FilterType;

/**   
 * @ClassName: DateFilter   
 * @Description: 日期过滤器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class DateFilter extends AbstractFilter<Date> implements Filter<Date>
{
	/** 过滤的类型 */
	FilterType type;
		
	public DateFilter()
	{
		this(null);
	}
	
	public DateFilter(Date date)
	{
		this(date, FilterType.Equal);
	}
	
	public DateFilter(Date date, FilterType type)
	{
		this.value = date;
		this.type = type;
	}
	
	public FilterType getType()
	{
		return type;
	}

	public void setType(FilterType type)
	{
		this.type = type;
	}

	/**
	 *  (non-Javadoc)
	 * @see cn.hutool.core.lang.Filter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(Date t)
	{
		if(t == null) return false;
		if(value == null) return true;
		if(type == FilterType.Equal)
		{
			return value.equals(t);
		}
		else if(type == FilterType.GT)
		{
			return t.getTime() > value.getTime();
		}
		else if(type == FilterType.LT)
		{
			return t.getTime() < value.getTime();
		}
		else if(type == FilterType.NotGt)
		{
			return t.getTime() <= value.getTime();
		}
		else if(type == FilterType.NotLt)
		{
			return t.getTime() >= value.getTime();
		}		
		return false;
	}
}
