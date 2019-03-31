/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  SchedulerInfoServiceImpl.java   
 * @Package com.loris.client.model.service.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.client.dao.SchedulerInfoMapper;
import com.loris.client.model.SchedulerInfo;
import com.loris.client.service.SchedulerInfoService;

/**   
 * @ClassName:  SchedulerInfoServiceImpl    
 * @Description: 调度管理器服务实现类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("schedulerInfoService")
public class SchedulerInfoServiceImpl extends ServiceImpl<SchedulerInfoMapper, SchedulerInfo> implements SchedulerInfoService
{
	@Override
	public boolean save(SchedulerInfo schedulerInfo)
	{
		if (StringUtils.isNotBlank(schedulerInfo.getId()))
		{
			return baseMapper.updateById(schedulerInfo) > 0;
		}
		else
		{
			schedulerInfo.create();
			return baseMapper.insert(schedulerInfo) > 0;
		}
	}
}
