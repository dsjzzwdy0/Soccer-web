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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.common.service.SqlHelper;
import com.loris.common.util.ArraysUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.dao.MatchBdMapper;
import com.loris.soccer.filter.MatchItemFilter;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.service.MatchBdService;

/**   
 * @ClassName:  League   
 * @Description: 北单比赛数据服务 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("matchBdService")
public class MatchBdServiceImpl extends ServiceImpl<MatchBdMapper, MatchBd> implements MatchBdService
{
	private static Logger logger = Logger.getLogger(MatchBdServiceImpl.class);
	
	@Autowired
	SqlHelper helper;
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchBdService#insert(java.util.List)
	 */
	@Override
	public boolean insert(List<MatchBd> matchBds)
	{
		return insert(matchBds, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.MatchBdService#insert(java.util.List, boolean)
	 */
	@Override
	public boolean insert(List<MatchBd> matchBds, boolean overwrite)
	{
		List<String> mids = ArraysUtil.getObjectFieldValue(matchBds, MatchBd.class, SoccerConstants.NAME_FIELD_MID);
		if (overwrite)
		{
			baseMapper.delete(new QueryWrapper<MatchBd>().in(SoccerConstants.NAME_FIELD_MID, mids));
		}
		else
		{
			List<MatchBd> existMatchs = list(new QueryWrapper<MatchBd>().in(SoccerConstants.NAME_FIELD_MID, mids));
			List<MatchBd> destMatchs = new ArrayList<>();

			MatchItemFilter<MatchBd> filter = new MatchItemFilter<>();
			for (MatchBd match : matchBds)
			{
				filter.setValue(match);
				if (!ArraysUtil.hasSameObject(existMatchs, filter))
				{
					destMatchs.add(match);
				}
			}

			if (destMatchs.size() == 0)
			{
				logger.warn("No match need to be updated.");
				return true;
			}
			matchBds = destMatchs;
		}
		try
		{
			return helper.insertBatch(matchBds);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
}
