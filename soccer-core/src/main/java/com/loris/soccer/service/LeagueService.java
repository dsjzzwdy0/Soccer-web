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

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Logo;
import com.loris.soccer.model.Rank;
import com.loris.soccer.model.Round;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.TeamRfSeason;

/**
 * @ClassName: League
 * @Description: 联赛数据服务
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface LeagueService extends IService<League>
{
	/**
	 * 插入联赛数据类
	 * 
	 * @param leagues
	 *            联赛数据列表
	 * @return 是否成功的标志
	 */
	boolean insertLeagues(List<League> leagues);
	
	/**
	 * 插入联赛数据
	 * @param leagues 联赛数据
	 * @param overwrite 是否覆盖数据
	 * @return 插入成功的标志
	 */
	boolean insertLeagues(List<League> leagues, boolean overwrite);

	/**
	 * 联赛名称
	 * 
	 * @param name
	 * @return 是否成功的标志
	 */
	League getLeague(String name);
	
	/**
	 * 保存球队数据
	 * @param teams 球队数据列表
	 * @return 是否成功的标志
	 */
	boolean insertTeams(List<Team> teams);
	
	/**
	 * 保存球队数据
	 * @param teams 球队数据列表
	 * @param overwrite 是否覆盖原有数据
	 * @return 是否成功的标志
	 */
	boolean insertTeams(List<Team> teams, boolean overwrite);
	
	/**
	 * 保存联赛轮次数据
	 * @param rounds
	 * @return
	 */
	boolean insertRounds(List<Round> rounds);
	
	/**
	 * 保存联赛轮次数据
	 * @param rounds
	 * @param overwrite
	 * @return
	 */
	boolean insertRounds(List<Round> rounds, boolean overwrite);
	
	/**
	 * 插入联赛数据的排名
	 * @param ranks 排名列表
	 * @return 是否成功
	 */
	boolean insertRanks(List<Rank> ranks);
	
	/**
	 * 插入联赛数据的排名
	 * @param ranks 排名列表
	 * @param overwrite 是否覆盖
	 * @return 是否成功
	 */
	boolean insertRanks(List<Rank> ranks, boolean overwrite);
	
	/**
	 * 插入图标数据
	 * @param logos 图标数据
	 * @return 是否成功的标志
	 */
	boolean insertLogos(List<Logo> logos);
	
	/**
	 * 插入图标数据
	 * @param logos 图标数据
	 * @param overwrite 是否覆盖
	 * @return 成功的标志
	 */
	boolean insertLogos(List<Logo> logos, boolean overwrite);
	
	/**
	 * 插入球队与联赛比赛的数据关系
	 * @param teamRfSeasons 
	 * @return 是否成功
	 */
	boolean insertTeamRfSeasons(List<TeamRfSeason> teamRfSeasons);
	
	/**
	 * 插入球队与联赛比赛的数据关系
	 * @param teamRfSeasons 数据列表
	 * @param overwrite 是否改写
	 * @return 是否成功
	 */
	boolean insertTeamRfSeasons(List<TeamRfSeason> teamRfSeasons, boolean overwrite);
}
