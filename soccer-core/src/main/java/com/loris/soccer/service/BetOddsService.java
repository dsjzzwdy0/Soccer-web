/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  BetOddsService.java   
 * @Package com.loris.soccer.service   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service;

import java.util.List;

import com.loris.soccer.model.BetBdOdds;
import com.loris.soccer.model.BetJcOdds;

/**   
 * @ClassName:  BetOddsService    
 * @Description: 投注信息的服务器类   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface BetOddsService
{
	/**
	 * 插入竞彩投注信息数据列表
	 * @param odds 赔率数据
	 * @return 是否成功的标志
	 */
	boolean insertBetJcOdds(List<BetJcOdds> odds);
	
	/**
	 * 插入竞彩投注信息数据列表
	 * @param odds 赔率数据
	 * @param overwrite 是否覆盖数据
	 * @return 是否成功的标志
	 */
	boolean insertBetJcOdds(List<BetJcOdds> odds, boolean overwrite);
	
	/**
	 * 插入北单投注信息数据列表
	 * @param odds 赔率数据
	 * @return 是否成功的标志
	 */
	boolean insertBetBdOdds(List<BetBdOdds> odds);
	
	/**
	 * 插入北单投注信息数据列表
	 * @param odds 赔率数据
	 * @param overwrite
	 * @return 是否成功的标志
	 */
	boolean insertBetBdOdds(List<BetBdOdds> odds, boolean overwrite);
}
