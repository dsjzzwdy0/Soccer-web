/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooLeague.java   
 * @Package com.loris.soccer.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.model;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;

/**   
 * @ClassName:  OkoooLeague.java   
 * @Description: 澳客联赛类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
@TableName("okooo_league")
public class OkoooLeague extends League
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj == this)
		{
			return true;
		}
		if(obj == null || !(obj instanceof OkoooLeague))
		{
			return false;
		}
		return StringUtils.isNotBlank(lid) && lid.equalsIgnoreCase(((OkoooLeague)obj).getLid());
	}
}
