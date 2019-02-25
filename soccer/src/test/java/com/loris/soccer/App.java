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

import java.util.LinkedHashMap;
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
import com.loris.client.model.SchedulerInfo;
import com.loris.client.model.WebPage;
import com.loris.client.parser.impl.LinksWebPageParser;
import com.loris.client.scheduler.TaskScheduler;
import com.loris.client.scheduler.Scheduler;
import com.loris.client.scheduler.SchedulerFactory;
import com.loris.client.task.Task;
import com.loris.client.task.basic.BasicTask;
import com.loris.client.task.basic.WebPageTask;
import com.loris.client.task.plugin.BasicTaskPostProcessPlugin;
import com.loris.client.task.plugin.BasicTaskProcessPlugin;
import com.loris.client.task.plugin.BasicWebPageTaskProducePlugin;
import com.loris.client.task.util.TaskQueue;
import com.loris.common.model.TableRecords;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Logo;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchBd;
import com.loris.soccer.model.MatchJc;
import com.loris.soccer.model.OddsNum;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsScore;
import com.loris.soccer.model.Team;
import com.loris.soccer.plugin.zgzcw.ZgzcwIssueProducePlugin;
import com.loris.soccer.plugin.zgzcw.parser.CenterPageParser;
import com.loris.soccer.plugin.zgzcw.parser.CupWebPageParser;
import com.loris.soccer.plugin.zgzcw.parser.LotteryBdWebPageParser;
import com.loris.soccer.plugin.zgzcw.parser.LotteryJcScoreWebPageParser;
import com.loris.soccer.plugin.zgzcw.parser.LotteryJcWebPageParser;
import com.loris.soccer.plugin.zgzcw.parser.OddsNumWebPageParser;
import com.loris.soccer.plugin.zgzcw.parser.OddsOpWebPageParser;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwConstants;
import com.loris.soccer.plugin.zgzcw.util.ZgzcwPageCreator;
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
			//testZgzcwWebPage();			
			//testZgzcwOpWebPage();
			//testZgzcwNumWebPage();
			//testZgzcwLeagueWebPage();
			//testBdWebPage();
			
			//testJcWebPage();
			
			testSchedulerInfo();

			//testMainThreadScheduler();
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
	
	public static void testSchedulerInfo() throws Exception
	{
		SchedulerInfo info = new SchedulerInfo();
		info.setSid("101-190");
		info.setName("数据下载的信息");
		info.setIntervaltime(4000);
		info.setMaxActiveTaskThread(3);
		info.setRandTimeSeed(200);
		info.setType("zgzcw.downloader");
		//info.addPlugin("bean:httpCommonPlugin");
		info.addPlugin(SchedulerInfo.PLUGIN_CLASS, ZgzcwIssueProducePlugin.class.getName());
		
		Scheduler scheduler = SchedulerFactory.createTaskScheduler(info);
		logger.info(scheduler.getName());
		SchedulerFactory.startTaskScheduler(scheduler);
	}
	
	/**
	 * 测试比分数据页面
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testScoreWebPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_SCORE_JC);
		if(!getWebPage(page))
		{
			return;
		}
		
		LotteryJcScoreWebPageParser parser = new LotteryJcScoreWebPageParser();
		TableRecords records = parser.parse(page);
		if(records == null)
		{
			logger.info("Parser error.");
		}
		List<OddsScore> scores = (List<OddsScore>)records.get(SoccerConstants.SOCCER_DATA_ODDS_NUM);
		int i = 1;
		for (OddsScore oddsScore : scores)
		{
			logger.info(i +++ ": " + oddsScore.getOrdinary() + " " + oddsScore.getMid() + " " + oddsScore.getOddsvalue());
		}
	}
	
	/**
	 * 测试竞彩页面
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testJcWebPage() throws Exception
	{		
		Map<String, String> params = new LinkedHashMap<>();
		params.put(SoccerConstants.NAME_FIELD_ISSUE, "2019-02-02");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LOTTERY_JC, params);
		
		if(!getWebPage(page))
		{
			return;
		}
		
		LotteryJcWebPageParser parser = new LotteryJcWebPageParser();
		TableRecords records = parser.parse(page);
		if(records == null)
		{
			logger.info("Parser error.");
		}
		
		List<MatchJc> matchJcs = (List<MatchJc>)records.get(SoccerConstants.SOCCER_DATA_MATCH_JC_LIST);
		
		int i = 1;
		for (MatchJc matchJc : matchJcs)
		{
			logger.info(i +++ ": " + matchJc);
		}
	}
	
	/**
	 * 测试北单页面
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testBdWebPage() throws Exception
	{		
		Map<String, String> params = new LinkedHashMap<>();
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LOTTERY_BD, params);
		
		if(!getWebPage(page))
		{
			return;
		}
		
		LotteryBdWebPageParser parser = new LotteryBdWebPageParser();
		TableRecords records = parser.parse(page);
		if(records == null)
		{
			logger.info("Parser error.");
		}
		
		List<MatchBd> matchBds = (List<MatchBd>)records.get(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST);
		
		int i = 1;
		for (MatchBd matchBd : matchBds)
		{
			logger.info(i +++ ": " + matchBd);
		}
	}
	
	/**
	 * 创建数据主页面
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwLeagueWebPage() throws Exception
	{		
		Map<String, String> params = new LinkedHashMap<>();
		params.put("lid", "83");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LEAGUE_CUP, params);	

		if(!getWebPage(page))
		{
			return;
		}
		
		//logger.info(page.getContent());
		
		CupWebPageParser parser = new CupWebPageParser();
		TableRecords records = parser.parse(page);
		if(records == null)
		{
			logger.info("Parser error.");
		}
		
		int i = 1;
		List<Match> matchs = (List<Match> )records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
		for (Match match : matchs)
		{
			logger.info(i +++ ": " + match.getMid() + "(" + match.getMatchtime() + ")");
		}
		
		List<Team> teams = (List<Team>)records.get(SoccerConstants.SOCCER_DATA_TEAM_LIST);
		i = 1;
		for (Team team : teams)
		{
			logger.info(i +++ ": " + team.getTid() + ", " + team.getName());
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

		if(!getWebPage(page))
		{
			return;
		}
		
		CenterPageParser parser = new CenterPageParser();
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
	 * 创建数据主页面
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwOpWebPage() throws Exception
	{
		Map<String, String> params = new LinkedHashMap<>();
		params.put("mid", "2432303");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params);	

		if(!getWebPage(page))
		{
			return;
		}
		
		//logger.info(page.getContent());
		
		OddsOpWebPageParser parser = new OddsOpWebPageParser();
		TableRecords records = parser.parse(page);
		if(records == null)
		{
			logger.info("Parser error.");
		}
		
		int i = 1;
		List<OddsOp> ops = (List<OddsOp> )records.get(SoccerConstants.SOCCER_DATA_OP_LIST);
		for (OddsOp op : ops)
		{
			logger.info(i +++ ": " + op.getCorpid() + "(" + op.getWinodds() + ", " 
					+ op.getDrawodds() + ", " + op.getLoseodds() + ")" + op.getOpentime());
		}
	}
	
	/**
	 * 创建数据主页面
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwNumWebPage() throws Exception
	{
		Map<String, String> params = new LinkedHashMap<>();
		params.put("mid", "2437113");
		params.put("matchtime", "2019-02-02 14:35:00");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_NUM, params);	

		if(!getWebPage(page))
		{
			return;
		}
		
		//logger.info(page.getContent());
		
		OddsNumWebPageParser parser = new OddsNumWebPageParser();
		TableRecords records = parser.parse(page);
		if(records == null)
		{
			logger.info("Parser error.");
		}
		
		int i = 1;
		List<OddsNum> odds = (List<OddsNum> )records.get(SoccerConstants.SOCCER_DATA_NUM_LIST);
		for (OddsNum odd : odds)
		{
			logger.info(i +++ ": " + odd.getCorpid() + "(" + odd.getWinodds() + ", " 
					+ odd.getGoalnum() + ", " + odd.getLoseodds() + ")" + odd.getOpentime());
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
			plugin.initialize(null);
		}
	}

	/**
	 * 测试线程处理工具
	 * 
	 * @throws Exception
	 */
	public static void testMainThreadScheduler() throws Exception
	{
		TaskScheduler scheduler = new TaskScheduler();
		scheduler.setMaxActiveTaskThread(1);
		//scheduler.setName("即时任务下载器");

		scheduler.addTaskPlugin(new BasicWebPageTaskProducePlugin());
		scheduler.addTaskPlugin(new BasicTaskProcessPlugin());
		scheduler.addTaskPlugin(new BasicTaskPostProcessPlugin());

		SchedulerFactory.startTaskScheduler(scheduler);
		
		//Thread thread = new Thread(scheduler);
		//thread.start();
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
	
	private static boolean getWebPage(WebPage page) throws Exception
	{
		try(HttpTaskProcessor client = (HttpTaskProcessor)context.getBean("httpCommonPlugin"))
		{
			if(!client.isInitialized())
			{
				client.initialize(null);
			}
			
			if(!client.execute(null, new WebPageTask(page)))
			{
				logger.info("Error when downloading: " + page.getUrl());
				return false;
			}
			return true;
		}		
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