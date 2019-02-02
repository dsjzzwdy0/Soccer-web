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
package com.loris.client.task.basic;

import com.loris.client.page.WebPage;

/**   
 * @ClassName:  League   
 * @Description: 网络页面处理的任务 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class WebPageTask extends AbstractTask
{
	/** 网页下载任务 */
	WebPage page = null;
	
	/**
	 * Create a new instance of WebPageTask.
	 * @param page The WebPage.
	 */
	public WebPageTask(WebPage page)
	{
		this.page = page;
	}

	public WebPage getPage()
	{
		return page;
	}

	public void setPage(WebPage page)
	{
		this.page = page;
	}
}
