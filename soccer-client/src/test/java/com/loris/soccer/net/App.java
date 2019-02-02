package com.loris.soccer.net;

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
import com.loris.client.parser.WebPageResults;
import com.loris.client.parser.impl.LinksWebPageParser;
import com.loris.client.task.Task;
import com.loris.client.task.impl.BasicTask;
import com.loris.client.task.util.TaskQueue;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = Logger.getLogger(App.class);
	
	private static ApplicationContext context;
	
    public static void main( String[] args )
    {
    	try
    	{
    		getApplicationContext();
    		
    		//testSetting();  
    		
    		testQueue();
    		//testContext();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally {
			context = null;
		}
    }
    
    public static void testQueue() throws Exception
    {
    	TaskQueue queue = new TaskQueue();
    	int num = 100;
    	
    	Task task = null;
    	for(int i = 0; i < num; i ++)
    	{
	    	task = new BasicTask();
	    	task.setName("Task[" + i + "]");
	    	task.setPriority( 1.0);
	    	
	    	queue.add(task);
    	}
    	
    	for(int i = 0; i < num; i ++)
    	{
    		task = queue.poll();
    		logger.info(i + " " + task.getName() + ": " + task.getPriority());
    	}
    	
    	BasicTask b = new BasicTask() {
    		@Override
    		public void execute()
    		{
    			logger.info("Test");
    		}
    	};
    	
    	Class<? extends Task> clazz = BasicTask.class;
    	
    	logger.info(clazz.isAssignableFrom(b.getClass()));
    	logger.info(b.getClass().isAssignableFrom(clazz));    
    }
    
    
    public static void testContext() throws Exception
    {
    	SettingFactory factory = (SettingFactory)context.getBean("settingFactory");
    	FetcherSetting setting = factory.getFetcherSetting("explorer");
    	logger.info(setting.getBrowserVersion().getUserAgent());    	
    	Map<String, String> headers = setting.getHeaders();
    	logger.info(headers);
    }
    
    /**
     * 测试网络设置工具
     * @throws Exception
     */
    public static void testSetting() throws Exception
    {
    	SettingFactory factory = (SettingFactory)context.getBean("settingFactory");
    	FetcherSetting setting = factory.getDefaultFetcherSetting();
    	String url = "https://liuinsect.iteye.com/blog/1886237";
    	WebPage page = new WebPage(url);
    	
    	try(WebFetcher fetcher = new HttpCommonFetcher(setting))
    	{
    		fetcher.init();    		
	    	if(fetcher.download(page))
	    	{
	    		//logger.info(page.getContent());
	    		logger.info("Success to download: " + page.getUrl());
	    		LinksWebPageParser parser = new LinksWebPageParser();
	    		WebPageResults results = parser.parse(page);
	    		int i = 1;
	    		for (String key : results.keySet())
				{
					logger.info(i +++ " " + key + ": " + results.get(key));
				}
	    	}
    	}    	
    	logger.info(DashBoard.print());    	
    }
    
    
    /**
	 * 获得Spring的运行与配置环境
	 * @return
	 */
	public static ApplicationContext getApplicationContext()
	{
		/** The Application Context. */
		context = new ClassPathXmlApplicationContext("classpath*:spring-settings.xml");
		return context;
	}
}
