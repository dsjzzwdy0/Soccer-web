/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OkoooMatchFilter.java   
 * @Package com.loris.soccer.data.okooo.filter   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目的
 */
package com.loris.soccer.data.okooo.filter;

import java.util.Date;

import com.loris.common.filter.ObjectFilter;
import com.loris.soccer.model.base.MatchItem;

/**   
 * @ClassName:  OkoooMatchFilter    
 * @Description: 澳客比赛数据过滤器   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public class OkoooMatchFilter extends ObjectFilter<MatchItem>
{
	/**
	 * 检测是否通过数据下载
	 * @param match 比赛
	 * @param return 是否下载的标志
	 */
	@Override
	public boolean accept(MatchItem match)
	{
		Date matchtime = match.getMatchtime();
		if(matchtime == null) return false;
		long l = matchtime.getTime();
		return l > System.currentTimeMillis();
	}
}
