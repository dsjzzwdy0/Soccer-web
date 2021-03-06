/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.complex.TeamGrade;
import com.loris.soccer.model.view.IssueMatchInfo;
import com.loris.soccer.model.view.MatchInfo;

/**   
 * @ClassName:  League   
 * @Description: 比赛数据服务接口，包含有比赛基本数据服务接口、北单比赛数据接口、
 * 竞彩比赛数据接口、足彩比赛数据接口
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface MatchService extends IService<Match>
{
	/**
	 * 插入比赛数据记录
	 * @param matchs 比赛列表
	 * @return 是否插入数据记录成功的标志
	 */
	boolean insertMatchs(List<Match> matchs);
	
	/**
	 * 插入比赛数据记录
	 * @param matchs 比赛列表
	 * @param overwrite 是否覆盖原有的数据记录
	 * @return 是否插入数据记录成功的标志
	 */
	boolean insertMatchs(List<Match> matchs, boolean overwrite);
	
	/**
	 * 按照日期查询比赛数据
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 比赛列表
	 */
	List<Match> getMatchs(Date start, Date end);
	
	/**
	 * 查询比赛数据
	 * @param start
	 * @param end
	 * @param hasResult
	 * @return 比赛列表
	 */
	List<MatchInfo> getMatchInfos(Date start, Date end, Boolean hasResult);
	
	/**
	 * 通过条件查询比赛数据
	 * @param lid 联赛编号
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param hasResult 是否有比赛结果
	 * @return 比赛列表
	 */
	List<MatchInfo> getMatchInfos(String lid, Date start, Date end, Boolean hasResult);
	
	/**
	 * 通过比赛编号查询比赛数据
	 * @param mid 比赛编号
	 * @return 比赛数据
	 */
	MatchInfo getMatchInfo(String mid);
	
	/**
	 * 查询比赛数据
	 * @param lids 联赛编号
	 * @param tids 球队编号
	 * @param hasResult
	 * @return 比赛列表
	 */
	List<MatchInfo> getMatchInfos(List<String> lids, List<String> tids, Boolean hasResult);
	
	/**
	 * 获得比赛信息数据 
	 * @param lid
	 * @param season
	 * @param round
	 * @return
	 */
	List<MatchInfo> getMatchInfos(String lid, String season, String round);
	
	/**
	 * 获得球队的战线信息
	 * @param matchs 比赛数据
	 * @param sameLeague 是否同一联赛
	 * @return 战线数据
	 */
	List<TeamGrade> getTeamGrades(List<? extends Match> matchs, int maxMatchNum, boolean sameLeague);
	
	/**
	 * 添加北单比赛的数据
	 * @param matchBds 北单比赛数据
	 * @return 是否更新成功的标志
	 */
	boolean insertIssueMatchs(List<IssueMatch> issueMatchs);
	
	/**
	 * 插入北单数据
	 * @param matchBds 北单比赛数据
	 * @param overwrite 是否覆盖已经有的比赛
	 * @return 是否更新成功的标识
	 */
	boolean insertIssueMatchs(List<IssueMatch> issueMatchs, boolean overwrite);
	
	/**
	 * 按照日期查询北单数据
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 比赛列表
	 */
	List<IssueMatch> getIssueMatchs(String type, Date start, Date end);
	
	/**
	 * 查询股注的比赛数据
	 * @param issue
	 * @param type
	 * @return
	 */
	List<IssueMatch> getIssueMatchs(String issue, String type);
	
	/**
	 * 查询投注比赛的数据列表
	 * @param issue
	 * @param type
	 * @return
	 */
	List<IssueMatchInfo> getIssueMatchsInfo(String issue, String type);
	
	/**
	 * 获得比赛的信息
	 * @param mids
	 * @return
	 */
	List<IssueMatchInfo> getIssueMatchsInfo(List<String> mids);
	
	/**
	 * 插入比赛结果数据记录
	 * @param results 比赛结果
	 * @return 是否成功的标志
	 */
	boolean insertMatchResults(List<MatchResult> results);
	
	/**
	 * 插入比赛结果数据记录
	 * @param results 比赛结果
	 * @param overwrite 是否覆盖
	 * @return 是否成功标志
	 */
	boolean insertMatchResults(List<MatchResult> results, boolean overwrite);
}
