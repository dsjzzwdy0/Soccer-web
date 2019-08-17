/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @JcMatchToMatchJc.java   
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
import com.loris.old.soccer.bean.JcMatch;
import com.loris.old.soccer.transfer.Transfer;
import com.loris.soccer.model.IssueMatch;

/**   
 * @ClassName:  JcMatchToMatchJc.java   
 * @Description: 竞彩比赛转换  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class JcMatchToMatchJc implements Transfer<IssueMatch, JcMatch>
{
	@Override
	public IssueMatch mapping(JcMatch source)
	{
		IssueMatch jc = new IssueMatch();
		jc.setMid(source.getMid());
		jc.setOrdinary(source.getOrdinary());
		jc.setMatchtime(DateUtil.tryToParseDate(source.getMatchtime()));
		jc.setClosetime(DateUtil.tryToParseDate(source.getClosetime()));
		jc.setIssue(source.getIssue());
		/*jc.setOpened(source.isIsopen());
		if(jc.isOpened())
		{
			jc.setWinodds(NumberUtil.parseFloat(source.getWinodds()));
			jc.setDrawodds(NumberUtil.parseFloat(source.getDrawodds()));
			jc.setLoseodds(NumberUtil.parseFloat(source.getLoseodds()));
		}
		jc.setRqopened(source.isIsrqopen());
		if(jc.isRqopened())
		{
			jc.setRqnum(NumberUtil.parseInt(source.getRangqiu()));
			jc.setRqwinodds(NumberUtil.parseFloat(source.getRqwinodds()));
			jc.setRqdrawodds(NumberUtil.parseFloat(source.getRqdrawodds()));
			jc.setRqloseodds(NumberUtil.parseFloat(source.getRqloseodds()));
		}*/
		return jc;
	}

}
