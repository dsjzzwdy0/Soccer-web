/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchService.java   
 * @Package com.loris.soccer.old.service   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.old.soccer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.old.soccer.bean.OldMatch;

/**   
 * @ClassName:  MatchService    
 * @Description: 数据服务   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface OldMatchService extends IService<OldMatch>
{
	/**
	 * 查询某一场比赛
	 * @param mid
	 * @return
	 */
	OldMatch getMatch(String mid);
}
