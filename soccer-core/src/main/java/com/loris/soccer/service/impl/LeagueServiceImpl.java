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

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.common.util.ArraysUtil;
import com.loris.soccer.dao.LeagueMapper;
import com.loris.soccer.model.League;
import com.loris.soccer.service.LeagueService;
import com.loris.soccer.service.util.LeagueChecker;

/**
 * @ClassName: League
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service("leagueService")
public class LeagueServiceImpl extends ServiceImpl<LeagueMapper, League> implements LeagueService
{
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.loris.soccer.service.LeagueService#insertLeagues(java.util.List)
	 */
	@Override
	@Transactional
	public boolean insertLeagues(List<League> leagues)
	{
		List<String> lids = ArraysUtil.getObjectFieldValue(leagues, League.class, "lid");
		List<League> existLeagues = baseMapper.selectList(new QueryWrapper<League>().in("lid", lids));
		LeagueChecker checker = new LeagueChecker();

		List<League> newLeagues = new ArrayList<>();
		for (League l : leagues)
		{
			checker.setLeague(l);
			if (!ArraysUtil.hasSameObject(existLeagues, checker))
			{
				newLeagues.add(l);
			}
		}
		return saveBatch(newLeagues);
	}

}
