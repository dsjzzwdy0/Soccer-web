/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsOpServiceImpl.java   
 * @Package com.loris.soccer.service.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:33:53   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.soccer.dao.OddsOpMapper;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.service.OddsOpService;

/**   
 * @ClassName:  OddsOpServiceImpl   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:33:53   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("oddsOpService")
public class OddsOpServiceImpl extends ServiceImpl<OddsOpMapper, OddsOp> implements OddsOpService
{
	/**
	 * 通过比赛编号获得欧赔数据列表
	 * @param mid 比赛编号
	 * @return 欧赔数据列表
	 */
	public List<OddsOp> selectOddsOp(String mid)
	{
		QueryWrapper<OddsOp> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mid", mid);
		return baseMapper.selectList(queryWrapper);
	}
	
	/**
	 * 通过比赛编号与博彩公司的编号获得欧赔数据列表
	 * @param mid 比赛编号
	 * @param corpid 博彩公司编号
	 * @return 欧赔数据列且
	 */
	public List<OddsOp> selectOddsOp(String mid, String corpid)
	{
		QueryWrapper<OddsOp> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mid", mid).eq("corpid", corpid);
		return baseMapper.selectList(queryWrapper);
	}
}
