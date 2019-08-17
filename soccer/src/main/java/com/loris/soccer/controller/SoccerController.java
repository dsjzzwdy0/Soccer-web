/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @SoccerController.java   
 * @Package com.loris.soccer.controllercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loris.auth.model.User;
import com.loris.common.constant.Constants;
import com.loris.common.exception.ParamsException;
import com.loris.common.util.DateUtil;
import com.loris.common.web.BaseController;
import com.loris.soccer.model.CompSetting;
import com.loris.soccer.model.League;
import com.loris.soccer.model.Round;
import com.loris.soccer.model.view.MatchInfo;
import com.loris.soccer.service.CompService;
import com.loris.soccer.service.LeagueService;
import com.loris.soccer.service.MatchService;

/**   
 * @ClassName:  SoccerController.java   
 * @Description: 足球控制服务器 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Controller
public class SoccerController extends BaseController
{
	//private static Logger logger = Logger.getLogger(SoccerController.class);

	// 开盘基础数据
	public final static String[][] ISSUE_PAGE_TYPES = { 
			{ "danchang", "北京单场" }, 	// 北京单场数据
			{ "jingcai", "竞彩足球" }, 	// 竞彩数据
			{ "listvars", "欧赔分析" } 	// 所有单场欧赔分析
	};

	// 当日开盘分析页面类型
	public final static String[][] ANALYSIS_PAGE_TYPES = { 
			{ "anarel", "关联分析" }, // 关联分析
			{ "anaoy", "欧亚对比分析" }, // 欧亚对比分析
			{ "anaop", "欧赔分析" }, // 欧赔分析
			{ "anayp", "亚盘对比分析" }, // 亚盘对比分析
			{ "anafc", "欧赔方差分析" }, // 欧赔方差分析
			{ "anazj", "战绩对比分析" }, // 战绩对比分析
			{ "anazh", "综合分析与结果推荐" } // 综合分析与结果推荐
	};

	// 比赛页面
	public final static String[][] LEAGUE_PAGE_TYPES = { 
			{ "leaguerel", "联赛关联" }, // 百家欧赔
			{ "leagueoy", "欧亚对比" }, // 欧亚对比
			{ "leaguefirst", "开盘分析" }, // 大小对比
			{ "leagueop", "赔率分析" }, // 赔率分析
			{ "bsls", "比赛历史" }, // 比赛历史
			{ "qpql", "球盘球路" }, // 球盘球路
			{ "opvar", "欧赔分析" } // 欧赔分析
	};

	// 比赛页面
	public final static String[][] MATCH_PAGE_TYPES = { 
			{ "bjop", "百家欧赔" }, // 百家欧赔
			{ "ypdb", "亚盘数据" }, // 亚盘
			{ "dxdb", "大小对比" }, // 大小对比
			{ "bfyc", "八方预测" }, // 八方预测
			{ "bsls", "比赛历史" }, // 比赛历史
			{ "qpql", "球盘球路" }, // 球盘球路
			{ "opvar", "欧赔分析" },// 欧赔分析
			{ "opseq", "欧赔序列" } // 欧赔分析
	};

	public final static String[][] STAT_PAGE_TYPES = { 
			{ "corp", "公司分析" },
			{ "graph", "点数据分布分析"}};
	
	@Autowired
	private CompService compService;
	
	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private MatchService matchService;
	
	/**
	 * 获得数据分析页面
	 * 
	 * @param type
	 *            分析页面的类型
	 * @return
	 */
	@RequestMapping("/analysis")
	public ModelAndView getAnalysisPage(String page)
	{
		int index = getAnalysisPageIndex(page);
		List<CompSetting> settings = compService.list();
		ModelAndView view = new ModelAndView(ANALYSIS_PAGE_TYPES[index][0] + ".soccer");
		view.addObject("type", "analysis");
		view.addObject("page", ANALYSIS_PAGE_TYPES[index][0]);
		view.addObject("title", ANALYSIS_PAGE_TYPES[index][1]);
		view.addObject("issues", getLatestIssues(10));
		view.addObject("settings", settings);

		setUserObject(view);
		return view;
	}
	
	/**
	 * 联赛数据分析页面
	 * @param type 分析类型
	 * @param round
	 *            赛事轮次
	 * @return
	 */
	@RequestMapping("/analeague")
	public ModelAndView getLeagueAnalysisPage(String page, Round round, String source)
	{
		if(StringUtils.isEmpty(round.getLid()) || StringUtils.isEmpty(round.getSeason()) ||
				StringUtils.isEmpty(round.getRound()))
		{
			throw new ParamsException("Illegal params be set.");
		}
		int index = getLeaguePageIndex(page);
		List<CompSetting> settings = compService.list();
		League league = leagueService.getLeague(round.getLid());
		ModelAndView view = new ModelAndView(LEAGUE_PAGE_TYPES[index][0] + ".soccer");
		view.addObject("type", "league");
		view.addObject("page", LEAGUE_PAGE_TYPES[index][0]);
		view.addObject("title", LEAGUE_PAGE_TYPES[index][1]);
		view.addObject("league", league);
		view.addObject("round", round);
		view.addObject("settings", settings);

		setUserObject(view);
		return view;
	}
	
	/**
	 * 获得比赛分析页面
	 * 
	 * @param page 类型
	 * @param mid 比赛编号
	 * @return 分析页面
	 */
	@RequestMapping("/match")
	public ModelAndView getMatchPage(String page, String mid)
	{
		int index = getMatchPageIndex(page);
		MatchInfo match = matchService.getMatchInfo(mid);
		if (match == null)
		{
			throw new ParamsException("Illegal params be set, there are no match data in database.");
		}

		//List<RankInfo> ranks = soccerManager.getMatchTeamLatestRank(match);
		//RankElement rankElement = new RankElement(match, ranks);
		// logger.info(ranks);
		ModelAndView view = new ModelAndView("match" + MATCH_PAGE_TYPES[index][0] + ".soccer");
		view.addObject("type", "match");
		view.addObject("page", MATCH_PAGE_TYPES[index][0]);
		view.addObject("title", MATCH_PAGE_TYPES[index][1]);
		view.addObject("match", match);
		//view.addObject("rank", rankElement);

		setUserObject(view);

		return view;
	}
	
	/**
	 * 查看关联的比赛
	 * 
	 * @return
	 */
	@RequestMapping("/matchrel")
	public ModelAndView getMatchRelation()
	{
		List<CompSetting> settings = compService.list();
		ModelAndView view = new ModelAndView("matchrel.soccer");
		view.addObject("type", "analysis");
		view.addObject("issues", getLatestIssues(10));
		view.addObject("settings", settings);

		setUserObject(view);
		return view;
	}
	
	/**
	 * 加入用户信息
	 * 
	 * @param view
	 */
	protected void setUserObject(ModelAndView view)
	{
		User user = (User) getHttpServletRequest().getSession().getAttribute(Constants.SESSION_USER);
		if (user != null)
		{
			view.addObject("user", user);
		}
	}
	
	/**
	 * 处理档期列表数据
	 * 
	 * @param issues
	 * @param curissue
	 */
	protected List<String> getIssueList(String curIssue)
	{
		List<String> issues = new ArrayList<>();
		Date date = DateUtil.tryToParseDate(curIssue);

		List<Date> dates = DateUtil.getDates(date, -5);
		for (Date date2 : dates)
		{
			issues.add(DateUtil.formatDay(date2));
		}

		return issues;
	}

	/**
	 * 获得页面的序号
	 * 
	 * @param type
	 * @param names
	 * @return
	 */
	protected int getPageIndex(String type, String[][] names)
	{
		if (StringUtils.isEmpty(type))
		{
			return 0;
		}
		for (int i = 0; i < names.length; i++)
		{
			if (type.equalsIgnoreCase(names[i][0]))
			{
				return i;
			}
		}
		try
		{
			int index = Integer.parseInt(type);
			if (index >= names.length)
			{
				index = 0;
			}
			return index;
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	/**
	 * 解析分析数据页面
	 * 
	 * @param type
	 *            页面类型
	 * @return 页面名称
	 */
	public int getAnalysisPageIndex(String type)
	{
		return getPageIndex(type, ANALYSIS_PAGE_TYPES);
	}

	/**
	 * 解析比赛页面序号
	 * 
	 * @param type
	 * @return
	 */
	public int getMatchPageIndex(String type)
	{
		return getPageIndex(type, MATCH_PAGE_TYPES);
	}

	/**
	 * 解析比赛页面序号
	 * 
	 * @param type
	 * @return
	 */
	public int getLeaguePageIndex(String type)
	{
		return getPageIndex(type, LEAGUE_PAGE_TYPES);
	}

	/**
	 * 解析比赛页面序号
	 * 
	 * @param type
	 * @return
	 */
	public int getStatPageIndex(String type)
	{
		return getPageIndex(type, STAT_PAGE_TYPES);
	}

	/**
	 * 获得最近的几期数据
	 * 
	 * @param size
	 * @return
	 */
	protected List<String> getLatestIssues(int size)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		List<String> issues = new ArrayList<>();

		calendar.add(Calendar.DAY_OF_YEAR, 2);
		issues.add(DateUtil.formatDay(calendar.getTime()));
		for (int i = 0; i < size; i++)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			issues.add(DateUtil.formatDay(calendar.getTime()));
		}
		return issues;
	}
}
