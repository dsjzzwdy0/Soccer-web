/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MatchMapper.java   
 * @Package com.loris.old.soccer.transfer.implcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.old.soccer.transfer.impl;

import com.loris.old.soccer.bean.OldMatch;
import com.loris.old.soccer.transfer.Transfer;
import com.loris.soccer.model.Match;

/**   
 * @ClassName:  MatchMapper.java   
 * @Description: 比赛数据转换  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OldMatchToMatch implements Transfer<Match, OldMatch>
{
	@Override
	public Match mapping(OldMatch source)
	{
		Match match = new Match(source.getMid());
		match.setMatchtime(source.getMatchDate());
		match.setLid(source.getLid());
		match.setHomeid(source.getHomeid());
		match.setClientid(source.getClientid());
		match.setSeason(match.getSeason());
		match.setRound(source.getRound());
		return match;
	}
}
