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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.impl.HttpCommonFetcher;
import com.loris.client.fetcher.setting.SettingFactory;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.fetcher.util.DashBoard;
import com.loris.client.fetcher.util.HttpUtil;
import com.loris.client.model.SchedulerInfo;
import com.loris.client.model.SchedulerStatus;
import com.loris.client.model.WebPage;
import com.loris.client.parser.impl.LinksWebPageParser;
import com.loris.client.scheduler.TaskScheduler;
import com.loris.client.scheduler.Scheduler;
import com.loris.client.scheduler.SchedulerFactory;
import com.loris.client.task.Task;
import com.loris.client.task.basic.BasicTask;
import com.loris.client.task.plugin.BasicWebPageTaskPlugin;
import com.loris.client.task.util.TaskQueue;
import com.loris.common.model.TableRecords;
import com.loris.common.service.DataService;
import com.loris.common.util.ArraysUtil;
import com.loris.common.util.KeyMap;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.collection.MatchItemList;
import com.loris.soccer.collection.MatchList;
import com.loris.soccer.collection.MatchResultList;
import com.loris.soccer.collection.OddsNumList;
import com.loris.soccer.collection.OddsOpList;
import com.loris.soccer.collection.OddsScoreList;
import com.loris.soccer.collection.OddsYpList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.ZgzcwIssueDataPlugin;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.ZgzcwPageCreator;
import com.loris.soccer.data.zgzcw.parser.CenterPageParser;
import com.loris.soccer.data.zgzcw.parser.CupWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LeagueWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryBdScoreWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryBdWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryJcScoreWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryJcWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsNumWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsOpWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsYpWebPageParser;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Logo;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.OddsNum;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsScore;
import com.loris.soccer.model.Team;
import com.loris.soccer.service.OddsService;

/**
 * Hello world!
 *
 */
public class App
{
	private static Logger logger = Logger.getLogger(App.class);

	/** Spring环境 */
	private static ClassPathXmlApplicationContext context;

	public static void main(String[] args)
	{
		try
		{
			getApplicationContext();
			// testSetting();
			// testQueue();
			// testAutowired();
			// testZgzcwWebPage();
			// testZgzcwOpWebPage();
			// testZgzcwNumWebPage();
			// testZgzcwLeagueWebPage();
			// testBdWebPage();
			//testJcWebPage();
			
			testZgzcwIssueScheduler();
			// testCenterPage();
			// testOddsOpPage();
			// testOddsYpPage();
			// testOddsNumPage();
			// testJcScoreWebPage();
			// testUpdate();
			// testLeagueCenterPage();
			// testMariaDB();
			// testDateString();
			// testJcWebPage();

			// testSchedulerInfo();
			// testMapEqual();
			// testMainThreadScheduler();
			// testContext();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				// context.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			context = null;
		}
	}

	/**
	 * 测试线程处理工具
	 * 
	 * @throws Exception
	 */
	public static void testZgzcwIssueScheduler() throws Exception
	{
		SchedulerStatus status = new SchedulerStatus();
		status.setIntervaltime(4000);
		status.setName("任务处理器");
		status.setSid("100-100-100");
		status.setMaxActiveTaskThread(3);

		TaskScheduler scheduler = new TaskScheduler(status);
		scheduler.setMaxActiveTaskThread(3);
		// scheduler.setName("即时任务下载器");

		ZgzcwIssueDataPlugin plugin = (ZgzcwIssueDataPlugin) context.getBean(ZgzcwIssueDataPlugin.class);
		// HttpCommonWebPageExecutor executor =
		// (HttpCommonWebPageExecutor)context.getBean("httpCommonPlugin");

		scheduler.addTaskPlugin(plugin);
		// scheduler.addTaskPlugin(executor);

		SchedulerFactory.startTaskScheduler(scheduler);
	}

	public static void testJcScoreWebPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_SCORE_JC);
		if (!downloadWebPage(page))
		{
			return;
		}

		LotteryJcScoreWebPageParser parser = new LotteryJcScoreWebPageParser();
		TableRecords records = parser.parse(page);

		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}

		OddsScoreList list = (OddsScoreList) records.get(SoccerConstants.SOCCER_DATA_SCORE_LIST);
		if (list == null)
		{
			logger.info("No OddsScore List, error.");
			return;
		}
		logger.info("Total OddsScore size is " + list.size());
		saveTableRecords(records);
	}

	public static void testBdScoreWebPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_SCORE_BD);
		if (!downloadWebPage(page))
		{
			return;
		}

		LotteryBdScoreWebPageParser parser = new LotteryBdScoreWebPageParser();
		TableRecords records = parser.parse(page);

		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}

		OddsScoreList list = (OddsScoreList) records.get(SoccerConstants.SOCCER_DATA_SCORE_LIST);
		if (list == null)
		{
			logger.info("No OddsScore List, error.");
			return;
		}
		logger.info("Total OddsScore size is " + list.size());
		saveTableRecords(records);
	}

	public static void testLeagueCenterPage() throws Exception
	{
		String lid = "36";
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_LID, lid);
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LEAGUE_LEAGUE, params);

		if (!downloadWebPage(page))
		{
			return;
		}

		LeagueWebPageParser parser = new LeagueWebPageParser();
		TableRecords records = parser.parse(page);

		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}

		MatchList list = (MatchList) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
		if (list == null)
		{
			logger.info("No Match list, error.");
			return;
		}
		logger.info("Total Match size is " + list.size());

		// RoundList rounds = (RoundList)
		// records.get(SoccerConstants.SOCCER_DATA_ROUND_LIST);
		MatchResultList results = (MatchResultList) records.get(SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST);
		int i = 1;
		for (MatchResult round : results)
		{
			logger.info(i++ + ": " + round);
		}

		saveTableRecords(records);
	}

	public static void testUpdate() throws Exception
	{
		OddsService service = context.getBean(OddsService.class);
		long st = System.currentTimeMillis();
		service.updateOpList();
		long en = System.currentTimeMillis();

		logger.info("Total spend time is " + (en - st) + " ms.");
	}

	public static void testOddsNumPage() throws Exception
	{
		String mid = "2402907";
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_MID, mid);
		params.put(SoccerConstants.NAME_FIELD_MATCHTIME, "2019-03-12 18:00");

		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_NUM, params);
		if (!downloadWebPage(page))
		{
			return;
		}

		OddsNumWebPageParser parser = new OddsNumWebPageParser();
		TableRecords records = parser.parse(page);

		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}

		OddsNumList list = (OddsNumList) records.get(SoccerConstants.SOCCER_DATA_NUM_LIST);
		if (list == null)
		{
			logger.info("No NumList, error.");
			return;
		}
		logger.info("Total OddsNum size is " + list.size());
		saveTableRecords(records);
	}

	public static void testOddsYpPage() throws Exception
	{
		String mid = "2402907";
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_MID, mid);
		params.put(SoccerConstants.NAME_FIELD_MATCHTIME, "2019-03-12 18:00");

		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_YP, params);
		if (!downloadWebPage(page))
		{
			return;
		}

		OddsYpWebPageParser parser = new OddsYpWebPageParser();
		TableRecords records = parser.parse(page);

		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}

		OddsYpList list = (OddsYpList) records.get(SoccerConstants.SOCCER_DATA_YP_LIST);
		if (list == null)
		{
			logger.info("No yplist, error.");
			return;
		}
		logger.info("Total OddsYp size is " + list.size());
		saveTableRecords(records);
	}

	public static void testOddsOpPage() throws Exception
	{
		String mid = "2402789";
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_MID, mid);

		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params);
		if (!downloadWebPage(page))
		{
			return;
		}

		OddsOpWebPageParser parser = new OddsOpWebPageParser();
		TableRecords records = parser.parse(page);

		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}

		OddsOpList list = (OddsOpList) records.get(SoccerConstants.SOCCER_DATA_OP_LIST);
		if (list == null)
		{
			logger.info("No oplist, error.");
			return;
		}
		logger.info("Total OddsOp size is " + list.size());
		saveTableRecords(records);
	}

	public static void testCenterPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_CENTER);
		if (!downloadWebPage(page))
		{
			return;
		}

		CenterPageParser parser = new CenterPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
		}

		LeagueList leagues = (LeagueList) records.get(SoccerConstants.SOCCER_DATA_LEAGUE_LIST);
		int i = 1;
		for (League league : leagues)
		{
			logger.info(i++ + ": " + league);
		}

		saveTableRecords(records);
	}

	public static void testDateString()
	{
		Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}");
		Matcher matcher = pattern
				.matcher("2、开标时间：2018-08-02 14:00。2、开标时间：2018-08-02 16:00:00。2、开标时间：2018-08-02 15:00:00。");
		while (matcher.find())
		{
			System.out.println(matcher.group());
		}
		Matcher matcher2 = pattern.matcher("比赛时间:2019-03-12 06:00");
		while (matcher2.find())
		{
			System.out.println(matcher2.group());
		}
	}

	public static void testMapEqual() throws Exception
	{
		Map<String, String> map1 = new HashMap<>();
		Map<String, String> map2 = new Hashtable<>();

		map1.put("1", "200");
		map2.put("1", "200");

		logger.info(map1.equals(map2));
		logger.info("Map1: " + map1.hashCode() + ", Map2: " + map2.hashCode());

		map2.put("2", "100");
		map1.put("2", "100");
		logger.info("Map1: " + map1.hashCode() + ", Map2: " + map2.hashCode());

		logger.info(map1.equals(map2));

		map1.put("3", "50");
		map2.put("3", "105");
		logger.info(map1.equals(map2));
		logger.info("Map1: " + map1.hashCode() + ", Map2: " + map2.hashCode());

		Map<String, String> params1 = new HashMap<>();
		params1.put(SoccerConstants.NAME_FIELD_MID, "201002");
		WebPage page1 = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params1);

		Map<String, String> params2 = new HashMap<>();
		params2.put(SoccerConstants.NAME_FIELD_MID, "201002");
		WebPage page2 = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params2);

		logger.info(page1.hashCode());
		logger.info(page2.hashCode());

		logger.info(page1.equals(page2));

		page2.setPriority(20);

		TaskQueue queue = new TaskQueue();
		queue.add(page1);
		queue.add(page2);

		logger.info(queue.size());

		page2.setMethod(HttpUtil.HTTP_METHOD_POST);
		queue.add(page2);
		logger.info(queue.size());

		logger.info(queue.poll().getPriority());
		logger.info(queue.poll().getPriority());
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
		info.addPlugin("bean:httpCommonPlugin");
		info.addPlugin(SchedulerInfo.PLUGIN_CLASS, ZgzcwIssueDataPlugin.class.getName());

		Scheduler scheduler = SchedulerFactory.createTaskScheduler(info);
		logger.info(scheduler.getName());
		SchedulerFactory.startTaskScheduler(scheduler);
	}

	/**
	 * 测试比分数据页面
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testScoreWebPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_SCORE_JC);
		if (!downloadWebPage(page))
		{
			return;
		}

		LotteryJcScoreWebPageParser parser = new LotteryJcScoreWebPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
		}
		List<OddsScore> scores = (List<OddsScore>) records.get(SoccerConstants.SOCCER_DATA_ODDS_NUM);
		int i = 1;
		for (OddsScore oddsScore : scores)
		{
			logger.info(
					i++ + ": " + oddsScore.getOrdinary() + " " + oddsScore.getMid() + " " + oddsScore.getOddsvalue());
		}
	}

	/**
	 * 测试竞彩页面
	 * 
	 * @throws Exception
	 */
	public static void testJcWebPage() throws Exception
	{
		Map<String, String> params = new LinkedHashMap<>();
		// params.put(SoccerConstants.NAME_FIELD_ISSUE, "2019-02-02");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LOTTERY_JC, params);

		if (!downloadWebPage(page))
		{
			return;
		}

		LotteryJcWebPageParser parser = new LotteryJcWebPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}
		
		/*
		 * int i = 1; for (MatchItem match : matchJcs) { logger.info(i +++ ": "
		 * + match); }
		 */
		MatchItemList matchJcs = (MatchItemList) records.get(SoccerConstants.SOCCER_DATA_MATCH_JC_LIST);
		if (matchJcs == null)
		{
			logger.info("Error: no Match JC data.");
			return;
		}
		logger.info("Match Jc data list size is " + matchJcs.size());
		
		MatchList matchList = (MatchList) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
		if(matchList != null && matchList.size() > 0)
		{		
			List<String> leagues = ArraysUtil.getObjectFieldValue(matchList, Match.class, SoccerConstants.NAME_FIELD_LID);
			for (String string : leagues)
			{
				logger.info(string);
			}
		}
		
		saveTableRecords(records);
	}

	/**
	 * 测试北单页面
	 * 
	 * @throws Exception
	 */
	public static void testBdWebPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LOTTERY_BD);

		if (!downloadWebPage(page))
		{
			return;
		}

		LotteryBdWebPageParser parser = new LotteryBdWebPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}
		saveTableRecords(records);
	}

	/**
	 * 创建数据主页面
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwLeagueWebPage() throws Exception
	{
		Map<String, String> params = new LinkedHashMap<>();
		params.put("lid", "83");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LEAGUE_CUP, params);

		if (!downloadWebPage(page))
		{
			return;
		}

		// logger.info(page.getContent());

		CupWebPageParser parser = new CupWebPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
		}

		int i = 1;
		List<Match> matchs = (List<Match>) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
		for (Match match : matchs)
		{
			logger.info(i++ + ": " + match.getMid() + "(" + match.getMatchtime() + ")");
		}

		List<Team> teams = (List<Team>) records.get(SoccerConstants.SOCCER_DATA_TEAM_LIST);
		i = 1;
		for (Team team : teams)
		{
			logger.info(i++ + ": " + team.getTid() + ", " + team.getName());
		}
	}

	/**
	 * 创建数据主页面
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwWebPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_CENTER, null);

		if (!downloadWebPage(page))
		{
			return;
		}

		CenterPageParser parser = new CenterPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
		}

		List<League> leagues = (List<League>) records.get(SoccerConstants.SOCCER_DATA_LEAGUE_LIST);
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
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwOpWebPage() throws Exception
	{
		Map<String, String> params = new LinkedHashMap<>();
		params.put("mid", "2432303");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_OP, params);

		if (!downloadWebPage(page))
		{
			return;
		}

		// logger.info(page.getContent());

		OddsOpWebPageParser parser = new OddsOpWebPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
		}

		int i = 1;
		List<OddsOp> ops = (List<OddsOp>) records.get(SoccerConstants.SOCCER_DATA_OP_LIST);
		for (OddsOp op : ops)
		{
			logger.info(i++ + ": " + op.getCorpid() + "(" + op.getWinodds() + ", " + op.getDrawodds() + ", "
					+ op.getLoseodds() + ")" + op.getOpentime());
		}
	}

	/**
	 * 创建数据主页面
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwNumWebPage() throws Exception
	{
		Map<String, String> params = new LinkedHashMap<>();
		params.put("mid", "2437113");
		params.put("matchtime", "2019-02-02 14:35:00");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_NUM, params);

		if (!downloadWebPage(page))
		{
			return;
		}

		// logger.info(page.getContent());

		OddsNumWebPageParser parser = new OddsNumWebPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
		}

		int i = 1;
		List<OddsNum> odds = (List<OddsNum>) records.get(SoccerConstants.SOCCER_DATA_NUM_LIST);
		for (OddsNum odd : odds)
		{
			logger.info(i++ + ": " + odd.getCorpid() + "(" + odd.getWinodds() + ", " + odd.getGoalnum() + ", "
					+ odd.getLoseodds() + ")" + odd.getOpentime());
		}
	}

	/**
	 * 测试
	 * 
	 * @throws Exception
	 */
	public static void testAutowired() throws Exception
	{
		try (ZgzcwIssueDataPlugin plugin = context.getBean(ZgzcwIssueDataPlugin.class))
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
		// scheduler.setName("即时任务下载器");

		scheduler.addTaskPlugin(new BasicWebPageTaskPlugin());

		SchedulerFactory.startTaskScheduler(scheduler);

		// Thread thread = new Thread(scheduler);
		// thread.start();
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

	private static boolean downloadWebPage(WebPage page) throws Exception
	{
		FetcherSetting defaulSetting = (FetcherSetting) context.getBean("defaultSetting");
		try (WebFetcher client = new HttpCommonFetcher(defaulSetting))
		{
			long st = System.currentTimeMillis();

			if (!client.download(page))
			{
				logger.info("Error when downloading: " + page.getUrl());
				return false;
			}

			long en = System.currentTimeMillis();
			logger.info("Get web page spend time is " + (en - st) + " ms.");
			return true;
		}
	}

	/**
	 * 存储数据
	 * 
	 * @param records
	 * @throws Exception
	 */
	private static void saveTableRecords(TableRecords records) throws Exception
	{
		long st = System.currentTimeMillis();
		DataService dataService = (DataService) context.getBean(com.loris.soccer.service.impl.SoccerDataService.class);
		dataService.saveTableRecords(records);
		long en = System.currentTimeMillis();
		logger.info("Save TableRecords " + records.toString() + " spend time is " + (en - st) + " ms.");
	}

	/**
	 * 测试MariaDB数据库的操作
	 * 
	 * @throws Exception
	 */
	public static void testMariaDB() throws Exception
	{
		/*
		 * String driver = "com.mysql.cj.jdbc.Driver"; String url =
		 * "jdbc:mysql://127.0.0.1:3307/ams?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";
		 * String user = "sdDtVp72zPE="; String password =
		 * "dzsM5hZNilfcvIJWL2/TXg=="; Class.forName(driver);
		 * 
		 * try(Connection connection = DriverManager.getConnection(url, user,
		 * password)) { logger.info("Success to connect: " + url); }
		 */
	}

	/**
	 * 获得Spring的运行与配置环境
	 * 
	 * @return
	 */
	public static void getApplicationContext()
	{
		/** The Application Context. */
		context = new ClassPathXmlApplicationContext("classpath*:spring-mybatis.xml");
		// context = new
		// ClassPathXmlApplicationContext("classpath*:spring-test.xml");
	}
}