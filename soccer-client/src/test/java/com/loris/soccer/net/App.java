package com.loris.soccer.net;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.impl.HtmlUnitFetcher;
import com.loris.client.fetcher.setting.SettingFactory;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.fetcher.util.DashBoard;
import com.loris.client.page.WebPage;

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
    		testContext();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally {
			context = null;
		}
    }
    
    public static void testContext() throws Exception
    {
    	SettingFactory factory = (SettingFactory)context.getBean("settingFactory");
    	logger.info("Bean has been initialized " + (factory != null));
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
    	
    	try(WebFetcher fetcher = new HtmlUnitFetcher(setting))
    	{
    		fetcher.init();    		
	    	if(fetcher.download(page))
	    	{
	    		//logger.info(page.getContent());
	    		logger.info("Success to download: " + page.getUrl());
	    	}
    	}
    	
    	logger.info(DashBoard.print());    	
    	//logger.info(BrowserVersion.FIREFOX_60.getUserAgent());
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
