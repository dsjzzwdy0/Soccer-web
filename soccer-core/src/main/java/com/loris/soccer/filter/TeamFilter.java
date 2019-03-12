/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  TeamFilter.java   
 * @Package com.loris.soccer.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.filter;

import com.loris.common.filter.AbstractFilter;
import com.loris.soccer.model.Team;

/**   
 * @ClassName:  TeamFilter    
 * @Description: 球队数据的比较  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class TeamFilter extends AbstractFilter<Team>
{
	/**
	 *  (non-Javadoc)
	 * @see com.loris.common.filter.AbstractFilter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(Team obj)
	{
		if(this.value == obj) return true;
		if(value == null) return false;
		return value.equals(obj);
	}
}
