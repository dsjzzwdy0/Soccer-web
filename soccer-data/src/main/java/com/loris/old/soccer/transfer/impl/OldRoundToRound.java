/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @RoundMapper.java   
 * @Package com.loris.old.soccer.transfer.implcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.old.soccer.transfer.impl;

import com.loris.old.soccer.bean.OldRound;
import com.loris.old.soccer.transfer.Transfer;
import com.loris.soccer.model.Round;

/**   
 * @ClassName:  RoundMapper.java   
 * @Description: 轮次数据  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OldRoundToRound implements Transfer<Round, OldRound>
{

	@Override
	public Round mapping(OldRound source)
	{
		Round round = new Round();
		round.setLid(source.getLid());
		round.setSeason(source.getSeason());
		round.setRound(source.getRid());
		return round;
	}

}
