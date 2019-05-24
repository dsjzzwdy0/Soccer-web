/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchItemList.java   
 * @Package com.loris.soccer.collection   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.collection;

import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.model.base.MatchItem;

/**
 * @ClassName: MatchItemList
 * @Description: (@Todo)
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MatchItemList extends DataList<MatchItem>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 判断是否包含有同样的比赛数据
	 * @param item 类数据
	 * @return 标志
	 */
	@Override
	public boolean contains(Object item)
	{
		if(!(item instanceof MatchItem)) return false;
		return super.contains(item);
	}
	
	/**
	 * 判断是否包含有同样的比赛编号的数据
	 * @param mid 比赛编号
	 * @return 标志
	 */
	public boolean containsMid(String mid)
	{
		for(MatchItem item: this)
		{
			if(mid.equals(item.getMid()))
				return true;
		}
		return false;
}
}
