/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  SchedulerStatusServiceImpl.java   
 * @Package com.loris.client.service.impl   
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
import com.loris.client.dao.SchedulerStatusMapper;
import com.loris.client.model.SchedulerStatus;
import com.loris.client.service.SchedulerStatusService;

/**   
 * @ClassName: SchedulerStatusServiceImpl   
 * @Description: 数据服务  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("schedulerStatusService")
public class SchedulerStatusServiceImpl extends ServiceImpl<SchedulerStatusMapper, SchedulerStatus> implements SchedulerStatusService
{
	@Override
	public boolean save(SchedulerStatus status)
	{
		if (StringUtils.isNotBlank(status.getId()))
		{
			return baseMapper.updateById(status) > 0;
		}
		else
		{
			return baseMapper.insert(status) > 0;
		}
	}
}
