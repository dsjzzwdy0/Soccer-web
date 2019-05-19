/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MatchSorter.java   
 * @Package com.loris.soccer.stat.algorithmcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.algorithm.sorter;

import java.util.Date;
import java.util.Comparator;

import com.loris.soccer.model.Match;


/**   
 * @ClassName:  MatchSorter.java   
 * @Description: 比赛比较器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchComparator implements Comparator<Match>
{
	private boolean asc;
	
	/**
	 * create a new instance of MatchComparator
	 */
	public MatchComparator()
	{
	}
	
	/**
	 * Create a new instance of MatchComparator
	 * @param asc
	 */
	public MatchComparator(boolean asc)
	{
		this.asc = asc;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Match o1, Match o2)
	{
		Date d1 = o1.getMatchtime();
		Date d2 = o2.getMatchtime();
		int r = 0;
		if(d1 == null && d2 == null) r = 0;
		if(d1 == null && d2 != null) r = -1;
		if(d1 != null && d2 == null) r = 1;
		r = d1.compareTo(d2);
		return asc ? r : -r;
	}

}
