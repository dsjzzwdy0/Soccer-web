/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  AbstractFilter.java   
 * @Package com.loris.common.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.filter;

/**   
 * @ClassName:  AbstractFilter    
 * @Description: 过滤器定义
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractFilter<T> implements Filter<T>
{
	protected T value;

	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.Filter#accept(java.lang.Object)
	 */
	@Override
	public abstract boolean accept(T obj);

	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.Filter#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(T value)
	{
		this.value = value;
	}

}
