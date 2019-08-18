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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.loris.client.fetcher.WebFetcher;
import com.loris.client.fetcher.impl.HtmlUnitFetcher;
import com.loris.client.fetcher.impl.HttpCommonFetcher;
import com.loris.client.fetcher.setting.SettingFactory;
import com.loris.client.fetcher.setting.FetcherSetting;
import com.loris.client.fetcher.util.DashBoard;
import com.loris.client.fetcher.util.HttpUtil;
import com.loris.client.model.SchedulerPlugins;
import com.loris.client.model.SchedulerStatus;
import com.loris.client.model.WebPage;
import com.loris.client.parser.impl.LinksWebPageParser;
import com.loris.client.scheduler.TaskScheduler;
import com.loris.client.scheduler.factory.SchedulerFactory;
import com.loris.client.sender.impl.HttpWebSender;
import com.loris.client.service.WebPageService;
import com.loris.client.scheduler.Scheduler;
import com.loris.client.task.Task;
import com.loris.client.task.basic.BasicTask;
import com.loris.client.task.plugin.BasicWebPageTaskPlugin;
import com.loris.client.task.util.TaskQueue;
import com.loris.common.constant.Enviroment;
import com.loris.common.model.TableRecords;
import com.loris.common.util.DateUtil;
import com.loris.common.util.KeyMap;
import com.loris.common.util.PairMap;
import com.loris.common.web.wrapper.Rest;
import com.loris.old.soccer.bean.OldMatch;
import com.loris.old.soccer.service.OldMatchService;
import com.loris.old.soccer.transfer.impl.MatchToMatchResult;
import com.loris.old.soccer.transfer.impl.OldMatchToMatch;
import com.loris.soccer.collection.LeagueList;
import com.loris.soccer.collection.LeagueMappingList;
import com.loris.soccer.collection.MatchMappingList;
import com.loris.soccer.collection.OddsOpList;
import com.loris.soccer.collection.OddsYpList;
import com.loris.soccer.collection.SeasonList;
import com.loris.soccer.collection.TeamMappingList;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.data.ZgzcwIssueDataPlugin;
import com.loris.soccer.data.ZgzcwLeagueDataPlugin;
import com.loris.soccer.data.ZgzcwMatchResultDataPlugin;
import com.loris.soccer.data.okooo.OkoooConstants;
import com.loris.soccer.data.okooo.OkoooPageCreator;
import com.loris.soccer.data.okooo.OkoooPageParser;
import com.loris.soccer.data.okooo.util.OkoooUtil;
import com.loris.soccer.data.util.MappingUtil;
import com.loris.soccer.data.zgzcw.ZgzcwConstants;
import com.loris.soccer.data.zgzcw.ZgzcwPageCreator;
import com.loris.soccer.data.zgzcw.ZgzcwPageParser;
import com.loris.soccer.data.zgzcw.parser.CenterPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryBdScoreWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryBdWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryJcScoreWebPageParser;
import com.loris.soccer.data.zgzcw.parser.LotteryJcWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsNumWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsOpWebPageParser;
import com.loris.soccer.data.zgzcw.parser.OddsYpWebPageParser;
import com.loris.soccer.model.IssueMatch;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Logo;
import com.loris.soccer.model.Match;
import com.loris.soccer.model.MatchResult;
import com.loris.soccer.model.MatchResult.ResultType;
import com.loris.soccer.model.base.Result;
import com.loris.soccer.model.OddsNum;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsScore;
import com.loris.soccer.model.OddsYp;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooOddsOp;
import com.loris.soccer.model.OkoooOddsYp;
import com.loris.soccer.model.OkoooTeam;
import com.loris.soccer.model.Season;
import com.loris.soccer.model.Team;
import com.loris.soccer.model.mapping.LeagueMapping;
import com.loris.soccer.model.mapping.MatchMapping;
import com.loris.soccer.model.mapping.TeamMapping;
import com.loris.soccer.model.view.MatchInfo;
import com.loris.soccer.service.DataService;
import com.loris.soccer.service.MappingService;
import com.loris.soccer.service.MatchService;
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
			Enviroment.startJobScheduler = false;
			getApplicationContext();
			// testSetting();
			// testZgzcwIssueScheduler();
			// testCenterPage();
			// testOddsOpPage();
			// testOddsYpPage();
			// testOddsNumPage();
			// testJcScoreWebPage();
			// testUpdate();
			// testLeagueCenterPage();
			// testMatchResult();
			// testUpload();
			// testSourceFinance();
			// testStat();
			// testTeamRating();
			testOddsScore();
			// testOkooo();
			// testOkoooOdds();
			// testOkoooMapping();
			// testOkoooTeamMapping();
			// testDateCompare();			
			// testSigmoid();
			// testMariaDB();
			// testDateString();
			// testSchedulerInfo();
			// testMapEqual();
			// testMainThreadScheduler();
			// testContext();
			// testQueue();
			// testAutowired();
			// testZgzcwWebPage();
			// testZgzcwOpWebPage();
			// testZgzcwNumWebPage();
			// testZgzcwYpWebPage();
			// testZgzcwLeagueWebPage();
			// testBdWebPage();
			// testJcWebPage();
			// testLiveBdWebPage();
			// testWebPageService();
			// testLeagueRoundWebPage();
			// testZgzcwCupWebPage();
			// testZgzcwLeagueCenterScheduler();	
			// testBdMatchInfo();
			// addSchedulerInfo();
			// testQuartzJob();
			// testMatchBd();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				context.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			context = null;
		}
	}
	
	public static void testOddsScore() throws Exception
	{
		OddsService oddsService = (OddsService) context.getBean("oddsService");
		//String mid = "";
		List<OddsScore> scores = oddsService.getOddsScores(null, null, null);
		for (OddsScore oddsScore : scores)
		{
			Map<Result, Float> results = oddsScore.getResultOdds();
			logger.info(oddsScore.getMid() + " Size of result is " + results.size());
			float[] probs = new float[results.size()];
			int i = 0;
			for (Result result : results.keySet())
			{
				probs[i] = results.get(result);
			}
		}
	}
	
	/**
	 * 测试对球队进行匹配
	 * @throws Exception
	 */
	public static void testOkoooTeamMapping() throws Exception
	{
		MappingService mappingService = (MappingService)context.getBean("mappingService");
		List<OkoooTeam> okoooTeams = mappingService.getOkoooTeams();
		List<Team> teams = mappingService.getTeams();
		
		float[][] values = MappingUtil.computeTeamNameCoefficients(okoooTeams, teams);
		int n = okoooTeams.size();
		int m = teams.size();
		
		for (int i = 0; i < n; i ++)
		{
			float max = -1.0f;
			int index = -1;
			for(int j = 0; j < m; j ++)
			{
				if(max < values[i][j])
				{
					max = values[i][j];
					index = j;
				}
			}
			logger.info(i + ": " + okoooTeams.get(i) + "=>" + teams.get(index) + " = " + max);
		}
	}
	
	/**
	 * 测试澳客网的映射数据处理
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testOkoooMapping() throws Exception
	{
		MappingService mappingService = (MappingService)context.getBean("mappingService");
		Date start = DateUtil.tryToParseDate("2019-07-17");
		MatchMappingList matchMappings = MappingUtil.createOkoooIssueMatchMapping(mappingService, start, null);
		
		PairMap<OkoooMatch, MatchInfo> pairs = (PairMap<OkoooMatch, MatchInfo>)MappingUtil.getPairMatchs(mappingService, matchMappings);
		int index = 0;
		for (OkoooMatch match : pairs.keySet())
		{
			Object value = pairs.get(match);
			logger.info(index +++ " " + match + "=>" + value);
		}
		
		for (MatchMapping matchMapping : matchMappings)
		{
			logger.info(matchMapping);
		}
		
		LeagueMappingList leagueMappings = MappingUtil.getOkoooLeagueMappings(mappingService, pairs);
		for (LeagueMapping leagueMapping : leagueMappings)
		{
			logger.info(leagueMapping);
		}
		
		TeamMappingList teamMappings = MappingUtil.getOkoooTeamMappings(mappingService, pairs);
		for (TeamMapping teamMapping : teamMappings)
		{
			logger.info(teamMapping);
		}
		
		logger.info("Save MatchMapping: " + matchMappings.size());
		mappingService.insertList(MatchMapping.class, matchMappings);
		mappingService.insertList(LeagueMapping.class, leagueMappings);
		mappingService.insertList(TeamMapping.class, teamMappings);
	}
	
	
	public static void testDateCompare() throws Exception
	{
		String t = "2019-07-18 01:00:00";
		Date d1 = DateUtil.tryToParseDate(t);
		Date d2 = DateUtil.tryToParseDate(t);
		logger.info("d1 == d2: " + (d1 == d2));
		logger.info("compare(d1, d2): " + (DateUtil.compareDate(d1, d2) == 0));
	}
	
	@SuppressWarnings("unchecked")
	public static void testOkoooOdds() throws Exception
	{
		String mid = "1051887";
		String matchtime = "2019-07-18 08:00:00";
		
		KeyMap params = new KeyMap();
		params.put(SoccerConstants.NAME_FIELD_MID, mid);
		params.put(SoccerConstants.NAME_FIELD_MATCHTIME, matchtime);
		//params.put(SoccerConstants.NAME_FIELD_PAGE, "0");
		
		WebPage page = OkoooPageCreator.createOkoooWebPage(OkoooConstants.PAGE_ODDS_YP, params);
		
		try(HtmlUnitFetcher fetcher = createHtmlUnitFetcher())
		{
			TableRecords records = OkoooUtil.downloadOkoooOddsPage(fetcher, page);
			if(records == null)
			{
				logger.info("The record table is null.");
				return;
			}

			for (String key : records.keySet())
			{
				logger.info(key);
				switch (key)
				{
				case SoccerConstants.SOCCER_DATA_ODDS_OKOOO_OP_LIST:
					List<OkoooOddsOp> ops = (List<OkoooOddsOp>) records.get(key);
					for (OkoooOddsOp okoooOddsOp : ops)
					{
						logger.info(okoooOddsOp);
					}
					logger.info("Total ops number is " + ops.size());
					break;
				case SoccerConstants.SOCCER_DATA_ODDS_OKOOO_YP_LIST:
					List<OkoooOddsYp> yps = (List<OkoooOddsYp>) records.get(key);
					for (OkoooOddsYp okoooOddsYp : yps)
					{
						logger.info(okoooOddsYp);
					}
					logger.info("Total yps number is " + yps.size());
					break;
				default:
					logger.info(records.get(key));
					break;
				}

			}
		}
	}
	
	public static void testOkooo() throws Exception
	{
		DataService dataService = (DataService) context.getBean("soccerDataService");
		WebPage page = OkoooPageCreator.createOkoooWebPage(OkoooConstants.PAGE_BD_SCORE);
		logger.info("Download: " + page.getUrl());
		if(downloadOkoooPage(page))
		{
			//logger.info(page.getContent());
			TableRecords records = OkoooPageParser.parseWebPage(page);
			if(records == null)
			{
				logger.info("The record table is null.");
				return;
			}
			
			dataService.saveTableRecords(records);
			
			/*
			List<OkoooIssueMatch> issueMatchs = null;
			for (String key : records.keySet())
			{
				logger.info(key);
				switch (key)
				{
				case SoccerConstants.SOCCER_DATA_LEAGUE_OKOOO_LIST:
					List<OkoooLeague> leagues = (List<OkoooLeague>)records.get(key);
					logger.info(leagues.size());
					for (OkoooLeague okoooLeague : leagues)
					{
						logger.info(okoooLeague);
					}
					break;
				case SoccerConstants.SOCCER_DATA_MATCH_OKOOO_BD_LIST:
					issueMatchs = (List<OkoooIssueMatch>) records.get(key);
					for (OkoooIssueMatch okoooIssueMatch : issueMatchs)
					{
						logger.info(okoooIssueMatch);
					}
					break;
				case SoccerConstants.SOCCER_DATA_MATCH_OKOOO_JC_LIST:
					issueMatchs = (List<OkoooIssueMatch>) records.get(key);
					for (OkoooIssueMatch okoooIssueMatch : issueMatchs)
					{
						logger.info(okoooIssueMatch);
					}
					break;
					case SoccerConstants.SOCCER_DATA_BETODDS_BD_LIST:
					List<BetBdOdds> oddsList = (List<BetBdOdds>) records.get(key);
					for (BetBdOdds betBdOdds : oddsList)
					{
						logger.info(betBdOdds);
					}
					break;
				case SoccerConstants.SOCCER_DATA_BETODDS_JC_LIST:
					List<BetJcOdds> jcoddsList = (List<BetJcOdds>) records.get(key);
					for (BetJcOdds betBdOdds : jcoddsList)
					{
						logger.info(betBdOdds);
					}
					break;
				case SoccerConstants.SOCCER_DATA_MATCH_OKOOO_LIST:
					List<OkoooMatch> matchs = (List<OkoooMatch>) records.get(key);
					for (OkoooMatch okoooMatch : matchs)
					{
						logger.info(okoooMatch);
					}
					break;
				case SoccerConstants.SOCCER_DATA_TEAM_OKOOO_LIST:
					List<OkoooTeam> teams = (List<OkoooTeam>)records.get(key);
					for (OkoooTeam okoooTeam : teams)
					{
						logger.info(okoooTeam);
					}
					break;
				default:
					break;
				}
			}*/
		}
	}
	
	
	public static void testSourceFinance() throws Exception
	{
		String mid = "1788379";
		OldMatchService matchService = (OldMatchService) context.getBean("oldMatchService");
		OldMatch match = matchService.getMatch(mid);
		logger.info(match);
		
		OldMatchToMatch matchTransfer = new OldMatchToMatch();
		MatchToMatchResult resultTransfer = new MatchToMatchResult();
		Match match2 = matchTransfer.mapping(match);
		MatchResult result = resultTransfer.mapping(match);
		
		logger.info("New Match: " + match2);
		logger.info("Match Result: " + result);
	}
	
	public static void testUpload() throws Exception
	{
		//logger.info("startScheduler: " + Enviroment.getParams("startScheduler"));
		//logger.info("file.encoding: " + Enviroment.getParams("file.encoding"));
		HttpWebSender sender = new HttpWebSender();
		sender.setUrl("http://localhost/soccer/upload/uploadRecords");
		
		MatchResult result = new MatchResult();
		result.setMid("5656458");
		result.setResultType(ResultType.LOSE);
		result.setHomegoal(3);
		result.setClientgoal(5);
		result.setMid("这是管委会");
		
		List<MatchResult> results = new ArrayList<>();
		results.add(result);
		
		//TableRecords records = new TableRecords();
		//records.put(SoccerConstants.SOCCER_DATA_MATCH_RESULT, records);
		//records.put("Tst", "test");
		
		Map<String, List<MatchResult>> records = new HashMap<>();
		records.put(SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST, results);
		logger.info(JSON.toJSONString(records));
		
		Rest rest = sender.send("records", records);
		logger.info("Result: " + rest);
		
		/*sender.setUrl("http://localhost/soccer/upload/uploadJson");
		rest = sender.send(records);
		logger.info("Result: " + rest);*/
	}
	
	public static void testMatchResult() throws Exception
	{
		ZgzcwMatchResultDataPlugin plugin = context.getBean(ZgzcwMatchResultDataPlugin.class);
		plugin.produce(null);
	}
	
	public static void testQuartzJob() throws Exception
	{
        logger.info("Test Quartz job.");
	}
	
	/**
	 * 添加运行计划数据
	 * @throws Exception
	 */
	public static void addSchedulerInfo() throws Exception
	{
		SchedulerFactory factory = SchedulerFactory.me();
		
		SchedulerPlugins info = new SchedulerPlugins();
		info.setIntervaltime(4500);
		info.setMaxActiveTaskThread(3);
		info.setName("最新开盘数据下载");
		info.setRandTimeSeed(200);
		info.setType("zgzcw.downloader");
		info.addPlugin(SchedulerPlugins.PLUGIN_BEAN, ZgzcwIssueDataPlugin.class.getName());
		
		factory.addSchedulerInfo(info);
		
		factory.saveAllSchedulers();
	}
	
	public static void testMatchBd() throws Exception
	{
		Date start = DateUtil.parseDay("2019-03-16");
		Date end = DateUtil.parseDay("2019-03-16");
		end = DateUtil.getDateLast(end);
		logger.info("START: " + start);
		logger.info("END: " + end);
		MatchService matchService = context.getBean(MatchService.class);
		List<IssueMatch> matchBdInfos = matchService.getIssueMatchs(SoccerConstants.LOTTERY_BD, start, end);
		int i = 1;
		for (IssueMatch matchBdInfo : matchBdInfos)
		{
			logger.info(i +++ ": " + matchBdInfo);
		}
	}
	
	/**
	 * 测试线程处理工具
	 * 
	 * @throws Exception
	 */
	public static void testZgzcwLeagueCenterScheduler() throws Exception
	{
		SchedulerStatus status = new SchedulerStatus();
		status.setIntervaltime(4000);
		status.setName("任务处理器");
		status.setSid("100-100-100");
		status.setMaxActiveTaskThread(3);

		TaskScheduler scheduler = new TaskScheduler(status);
		scheduler.setMaxActiveTaskThread(3);
		// scheduler.setName("即时任务下载器");

		ZgzcwLeagueDataPlugin plugin = context.getBean(ZgzcwLeagueDataPlugin.class);
		// HttpCommonWebPageExecutor executor =
		// (HttpCommonWebPageExecutor)context.getBean("httpCommonPlugin");

		scheduler.addTaskPlugin(plugin);
		// scheduler.addTaskPlugin(executor);

		SchedulerFactory.startTaskScheduler(scheduler);
	}
	
	@SuppressWarnings("unchecked")
	public static void testLeagueRoundWebPage() throws Exception
	{
		KeyMap params = new KeyMap();
		params.put(ZgzcwConstants.NAME_FIELD_SOURCE_LID, "150");
		params.put(ZgzcwConstants.NAME_FIELD_SEASON, "2018-2019");
		params.put(ZgzcwConstants.NAME_FIELD_CUR_ROUND, "21");
		params.put(ZgzcwConstants.NAME_FIELD_LEAGUE_TYPE, "");
		
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LEAGUE_LEAGUE_ROUND, params);
		if (!downloadWebPage(page))
		{
			return;
		}
		//logger.info(page.getUrl());
		//logger.info(page.getContent());

		TableRecords records = ZgzcwPageParser.parseWebPage(page);
		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}
		List<Match> list = (List<Match>) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
		if (list == null)
		{
			logger.info("No Match list, error.");
			return;
		}
		logger.info("Total Match size is " + list.size());

		// RoundList rounds = (RoundList)
		// records.get(SoccerConstants.SOCCER_DATA_ROUND_LIST);
		List<MatchResult> results = (List<MatchResult>) records.get(SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST);
		logger.info("Total Match size is " + results.size());
		
		saveTableRecords(records);
	}
	
	public static void testWebPageService() throws Exception
	{
		List<String> types = new ArrayList<>();
		types.add("yp");
		types.add("league");
		WebPageService pageService = context.getBean(WebPageService.class);
		List<WebPage> pages = pageService.getWebPage(SoccerConstants.SOURCE_ZGZCW, types, null, null);
		
		int i = 1;
		for (WebPage webPage : pages)
		{
			logger.info(i +++ ": " + webPage.getUrl() + ", " + webPage.getType() + ", " + webPage.getSource());
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

	@SuppressWarnings("unchecked")
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

		List<OddsScore> list = (List<OddsScore>) records.get(SoccerConstants.SOCCER_DATA_ODDS_SCORE_LIST);
		if (list == null)
		{
			logger.info("No OddsScore List, error.");
			return;
		}
		logger.info("Total OddsScore size is " + list.size());
		saveTableRecords(records);
	}
	
	@SuppressWarnings("unchecked")
	public static void testLiveBdWebPage() throws Exception
	{
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LIVE_BD);
		if (!downloadWebPage(page))
		{
			return;
		}

		TableRecords records = ZgzcwPageParser.parseWebPage(page);

		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}

		List<IssueMatch> list = (List<IssueMatch>) records.get(SoccerConstants.SOCCER_DATA_MATCH_BD_LIST);
		if (list == null)
		{
			logger.info("No OddsScore List, error.");
			return;
		}
		
		for (IssueMatch issueMatch : list)
		{
			logger.info(issueMatch);
		}
		
		List<Match> matchs = (List<Match>)records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
		for (Match match : matchs)
		{
			logger.info(match);
		}
		logger.info("Total OddsScore size is " + list.size());
		saveTableRecords(records);
	}

	@SuppressWarnings("unchecked")
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

		List<OddsScore> list = (List<OddsScore>) records.get(SoccerConstants.SOCCER_DATA_ODDS_SCORE_LIST);
		if (list == null)
		{
			logger.info("No OddsScore List, error.");
			return;
		}
		logger.info("Total OddsScore size is " + list.size());
		saveTableRecords(records);
	}

	@SuppressWarnings("unchecked")
	public static void testLeagueCenterPage() throws Exception
	{
		String lid = "83";
		Map<String, String> params = new KeyMap(SoccerConstants.NAME_FIELD_LID, lid);
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LEAGUE_CUP, params);

		if (!downloadWebPage(page))
		{
			return;
		}
		
		TableRecords records = ZgzcwPageParser.parseWebPage(page);

		if (records == null)
		{
			logger.info("Parser error.");
			return;
		}

		List<Match> list = (List<Match>) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
		if (list == null)
		{
			logger.info("No Match list, error.");
			return;
		}
		logger.info("Total Match size is " + list.size());

		// RoundList rounds = (RoundList)
		// records.get(SoccerConstants.SOCCER_DATA_ROUND_LIST);
		List<MatchResult> results = (List<MatchResult>) records.get(SoccerConstants.SOCCER_DATA_MATCH_RESULT_LIST);
		int i = 1;
		for (MatchResult result : results)
		{
			logger.info(i++ + ": " + result);
		}
		
		SeasonList seasons = (SeasonList) records.get(SoccerConstants.SOCCER_DATA_LEAGUE_SEASON_LIST);
		i = 1;
		for (Season season : seasons)
		{
			logger.info(i++ + ": " + season);
		}
		
		logger.info("Last Season of " + lid + " is " + seasons.getLastSeason());

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

	@SuppressWarnings("unchecked")
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

		List<OddsNum> list = (List<OddsNum>) records.get(SoccerConstants.SOCCER_DATA_ODDS_NUM_LIST);
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
		String mid = "2438020";
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

		OddsYpList list = (OddsYpList) records.get(SoccerConstants.SOCCER_DATA_ODDS_YP_LIST);
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
		String mid = "2438020";
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

		OddsOpList list = (OddsOpList) records.get(SoccerConstants.SOCCER_DATA_ODDS_OP_LIST);
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
		SchedulerPlugins info = new SchedulerPlugins();
		info.setId("101-190");
		info.setName("数据下载的信息");
		info.setIntervaltime(4000);
		info.setMaxActiveTaskThread(3);
		info.setRandTimeSeed(200);
		info.setType("zgzcw.downloader");
		info.addPlugin("bean:httpCommonPlugin");
		info.addPlugin(SchedulerPlugins.PLUGIN_CLASS, ZgzcwIssueDataPlugin.class.getName());

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
	@SuppressWarnings("unchecked")
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
		List<IssueMatch> matchJcs = (List<IssueMatch>) records.get(SoccerConstants.SOCCER_DATA_MATCH_JC_LIST);
		if (matchJcs == null)
		{
			logger.info("Error: no Match JC data.");
			return;
		}
		logger.info("Match Jc data list size is " + matchJcs.size());
		
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
	public static void testZgzcwCupWebPage() throws Exception
	{
		Map<String, String> params = new LinkedHashMap<>();
		params.put("lid", "181");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_LEAGUE_LEAGUE, params);

		if (!downloadWebPage(page))
		{
			return;
		}

		// logger.info(page.getContent());

		TableRecords records = ZgzcwPageParser.parseWebPage(page);
		
		if (records == null)
		{
			logger.info("Parser error.");
		}

		int i = 1;
		List<Match> matchs = (List<Match>) records.get(SoccerConstants.SOCCER_DATA_MATCH_LIST);
		for (Match match : matchs)
		{
			logger.info(i++ + ": " + match.getMid() + "(" + match.getMatchtime() + ", " + match.getSeason() + ")");
		}

		List<Team> teams = (List<Team>) records.get(SoccerConstants.SOCCER_DATA_TEAM_LIST);
		i = 1;
		for (Team team : teams)
		{
			logger.info(i++ + ": " + team.getTid() + ", " + team.getName());
		}
		
		List<Season> seasons = (List<Season>)records.get(SoccerConstants.SOCCER_DATA_LEAGUE_SEASON_LIST);
		i = 1;
		for (Season season : seasons)
		{
			logger.info(i +++ ": " + season);
		}
		saveTableRecords(records);
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
		List<OddsOp> ops = (List<OddsOp>) records.get(SoccerConstants.SOCCER_DATA_ODDS_OP_LIST);
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
		params.put(SoccerConstants.NAME_FIELD_MID, "2514558");
		params.put(SoccerConstants.NAME_FIELD_MATCHTIME, "2019-04-21 13:00:00");
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
		List<OddsNum> odds = (List<OddsNum>) records.get(SoccerConstants.SOCCER_DATA_ODDS_NUM_LIST);
		for (OddsNum odd : odds)
		{
			logger.info(i++ + ": " + odd.getCorpid() + "(" + odd.getWinodds() + ", " + odd.getGoalnum() + ", "
					+ odd.getLoseodds() + ")" + odd.getOpentime());
		}
	}
	
	/**
	 * 创建数据主页面
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void testZgzcwYpWebPage() throws Exception
	{
		Map<String, String> params = new LinkedHashMap<>();
		params.put(SoccerConstants.NAME_FIELD_MID, "2509215");
		params.put(SoccerConstants.NAME_FIELD_MATCHTIME, "2019-08-04 05:00:00");
		WebPage page = ZgzcwPageCreator.createZgzcwWebPage(ZgzcwConstants.PAGE_ODDS_YP, params);

		if (!downloadWebPage(page))
		{
			return;
		}

		// logger.info(page.getContent());

		OddsYpWebPageParser parser = new OddsYpWebPageParser();
		TableRecords records = parser.parse(page);
		if (records == null)
		{
			logger.info("Parser error.");
		}

		int i = 1;
		List<OddsYp> odds = (List<OddsYp>) records.get(SoccerConstants.SOCCER_DATA_ODDS_YP_LIST);
		for (OddsYp odd : odds)
		{
			logger.info(i++ + ": " + odd.getCorpid() + "(" + odd.getWinodds() + ", " + odd.getHandicapName() + ", "
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

		scheduler.addTaskPlugin(new BasicWebPageTaskPlugin(""));
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
	
	private static boolean downloadOkoooPage(WebPage page) throws Exception
	{
		FetcherSetting setting = (FetcherSetting) context.getBean("okoooSetting");
		WebPage basePage = (WebPage) context.getBean("okoooWebPage");
		try (WebFetcher client = new HtmlUnitFetcher(setting, basePage))
		{
			long st = System.currentTimeMillis();
			client.init();
			
			logger.info("Starting to download new page: " + page.getUrl());
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
	 * 创建下载管理器
	 * @return
	 * @throws Exception
	 */
	private static HtmlUnitFetcher createHtmlUnitFetcher() throws Exception
	{
		FetcherSetting setting = (FetcherSetting) context.getBean("okoooSetting");
		WebPage basePage = (WebPage) context.getBean("okoooWebPage");
		HtmlUnitFetcher client = new HtmlUnitFetcher(setting, basePage);
		client.init();
		return client;
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
		DataService dataService = (DataService) context.getBean(com.loris.soccer.service.impl.SoccerDataServiceImpl.class);
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
