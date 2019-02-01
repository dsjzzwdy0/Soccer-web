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
package com.loris.client.fetcher.impl;

import java.io.IOException;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.page.WebPage;

/**   
 * @ClassName:  FetcherSetting   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public abstract class AbstractWebFetcher implements WebFetcher
{
	/** FetcherSetting 设置信息*/
	protected FetcherSetting setting;

	/** 
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
	}

	/** 
	 * (non-Javadoc)
	 * @see com.loris.client.fetcher.WebFetcher#init()
	 */
	@Override
	public void init() throws IOException
	{
	}

	/** 
	 * (non-Javadoc)
	 * @see com.loris.client.fetcher.WebFetcher#setFetcherSetting(com.loris.client.fetcher.setting.FetcherSetting)
	 */
	@Override
	public void setFetcherSetting(FetcherSetting setting)
	{
		this.setting = setting;		
	}

	/** 
	 * (non-Javadoc)
	 * @see com.loris.client.fetcher.WebFetcher#download(com.loris.client.page.WebPage)
	 */
	@Override
	public abstract boolean download(WebPage page) throws IOException, UrlFetchException, HostForbiddenException;

}
