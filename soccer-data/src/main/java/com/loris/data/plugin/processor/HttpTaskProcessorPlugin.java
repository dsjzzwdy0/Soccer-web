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
package com.loris.data.plugin.processor;

import java.io.IOException;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.fetcher.impl.HttpCommonFetcher;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.page.WebPage;
import com.loris.client.task.Task;
import com.loris.client.task.basic.WebPageTask;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.BasicTaskProcessPlugin;

/**   
 * @ClassName:  League   
 * @Description: 通过HttpCommonClient处理网络数据   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class HttpTaskProcessorPlugin extends BasicTaskProcessPlugin
{
	/** 页面下载 客户端 */
	private HttpCommonFetcher client = null;
	
	/**
	 * Create a new HttpTaskProcessPlugin
	 * @param setting
	 */
	public HttpTaskProcessorPlugin(FetcherSetting setting)
	{
		client = new HttpCommonFetcher(setting);
	}
	
	/**
	 * 执行运务
	 * 
	 * @param context TaskPluginContext 任务插件运行的环境
	 * @throws HostForbiddenException 
	 * @throws  
	 * @see com.loris.client.task.plugin.TaskProcessPlugin#execute(com.loris.client.task.Task)
	 */
	@Override
	public boolean execute(TaskPluginContext context, Task task) throws UrlFetchException, IOException, HostForbiddenException
	{
		if(!(task.getClass().isInstance(WebPageTask.class)))
		{
			WebPage page = ((WebPageTask)task).getPage();
			return client.download(page);
		}
		else
		{			
			return false;
		}
	}
	
	@Override
	public void close()
	{
		client = null;
	}
}
