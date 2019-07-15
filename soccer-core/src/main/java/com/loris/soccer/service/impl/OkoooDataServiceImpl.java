/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooDataServiceImpl.java   
 * @Package com.loris.soccer.service.implcom.loris.soccer.model   
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
import com.loris.soccer.dao.OkoooIssueMatchMapper;
import com.loris.soccer.dao.OkoooLeagueMapper;
import com.loris.soccer.dao.OkoooMatchMapper;
import com.loris.soccer.dao.OkoooOddsOpMapper;
import com.loris.soccer.dao.OkoooOddsYpMapper;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooOddsOp;
import com.loris.soccer.model.OkoooOddsYp;
import com.loris.soccer.service.OkoooDataService;

/**   
 * @ClassName:  OkoooDataServiceImpl.java   
 * @Description: 澳客数据服务类
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("okoooDataService")
public class OkoooDataServiceImpl implements OkoooDataService
{
	@Autowired
	private SqlHelper sqlHelper;
	
	@Autowired
	private OkoooLeagueMapper okoooLeagueMapper;
	
	@Autowired
	private OkoooMatchMapper okoooMatchMapper;
	
	@Autowired
	private OkoooIssueMatchMapper okoooIssueMatchMapper;
	
	@Autowired
	private OkoooOddsOpMapper okoooOddsOpMapper;
	
	@Autowired
	private OkoooOddsYpMapper okoooOddsYpMapper;
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooLeagues(java.util.List)
	 */
	@Override
	public boolean insertOkoooLeagues(List<OkoooLeague> leagues)
	{
		return insertOkoooLeagues(leagues, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooLeagues(java.util.List, boolean)
	 */
	@Override
	public boolean insertOkoooLeagues(List<OkoooLeague> leagues, boolean overwrite)
	{
		ObjectFilter<OkoooLeague> filter = new ObjectFilter<>();		
		return SqlHelper.insertList(leagues, OkoooLeague.class, okoooLeagueMapper, filter,
				SoccerConstants.NAME_FIELD_LID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooMatchs(java.util.List)
	 */
	@Override
	public boolean insertOkoooMatchs(List<OkoooMatch> matchs)
	{
		return insertOkoooMatchs(matchs, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooMatchs(java.util.List, boolean)
	 */
	@Override
	public boolean insertOkoooMatchs(List<OkoooMatch> matchs, boolean overwrite)
	{
		ObjectFilter<OkoooMatch> filter = new ObjectFilter<>();		
		return SqlHelper.insertList(matchs, OkoooMatch.class, okoooMatchMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooIssueMatch(java.util.List)
	 */
	@Override
	public boolean insertOkoooIssueMatch(List<OkoooIssueMatch> issueMatchs)
	{
		return insertOkoooIssueMatch(issueMatchs, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooIssueMatch(java.util.List, boolean)
	 */
	@Override
	public boolean insertOkoooIssueMatch(List<OkoooIssueMatch> issueMatchs, boolean overwrite)
	{
		ObjectFilter<OkoooIssueMatch> filter = new ObjectFilter<>();		
		return SqlHelper.insertList(issueMatchs, OkoooIssueMatch.class, okoooIssueMatchMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooOddsOps(java.util.List)
	 */
	@Override
	public boolean insertOkoooOddsOps(List<OkoooOddsOp> ops)
	{
		return insertOkoooOddsOps(ops, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooOddsOps(java.util.List, boolean)
	 */
	@Override
	public boolean insertOkoooOddsOps(List<OkoooOddsOp> ops, boolean overwrite)
	{
		ObjectFilter<OkoooOddsOp> filter = new ObjectFilter<>();		
		return SqlHelper.insertList(ops, OkoooOddsOp.class, okoooOddsOpMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooOddsYps(java.util.List)
	 */
	@Override
	public boolean insertOkoooOddsYps(List<OkoooOddsYp> yps)
	{
		return insertOkoooOddsYps(yps, false);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#insertOkoooOddsYps(java.util.List, boolean)
	 */
	@Override
	public boolean insertOkoooOddsYps(List<OkoooOddsYp> yps, boolean overwrite)
	{
		ObjectFilter<OkoooOddsYp> filter = new ObjectFilter<>();		
		return SqlHelper.insertList(yps, OkoooOddsYp.class, okoooOddsYpMapper, filter,
				SoccerConstants.NAME_FIELD_MID, sqlHelper, overwrite);
	}
}
