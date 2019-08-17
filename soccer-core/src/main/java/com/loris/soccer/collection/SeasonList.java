/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  SeasonList.java   
 * @Package com.loris.soccer.collection   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.collection;

import org.apache.commons.lang3.StringUtils;

import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.model.Season;

/**   
 * @ClassName: SeasonList   
 * @Description: 赛季列表数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class SeasonList extends DataList<Season>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获得最后一个的赛季信息
	 * @return
	 */
	public String getLastSeason()
	{
		String lastSeason = null;
		for(Season season: this)
		{
			if(StringUtils.isBlank(lastSeason))
			{
				lastSeason = season.getSeason();
				continue;
			}
			if(lastSeason.compareTo(season.getSeason()) < 0)
			{
				lastSeason = season.getSeason();
			}
		}
		return lastSeason;
	}

}
