/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebPageService.java   
 * @Package com.loris.client.page.service   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月31日 下午8:54:26   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.client.model.WebPage;

/**   
 * @ClassName:  WebPageService   
 * @Description: 数据服务的接口 
 * @author: 东方足彩
 * @date:   2019年1月31日 下午8:54:26   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface WebPageService extends IService<WebPage>
{
	/**
	 * 按照关键字查找数据的页面
	 * @param pageid 网页唯一标识
	 * @return 网页数据
	 */
	List<WebPage> selectWebPage(String pageid);
}
