/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MatchTrade.java   
 * @Package com.loris.soccer.quantcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.quant;

import java.util.List;

import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.quant.type.QuantType;

/**   
 * @ClassName:  MatchTrade.java   
 * @Description: 比赛交易数据计算  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface MatchTrade
{
	/**
	 * 按照一定的要求进行排序计算
	 * @param source 源数据
	 * @param type 计算的类型
	 * @return 排序之后的比赛数据
	 */
	List<IssueMatch> sort(List<IssueMatch> source, QuantType type);
}
