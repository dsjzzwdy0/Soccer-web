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
package com.loris.soccer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loris.common.model.TableRecords;
import com.loris.common.service.DataService;
import com.loris.common.service.SqlHelper;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.base.MatchItem;
import com.loris.soccer.service.LeagueService;
import com.loris.soccer.service.MatchService;

import static com.loris.soccer.constant.SoccerConstants.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: League
 * @Description: 存储数据页面的服务接口实现类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service("soccerDataService")
public class SoccerDataServiceImpl implements DataService
{
	//private static Logger logger = Logger.getLogger(SoccerDataServiceImpl.class);

	@Autowired
	protected LeagueService leagueService;
	
	@Autowired
	protected MatchService matchService;

	@Autowired
	protected SqlHelper sqlHelper;

	/**
	 * 保存数据页面解析得到的内容
	 * 
	 * @see com.loris.common.service.DataService#saveTableRecords(com.loris.common.model.TableRecords)
	 */
	@Override
	@Transactional
	public boolean saveTableRecords(TableRecords results)
	{
		for (String key : results.keySet())
		{
			switch (key)
			{
			case SOCCER_DATA_LEAGUE_LIST:
				LeagueList leagues = (LeagueList) results.get(key);
				leagueService.insertLeagues(leagues);
				break;
			case SOCCER_DATA_LOGO_LIST:
				break;
			case SOCCER_DATA_MATCH_LIST:
				MatchList matchList = (MatchList) results.get(key);
				matchService.insertMatchs(matchList);
				break;
			case SOCCER_DATA_MATCH_BD_LIST:
				MatchItemList matchItemList = (MatchItemList) results.get(key);
				List<MatchBd> matchBds = new ArrayList<>();
				for (MatchItem matchBd : matchItemList)
				{
					matchBds.add((MatchBd)matchBd);
				}
				matchService.insert(matchBds);
				break;
			default:
				// No nothing.
				break;
			}
			//logger.info(key);
		}
		return false;
	}
}
