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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loris.common.dao.SqlHelper;
import com.loris.common.wrapper.TableRecords;
import com.loris.soccer.model.League;
import com.loris.soccer.service.LeagueService;
import com.loris.soccer.service.SoccerDataService;

import static com.loris.soccer.constant.SoccerConstants.*;

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
@Service("webPageResultsService")
public class SoccerServiceImpl implements SoccerDataService
{
	private static Logger logger = Logger.getLogger(SoccerServiceImpl.class);

	@Autowired
	protected LeagueService leagueService;

	@Autowired
	protected SqlHelper sqlHelper;

	/**
	 * 保存数据页面解析得到的内容
	 * 
	 * @see com.loris.soccer.service.SoccerDataService#saveSoccerDataRecords(com.loris.common.wrapper.TableRecords)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean saveSoccerDataRecords(TableRecords results)
	{
		for (String key : results.keySet())
		{
			switch (key)
			{
			case SOCCER_DATA_LEAGUE_LIST:
				List<League> leagues = (List<League>) results.get(key);
				leagueService.insertLeagues(leagues);
				break;
			default:
				// No nothing.
				break;
			}
			logger.info(key);
		}
		return false;
	}
}
