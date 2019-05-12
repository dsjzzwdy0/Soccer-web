/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsValueFilter.java   
 * @Package com.loris.soccer.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.filter;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.filter.ObjectFilter;
import com.loris.soccer.model.base.OddsValue;

/**
 * @ClassName: OddsValueFilter
 * @Description: (@Todo)
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class OddsValueFilter extends ObjectFilter<OddsValue>
{
	/**
	 * 检测是否相同的值
	 * @param oddsValue
	 * @return
	 */
	@Override
	public boolean accept(OddsValue oddsValue)
	{
		if(StringUtils.equals(value.getMid(), oddsValue.getMid()) &&
				StringUtils.equals(value.getCorpid(), oddsValue.getCorpid()))
		{
			return true;
		}
		return false;
	}
}
