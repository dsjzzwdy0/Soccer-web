/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @QuantServiceImpl.java   
 * @Package com.loris.soccer.stat.service.implcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loris.common.filter.ObjectFilter;
import com.loris.common.service.SqlHelper;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.stat.dao.CorpFreqMapper;
import com.loris.soccer.stat.model.CorpFreq;
import com.loris.soccer.stat.service.QuantService;

/**   
 * @ClassName:  QuantServiceImpl.java   
 * @Description: 量化计算服务类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("quantService")
public class QuantServiceImpl implements QuantService
{
	@Autowired
	private SqlHelper sqlHelper;
	
	@Autowired
	private CorpFreqMapper corpFreqMapper;

	/**
	 * 
	 *  (non-Javadoc)
	 * @see com.loris.soccer.stat.service.QuantService#insertCorpFreqs(java.util.List)
	 */
	@Override
	public boolean insertCorpFreqs(List<CorpFreq> freqs)
	{
		ObjectFilter<CorpFreq> filter = new ObjectFilter<>();		
		return SqlHelper.insertList(freqs, CorpFreq.class, corpFreqMapper, filter,
				SoccerConstants.NAME_FIELD_CORPID, sqlHelper, true);		
	}

}
