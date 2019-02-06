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
package com.loris.soccer;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.impl.HttpCommonFetcher;
import com.loris.client.fetcher.setting.SettingFactory;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.fetcher.util.DashBoard;
import com.loris.client.page.WebPage;
import com.loris.client.parser.impl.LinksWebPageParser;
import com.loris.client.task.MainTaskScheduler;
import com.loris.client.task.Task;
import com.loris.client.task.basic.BasicTask;
import com.loris.client.task.basic.WebPageTask;
import com.loris.client.task.plugin.BasicTaskPostProcessPlugin;
import com.loris.client.task.plugin.BasicTaskProcessPlugin;
import com.loris.client.task.plugin.BasicTaskProducePlugin;
import com.loris.client.task.util.TaskQueue;
import com.loris.common.wrapper.TableRecords;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.zgzcw.constant.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.parser.LeagueMainPageParser;
import com.loris.soccer.data.zgzcw.producer.ZgzcwIssueProducePlugin;
import com.loris.soccer.data.zgzcw.util.ZgzcwPageCreator;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Logo;
import com.loris.soccer.processor.HttpTaskProcessor;


/**
 * Hello world!
 *
 */
public class App
{
	private static Logger logger = Logger.getLogger(App.class);

	/** Spring环境 */
	private static ApplicationContext context;

	public static void main(String[] args)
	{
		try
		{
			getApplicationContext();
			// testSetting();
			// testQueue();			
			//testAutowired();
			
			testZgzcwWebPage();

			//testMailThreadScheduler();
			// testContext();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			context = null;
		}
	}
	

	/**
	 * 创建数据主页面
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwWebPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_CENTER, null);				
		HttpTaskProcessor client = (HttpTaskProcessor)context.getBean("httpCommonPlugin");
		if(!client.isInitialized())
		{
			client.initialize();
		}
		
		client.execute(null, new WebPageTask(page));
		
		LeagueMainPageParser parser = new LeagueMainPageParser();
		TableRecords records = parser.parse(page);
		if(records == null)
		{
			logger.info("Parser error.");
		}
		
		List<League> leagues = (List<League>)records.get(SoccerConstants.SOCCER_DATA_LEAGUE_LIST);
		for (League league : leagues)
		{
			logger.info(league.getLid() + ", " + league.getName());
		}
		
		List<Logo> logos = (List<Logo>) records.get(SoccerConstants.SOCCER_DATA_LOGO_LIST);
		for (Logo logo : logos)
		{
			logger.info(logo.getPid() + ": " + logo.getUrl());
		}
	}
	
	/**
	 * 测试
	 * @throws Exception
	 */
	public static void testAutowired() throws Exception
	{
		try(ZgzcwIssueProducePlugin plugin = context.getBean(ZgzcwIssueProducePlugin.class))
		{		
			plugin.initialize();
		}
	}

	/**
	 * 测试线程处理工具
	 * 
	 * @throws Exception
	 */
	public static void testMailThreadScheduler() throws Exception
	{
		MainTaskScheduler scheduler = new MainTaskScheduler();
		scheduler.setMaxActiveTaskThread(1);
		scheduler.setName("即时任务下载器");

		scheduler.getTaskProducer().addTaskProducePlugin(new BasicTaskProducePlugin());
		scheduler.getTaskProcessor().addTaskProcessPlugIn(new BasicTaskProcessPlugin());
		scheduler.getTaskPostProcessor().addTaskPostProcessPlugin(new BasicTaskPostProcessPlugin());

		Thread thread = new Thread(scheduler);
		thread.start();

	}

	/**
	 * 测试队列的数据
	 * 
	 * @throws Exception
	 */
	public static void testQueue() throws Exception
	{
		TaskQueue queue = new TaskQueue();
		int num = 100;

		Task task = null;
		for (int i = 0; i < num; i++)
		{
			task = new BasicTask();
			task.setName("Task[" + i + "]");
			task.setPriority(1.0);

			queue.add(task);
		}

		for (int i = 0; i < num; i++)
		{
			task = queue.poll();
			logger.info(i + " " + task.getName() + ": " + task.getPriority());
		}

		BasicTask b = new BasicTask()
		{
		};

		Class<? extends Task> clazz = BasicTask.class;

		logger.info(clazz.isAssignableFrom(b.getClass()));
		logger.info(b.getClass().isAssignableFrom(clazz));
	}

	/**
	 * 测试应用环境
	 * 
	 * @throws Exception
	 */
	public static void testContext() throws Exception
	{
		SettingFactory factory = (SettingFactory) context.getBean("settingFactory");
		FetcherSetting setting = factory.getFetcherSetting("explorer");
		logger.info(setting.getBrowserVersion().getUserAgent());
		Map<String, String> headers = setting.getHeaders();
		logger.info(headers);
	}

	/**
	 * 测试网络设置工具
	 * 
	 * @throws Exception
	 */
	public static void testSetting() throws Exception
	{
		SettingFactory factory = (SettingFactory) context.getBean("settingFactory");
		FetcherSetting setting = factory.getDefaultFetcherSetting();
		String url = "https://liuinsect.iteye.com/blog/1886237";
		WebPage page = new WebPage(url);

		try (WebFetcher fetcher = new HttpCommonFetcher(setting))
		{
			fetcher.init();
			if (fetcher.download(page))
			{
				// logger.info(page.getContent());
				logger.info("Success to download: " + page.getUrl());
				LinksWebPageParser parser = new LinksWebPageParser();
				TableRecords results = parser.parse(page);
				int i = 1;
				for (String key : results.keySet())
				{
					logger.info(i++ + " " + key + ": " + results.get(key));
				}
			}
		}
		logger.info(DashBoard.print());
	}

	/**
	 * 获得Spring的运行与配置环境
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext()
	{
		/** The Application Context. */
		context = new ClassPathXmlApplicationContext("classpath*:spring-mybatis.xml");
		return context;
	}
}