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
import com.loris.soccer.model.Match;

/**   
 * @ClassName:  League   
 * @Description: 比赛数据服务接口
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface MatchService extends IService<Match>
{
	/**
	 * 插入比赛数据记录
	 * @param matchs 比赛列表
	 * @return 是否插入数据记录成功的标志
	 */
	boolean insertMatchs(List<Match> matchs);
	
	/**
	 * 插入比赛数据记录
	 * @param matchs 比赛列表
	 * @param overwrite 是否覆盖原有的数据记录
	 * @return 是否插入数据记录成功的标志
	 */
	boolean insertMatchs(List<Match> matchs, boolean overwrite);
}
