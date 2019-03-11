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
package com.loris.soccer.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.soccer.model.MatchBd;

/**   
 * @ClassName:  MatchBdService   
 * @Description: 北单比赛数据的服务类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface MatchBdService extends IService<MatchBd>
{
	/**
	 * 添加北单比赛的数据
	 * @param matchBds
	 * @return
	 */
	boolean insert(List<MatchBd> matchBds);
	
	/**
	 * 插入北单数据
	 * @param matchBds
	 * @param overwrite
	 * @return
	 */
	boolean insert(List<MatchBd> matchBds, boolean overwrite);
}
