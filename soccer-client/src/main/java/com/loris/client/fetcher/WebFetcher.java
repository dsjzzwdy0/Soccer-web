/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  WebFetcher.java   
 * @Package com.loris.net   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月29日 下午5:57:19   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.client.fetcher;

import java.io.Closeable;

import com.loris.client.exception.UrlFetchException;
import com.loris.client.page.WebPage;

/**   
 * @ClassName:  WebFetcher   
 * @Description: 数据页面的下载工具类  
 * @author: 东方足彩
 * @date:   2019年1月29日 下午5:57:19   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface WebFetcher extends Closeable
{
	/**
	 * 初始化网页数据下载器
	 */
	void init();
	
	/**
	 * 下载数据页面
	 * @param page 页面数据
	 * @return 下载是否成功的标志
	 * @throws UrlFetchException 下载过程中出现的异常
	 */
	boolean download(WebPage page) throws UrlFetchException;
}
