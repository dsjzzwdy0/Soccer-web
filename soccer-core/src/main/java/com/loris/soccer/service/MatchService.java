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
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.MatchJc;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.view.MatchBdInfo;
import com.loris.soccer.model.view.MatchJcInfo;

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
	 * 添加北单比赛的数据
	 * @param matchBds 北单比赛数据
	 * @return 是否更新成功的标志
	 */
	boolean insertMatchBds(List<MatchBd> matchBds);
	
	/**
	 * 插入北单数据
	 * @param matchBds 北单比赛数据
	 * @param overwrite 是否覆盖已经有的比赛
	 * @return 是否更新成功的标识
	 */
	boolean insertMatchBds(List<MatchBd> matchBds, boolean overwrite);
	
	/**
	 * 按照日期查询北单数据
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 比赛列表
	 */
	List<MatchBd> getMatchBds(Date start, Date end);
	
	/**
	 * 插入竞彩比赛数据
	 * @param matchJcs 竞彩数据
	 * @return 是否插入成功的标志
	 */
	boolean insertMatchJcs(List<MatchJc> matchJcs);
	
	/**
	 * 插入竞彩数据
	 * @param matchJcs 
	 * @param overwrite
	 * @return
	 */
	boolean insertMatchJcs(List<MatchJc> matchJcs, boolean overwrite);
	
	
	/**
	 * 按照日期查询北单数据
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 比赛列表
	 */
	List<MatchJc> getMatchJcs(Date start, Date end);
	
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
	
	/**
	 * 按照北单的期号查询北单比赛数据
	 * @param bdno 北单期号
	 * @param issue 比赛日期
	 * @return 数据列表
	 */
	List<MatchBdInfo> getMatchBdInfos(String issue, String bdno);
	
	/**
	 * 获得竞彩比赛数据的列表
	 * @param issue 比赛期号
	 * @return 数据列表
	 */
	List<MatchJcInfo> getMatchJcInfos(String issue);
}
