/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @SoccerAlgorithm.java   
 * @Package com.loris.soccer.stat.algorithmcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.algorithm;

import java.util.Arrays;
import java.util.List;

import com.loris.common.filter.ObjectFilter;
import com.loris.soccer.model.complex.TeamGrade;
import com.loris.soccer.model.view.MatchInfo;
import com.loris.soccer.stat.algorithm.sorter.MatchComparator;

/**   
 * @ClassName:  SoccerAlgorithm.java   
 * @Description: 足球数据的算法  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class SoccerAlgorithm
{
	/**
	 * 计算球队的成绩数据
	 * @param tid 球队编号
	 * @param name 球队成绩的名称
	 * @param matchInfos 比赛列表
	 * @param filter 过滤器
	 * @param maxMatchNum 最大比赛数
	 * @return 球队战绩
	 */
	public static TeamGrade computeTeamGrade(String tid, String name, List<MatchInfo> matchInfos, ObjectFilter<MatchInfo> filter, int maxMatchNum)
	{
		MatchComparator comparator = new MatchComparator(false);
		MatchInfo[] ms = new MatchInfo[matchInfos.size()];
		ms = matchInfos.toArray(ms);
		Arrays.sort(ms, comparator);
		
		TeamGrade grade = new TeamGrade(tid);
		grade.setName(name);
		int num = 0;
		for (MatchInfo match : ms)
		{
			if(filter == null || filter.accept(match))
			{
				grade.addMatchInfo(match);
				//计数
				num ++;
				if(num >= maxMatchNum)
					break;
			}
		}
		return grade;
	}
}
