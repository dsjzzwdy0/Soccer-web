/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  BetOddsServiceImpl.java   
 * @Package com.loris.soccer.service.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loris.common.filter.ObjectFilter;
import com.loris.common.service.SqlHelper;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.BetBdOddsMapper;
import com.loris.soccer.dao.BetJcOddsMapper;
import com.loris.soccer.model.BetBdOdds;
import com.loris.soccer.model.BetJcOdds;
import com.loris.soccer.service.BetOddsService;

/**   
 * @ClassName:  BetOddsServiceImpl    
 * @Description: 投注信息服务类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("betOddsService")
public class BetOddsServiceImpl implements BetOddsService
{
	@Autowired
	private SqlHelper sqlHelper;
	
	@Autowired
	private BetBdOddsMapper betBdOddsMapper;
	
	@Autowired
	private BetJcOddsMapper betJcOddsMapper;

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.BetOddsService#insertBetJcOdds(java.util.List)
	 */
	@Override
	public boolean insertBetJcOdds(List<BetJcOdds> odds)
	{
		return insertBetJcOdds(odds, true);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.BetOddsService#insertBetJcOdds(java.util.List, boolean)
	 */
	@Override
	public boolean insertBetJcOdds(List<BetJcOdds> odds, boolean overwrite)
	{
		ObjectFilter<BetJcOdds> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(odds, BetJcOdds.class, betJcOddsMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/* (non-Javadoc)
	 * @see com.loris.soccer.service.BetOddsService#insertBetBdOdds(java.util.List)
	 */
	@Override
	public boolean insertBetBdOdds(List<BetBdOdds> odds)
	{
		return insertBetBdOdds(odds, true);
	}

	/* (non-Javadoc)
	 * @see com.loris.soccer.service.BetOddsService#insertBetBdOdds(java.util.List, boolean)
	 */
	@Override
	public boolean insertBetBdOdds(List<BetBdOdds> odds, boolean overwrite)
	{
		ObjectFilter<BetBdOdds> filter = new ObjectFilter<>();	
		return SqlHelper.insertList(odds, BetBdOdds.class, betBdOddsMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

}
