/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebPageServiceImpl.java   
 * @Package com.loris.client.page.service.impl   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月31日 下午8:55:23   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.client.dao.WebPageMapper;
import com.loris.client.model.WebPage;
import com.loris.client.service.WebPageService;
import com.loris.common.util.ToolUtil;

/**   
 * @ClassName:  WebPageServiceImpl   
 * @Description: 数据页面的服务类 
 * @author: 东方足彩
 * @date:   2019年1月31日 下午8:55:23   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("webPageService")
public class WebPageServiceImpl extends ServiceImpl<WebPageMapper, WebPage> implements WebPageService
{
	/**
	 * 按照关键字查找数据的页面
	 * @param pageid 网页唯一标识
	 * @return 网页数据
	 */
	@Override
	public List<WebPage> selectWebPage(String pageid)
	{
		QueryWrapper<WebPage> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pageid", pageid);
		return baseMapper.selectList(queryWrapper);
	}

	/**
	 *  (non-Javadoc)
	 * @see com.loris.client.service.WebPageService#getWebPage(java.lang.String, java.util.List, java.util.Date, java.util.Date)
	 */
	@Override
	public List<WebPage> getWebPage(String source, List<String> types, Date start, Date end)
	{
		QueryWrapper<WebPage> queryWrapper = new QueryWrapper<>();
		
		if(StringUtils.isNotBlank(source))
		{
			queryWrapper.like("source", source);
		}
		if(ToolUtil.isNotEmpty(start))
		{
			queryWrapper.gt("loadtime", start);
		}
		if(ToolUtil.isNotEmpty(end))
		{
			queryWrapper.lt("loadtime", end);
		}
		//queryWrapper.and(wrapper->wrapper.eq("type", "yp").or().eq("type", "league"));
		if(types != null && types.size() > 0)
		{
			QueryWrapper<WebPage> q1 = new QueryWrapper<>();
			
			//queryWrapper.and(wrapper-> wrapper.);
			for (int i = 0; i < types.size(); i ++)
			{
				if(i > 0)
				{
					q1.or();
				}
				q1.like("type", types.get(i));
			}
			queryWrapper.and(wrapper-> q1);
		}
		
		System.out.println(queryWrapper.getSqlSegment());
		return baseMapper.selectList(queryWrapper);
	}
}
