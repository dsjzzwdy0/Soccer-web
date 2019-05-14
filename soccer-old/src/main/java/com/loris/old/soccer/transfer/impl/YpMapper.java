/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  YpMapper.java   
 * @Package com.loris.old.soccer.transfer.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.old.soccer.transfer.impl;

import java.util.List;

import com.loris.old.soccer.bean.Yp;
import com.loris.old.soccer.transfer.Mapper;
import com.loris.soccer.model.OddsYp;

/**   
 * @ClassName:  YpMapper    
 * @Description: 原始数据亚盘转换为新的亚盘数据 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class YpMapper implements Mapper<List<OddsYp>, Yp>
{
	/**
	 *  (non-Javadoc)
	 * @see com.loris.old.soccer.transfer.Mapper#mapping(java.lang.Object)
	 */
	@Override
	public List<OddsYp> mapping(Yp source)
	{
		return null;
	}
}
