/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooBasePlugin.java   
 * @Package com.loris.soccer.datacom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.data;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.loris.client.exception.HostForbiddenException;
import com.loris.client.exception.UrlFetchException;
import com.loris.client.exception.WebParserException;
import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.impl.HtmlUnitFetcher;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.task.Task;
import com.loris.client.task.context.TaskPluginContext;
import com.loris.client.task.plugin.BasicWebPageTaskPlugin;
import com.loris.client.task.plugin.TaskProcessPlugin;
import com.loris.client.task.plugin.TaskProducePlugin;
import com.loris.common.context.ApplicationContextHelper;
import com.loris.soccer.service.OkoooDataService;

/**   
 * @ClassName:  OkoooBasePlugin.java   
 * @Description: 澳 客网数据下载接口
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Component
public class OkoooBasePlugin extends BasicWebPageTaskPlugin implements TaskProducePlugin, TaskProcessPlugin
{
	/** 网络下载器 */
	protected WebFetcher webPagefetcher = null;
	
	/** 澳客数据服务器 */
	@Autowired
	protected OkoooDataService okoooDataService;
	
	/**
	 * @param name
	 */
	public OkoooBasePlugin(String name)
	{
		super(name);
	}

	/**
	 * 初始化任务产生器
	 * 
	 * @param context 插件任务运行环境
	 * @throws IOException 在任务产生过程中出现异常
	 */
	@Override
	public void initialize(TaskPluginContext context) throws IOException
	{
		if(initialized)
		{
			return;
		}
		super.initialize(context);
		
		FetcherSetting setting = ApplicationContextHelper.getBean("okoooSetting");
		webPagefetcher = new HtmlUnitFetcher(setting);
		try
		{
			webPagefetcher.init();
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Error occured when HttpCommonFetcher init().");
		}
		
		//初始化过滤器
		//initFilter(context);
		initialized = true;
	}

	/* (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProcessPlugin#execute(com.loris.client.task.context.TaskPluginContext, com.loris.client.task.Task)
	 */
	@Override
	public boolean execute(TaskPluginContext context, Task task)
			throws UrlFetchException, WebParserException, IOException, HostForbiddenException, SQLException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.loris.client.task.plugin.TaskProducePlugin#produce(com.loris.client.task.context.TaskPluginContext)
	 */
	@Override
	public void produce(TaskPluginContext context) throws IOException, SQLException
	{
	}
}
