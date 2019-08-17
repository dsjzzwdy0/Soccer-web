/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  YpMapper.java   
 * @Package com.loris.old.soccer.transfer.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.old.soccer.transfer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.util.DateUtil;
import com.loris.old.soccer.bean.Yp;
import com.loris.old.soccer.transfer.Transfer;
import com.loris.soccer.dict.HandicapDict;
import com.loris.soccer.model.OddsYp;

/**   
 * @ClassName:  YpMapper    
 * @Description: 原始亚盘数据转换为新的亚盘数据 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class YpToOddsYp implements Transfer<List<OddsYp>, Yp>
{
	/**
	 *  (non-Javadoc)
	 * @see com.loris.old.soccer.transfer.Transfer#mapping(java.lang.Object)
	 */
	@Override
	public List<OddsYp> mapping(Yp source)
	{
		List<OddsYp> yps = new ArrayList<>();
		if(StringUtils.isNotEmpty(source.getFirsttime()))
		{
			OddsYp first = new OddsYp();
			first.setMid(source.getMid());
			first.setCorpid(source.getGid());
			//first.setCorpname(source.getGname());
			first.setOpentime(DateUtil.tryToParseDate(source.getFirsttime()));
			first.setWinodds(source.getFirstwinodds());
			first.setHandicap(HandicapDict.getHandicapValue(source.getFirsthandicap()));
			first.setLoseodds(source.getFirstloseodds());
			first.setWinkelly(source.getWinkelly());
			first.setLosekelly(source.getLosekelly());
			first.setWinprob(source.getWinprob());
			first.setLoseprob(source.getLoseprob());
			first.setLossratio(source.getLossratio());
			yps.add(first);
		}

		OddsYp last = new OddsYp();
		last.setMid(source.getMid());
		last.setCorpid(source.getGid());
		//last.setCorpname(source.getGname());
		last.setOpentime(new Date(source.getLastTimeValue()));
		last.setWinodds(source.getWinodds());
		last.setHandicap(HandicapDict.getHandicapValue(source.getHandicap()));
		last.setLoseodds(source.getLoseodds());
		last.setWinkelly(source.getWinkelly());
		last.setLosekelly(source.getLosekelly());
		last.setWinprob(source.getWinprob());
		last.setLoseprob(source.getLoseprob());
		last.setLossratio(source.getLossratio());
				
		yps.add(last);
		return yps;
	}
}
