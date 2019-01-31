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
package com.loris.client.page.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.client.page.WebPage;
import com.loris.client.page.dao.WebPageMapper;
import com.loris.client.page.service.WebPageService;

/**   
 * @ClassName:  WebPageServiceImpl   
 * @Description: TODO(这里用一句话描述这个类的作用)   
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
	public List<WebPage> selectWebPage(String pageid)
	{
		QueryWrapper<WebPage> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("PAGEID", pageid);
		return baseMapper.selectList(queryWrapper);
	}
}
