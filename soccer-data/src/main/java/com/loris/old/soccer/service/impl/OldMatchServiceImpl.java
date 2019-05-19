/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  MatchServiceImpl.java   
 * @Package com.loris.soccer.old.service.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.old.soccer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.old.soccer.bean.OldMatch;
import com.loris.old.soccer.dao.OldMatchMapper;
import com.loris.old.soccer.service.OldMatchService;

/**   
 * @ClassName:  MatchServiceImpl    
 * @Description: 服务类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("oldMatchService")
public class OldMatchServiceImpl extends ServiceImpl<OldMatchMapper, OldMatch> implements OldMatchService
{
	@Autowired
	OldMatchMapper matchMapper;
	
	/**
	 *  (non-Javadoc)
	 * @see com.loris.old.soccer.service.OldMatchService#getMatch(java.lang.String)
	 */
	@Override
	public OldMatch getMatch(String mid)
	{
		QueryWrapper<OldMatch> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("mid", mid);
		return matchMapper.selectOne(queryWrapper);
	}

}
