/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @DfzcMatchTrade.java   
 * @Package com.loris.soccer.quant.basecom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.quant.base;

import java.util.List;

import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.quant.MatchTrade;
import com.loris.soccer.quant.type.QuantType;

/**   
 * @ClassName:  DfzcMatchTrade.java   
 * @Description: 东方足彩量化交易数据模型 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class DfzcMatchTrade implements MatchTrade
{

	/**
	 * 按照量化类型，对比赛数据进行排序，并返回排序之后的比赛列表
	 *  (non-Javadoc)
	 * @see com.loris.soccer.quant.MatchTrade#sort(java.util.List, com.loris.soccer.quant.type.QuantType)
	 */
	@Override
	public List<IssueMatch> sort(List<IssueMatch> source, QuantType type)
	{
		return null;
	}
}
