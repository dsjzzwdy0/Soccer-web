/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  FilterType.java   
 * @Package com.loris.common.filter.type   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.filter.type;

/**   
 * @ClassName: FilterType   
 * @Description: 过滤器的类型  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public enum FilterType
{
	/**
	 * 等于
	 */
	Equal,
	
	/**
	 * 大于
	 */
	GT,
	
	/**
	 * 小于
	 */
	LT,
	
	/**
	 * 不大于
	 */
	NotGt,
	
	/**
	 * 不小于
	 */
	NotLt,
	
	/**
	 * 日期相等
	 */
	EqualDay
}
