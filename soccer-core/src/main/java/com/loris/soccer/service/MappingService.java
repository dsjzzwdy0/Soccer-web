/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MappingService.java   
 * @Package com.loris.soccer.service   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目的
 */
package com.loris.soccer.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.loris.common.web.wrapper.Pagination;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.League;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooTeam;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.base.Mapping;
import com.loris.soccer.model.mapping.LeagueMapping;
import com.loris.soccer.model.mapping.MatchMapping;
import com.loris.soccer.model.view.MatchInfo;

/**   
 * @ClassName:  MappingService    
 * @Description: 映射数据服务类：解决中国足彩网、澳客网之间的比赛数据、欧赔、
 * 			亚盘等数据之间的相互匹配，从页提供全面的数据服务。
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public interface MappingService
{
	/**
	 * 查询该时间段内的所有的比赛映射数据
	 * @param source 数据来源
	 * @param dest 数据映射目标
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 所有的映射结果
	 */
	List<MatchMapping> getMatchMappings(String source, String dest, Date start, Date end);
	
	/**
	 * 查询澳客网的期号比赛数据
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 所有的比赛数据
	 */
	List<OkoooIssueMatch> getOkoooIssueMatchs(Date start, Date end);
	
	/**
	 * 获得中国足彩网的比赛数据
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 所有的比赛数据
	 */
	List<IssueMatch> getIssueMatchs(Date start, Date end);
	
	/**
	 * 获得比赛数据列表
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 比赛列表数据
	 */
	List<MatchInfo> getMatchs(Date start, Date end);
	
	/**
	 * 获得比赛列表数据
	 * @param mids 比赛的编号列表
	 * @return 比赛数据列表
	 */
	List<MatchInfo> getMatchs(List<String> mids);
	
	/**
	 * 获得澳客网比赛数据列表
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 比赛列表
	 */
	List<OkoooMatch> getOkoooMatchs(Date start, Date end);
	
	/**
	 * 获得澳客网比赛列表数据
	 * @param mids 比赛的编号列表
	 * @return 比赛数据列表
	 */
	List<OkoooMatch> getOkoooMatchs(List<String> mids);
	
	/**
	 * 获得足彩网的联赛数据
	 * @return 联赛列表
	 */
	List<League> getLeagues();
	
	/**
	 * 获得澳客网联赛数据
	 * @return 联赛列表
	 */
	List<OkoooLeague> getOkoooLeagues();
	
	/**
	 * 获得联赛的比赛球队
	 * @return
	 */
	List<Team> getTeams();
	
	/**
	 * 获得联赛的比赛球队
	 * @param tids 球队编号
	 * @return 球队列表
	 */
	List<Team> getTeams(List<String> tids);
	
	/**
	 * 获得澳客球队列表
	 * @return 球队列表
	 */
	List<OkoooTeam> getOkoooTeams();
	
	/**
	 * 获得澳客联赛的球队信息
	 * @param names 名称编号
	 * @return 球队列表
	 */
	List<OkoooTeam> getOkoooTeams(List<String> names);
	
	/**
	 * 插入映射数据列表
	 * @param mappings 映射列表
	 * @return 是否成功的标志
	 */
	<T extends Mapping> boolean insertList(Class<T> clazz, List<T> mappings);
	
	/**
	 * 获得页面数据
	 * @param page 分页信息
	 * @return 分页数据列表
	 */
	IPage<LeagueMapping> getLeagueMapping(Pagination pagination);
}
