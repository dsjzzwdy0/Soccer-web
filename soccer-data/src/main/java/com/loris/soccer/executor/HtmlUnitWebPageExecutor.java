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
package com.loris.soccer.executor;

import java.io.IOException;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.fetcher.impl.HtmlUnitFetcher;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.model.WebPage;
import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.BasicTaskProcessPlugin;

/**   
 * @ClassName:  League   
 * @Description: HtmlUnit客户端任务处理器  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class HtmlUnitWebPageExecutor extends BasicTaskProcessPlugin
{
	/** HtmlUnitFetcher Client. */
	private HtmlUnitFetcher client = null;
		
	/**
	 * Create a new instance of HtmlUnitTaskProcessPlugin
	 * @param setting
	 * @param initPage
	 */
	public HtmlUnitWebPageExecutor(FetcherSetting setting, WebPage initPage)
	{
		client = new HtmlUnitFetcher(setting, initPage);
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
		if(!(task.getClass().isInstance(WebPage.class)))
		{
			WebPage page = (WebPage)task;
			return client.download(page);
		}
		else
		{			
			return false;
		}
	}
	
	/**
	 * 初始化
	 */
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		client.init();
		super.initialize(context);
	}

	/**
	 * 关闭数据接口
	 */
	@Override
	public void close()
	{
		client.close();
		super.close();
	}
}
