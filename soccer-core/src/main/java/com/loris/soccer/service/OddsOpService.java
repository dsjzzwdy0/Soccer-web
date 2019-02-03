/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsOpService.java   
 * @Package com.loris.soccer   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:33:06   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.soccer.model.OddsOp;

/**   
 * @ClassName:  OddsOpService   
 * @Description: 欧赔数据业务查询服务 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:33:06   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface OddsOpService extends IService<OddsOp>
{
	/**
	 * 通过比赛编号获得欧赔数据列表
	 * @param mid 比赛编号
	 * @return 欧赔数据列表
	 */
	List<OddsOp> selectOddsOp(String mid);
	
	/**
	 * 通过比赛编号与博彩公司的编号获得欧赔数据列表
	 * @param mid 比赛编号
	 * @param corpid 博彩公司编号
	 * @return 欧赔数据列且
	 */
	List<OddsOp> selectOddsOp(String mid, String corpid);
	
	/**
	 * 插入欧赔数据列表
	 * @param ops 欧赔数据列表
	 * @return 是否成功的标志
	 */
	boolean insertOddsOp(List<OddsOp> ops);
}
