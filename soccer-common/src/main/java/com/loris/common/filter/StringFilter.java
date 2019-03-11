/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.filter;

import org.apache.commons.lang3.StringUtils;
/**   
 * @ClassName:  League   
 * @Description: 字符串过滤器
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class StringFilter extends AbstractFilter<String> implements Filter<String>
{	
	/**
	 * Create a new instance of LeagueChecker.
	 */
	public StringFilter()
	{
	}
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.util.ArraysUtil.Filter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(String obj)
	{
		return StringUtils.equals(value, obj);
	}
}
