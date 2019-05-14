/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @BdMatchToMatchBd.java   
 * @Package com.loris.old.soccer.transfer.implcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.old.soccer.transfer.impl;

import com.loris.common.util.DateUtil;
import com.loris.common.util.NumberUtil;
import com.loris.old.soccer.bean.BdMatch;
import com.loris.old.soccer.transfer.Transfer;
import com.loris.soccer.model.MatchBd;


/**   
 * @ClassName:  BdMatchToMatchBd.java   
 * @Description: 北单数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class BdMatchToMatchBd implements Transfer<MatchBd, BdMatch>
{
	@Override
	public MatchBd mapping(BdMatch source)
	{
		MatchBd bd = new MatchBd();
		bd.setMid(source.getMid());
		bd.setOrdinary(source.getOrdinary());
		bd.setBdno(source.getIssue());
		bd.setIssue(source.getIssue());
		bd.setMatchtime(source.getMatchDate());
		bd.setClosetime(DateUtil.tryToParseDate(source.getClosetime()));
		bd.setRqnum(NumberUtil.parseInt(source.getRangqiu()));
		bd.setRqopened((bd.getRqnum() > 0 || bd.getRqnum() < 0));
		bd.setWinodds(NumberUtil.parseFloat(source.getWinodds()));
		bd.setDrawodds(NumberUtil.parseFloat(source.getDrawodds()));
		bd.setLoseodds(NumberUtil.parseFloat(source.getLoseodds()));
		bd.setClosetime(DateUtil.tryToParseDate(source.getClosetime()));
		bd.setDelaytime(null);
		return bd;
	}

}
