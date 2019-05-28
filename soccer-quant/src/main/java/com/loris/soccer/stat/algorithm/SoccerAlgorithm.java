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

import org.apache.commons.lang3.StringUtils;

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
	 * 计算球队的成绩，其中Grades作为原始的数据
	 * @param grades 球队数据
	 * @param matchinfos 比赛数据
	 * @param filter 过滤器
	 * @param maxMatchNum 最大的比赛数
	 * @return
	 */
	public static List<TeamGrade> computeTeamGrades(List<TeamGrade> grades, List<MatchInfo> matchInfos,
			ObjectFilter<MatchInfo> filter, int maxMatchNum)
	{
		for (TeamGrade grade : grades)
		{
			computeTeamGrade(grade, matchInfos, filter, maxMatchNum);
		}
		return grades;
	}
	
	/**
	 * 计算球队的成绩数据
	 * @param grade 球队数据
	 * @param matchInfos 比赛列表
	 * @param filter 过滤器
	 * @param maxMatchNum 最大比赛数
	 * @return 球队战绩
	 */
	public static TeamGrade computeTeamGrade(TeamGrade grade, List<MatchInfo> matchInfos, ObjectFilter<MatchInfo> filter, 
			int maxMatchNum)
	{
		MatchComparator comparator = new MatchComparator(false);
		MatchInfo[] ms = new MatchInfo[matchInfos.size()];
		ms = matchInfos.toArray(ms);
		Arrays.sort(ms, comparator);
		
		int num = 0;
		for (MatchInfo match : ms)
		{
			if((!StringUtils.equals(grade.getTid(), match.getHomeid())
					&& !StringUtils.equals(grade.getTid(), match.getClientid()))
					|| match.getResult() == null)
			{
				continue;
			}
			if(filter == null || filter.accept(match))
			{
				if(!grade.addMatchInfo(match))
					continue;
				
				//计数
				num ++;
				if(num >= maxMatchNum)
					break;
			}
		}
		return grade;
	}
}
