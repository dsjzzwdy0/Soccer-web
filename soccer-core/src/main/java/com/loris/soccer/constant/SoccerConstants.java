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
package com.loris.soccer.constant;

/**
 * @ClassName: League
 * @Description: 足彩应用系统中一些常量数据的定义
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface SoccerConstants
{	
	final static public String SOURCE_ZGZCW = "zgzcw";
	final static public String SOURCE_OKOOO = "okooo";
	
	/** 缓存的名称 */
	final static public String CAHE_ODDS_NAME = "SoccerCache";
	
	/** 数据SoccerData TableRecords关键字定义 */
	final static public String SOCCER_DATA_LEAGUE = "league";
	final static public String SOCCER_DATA_LEAGUE_LIST = "leagues";
	final static public String SOCCER_DATA_LEAGUE_NAME_LIST = "leaguenames";
	final static public String SOCCER_DATA_LEAGUE_ROUND_LIST = "roundlist";
	final static public String SOCCER_DATA_LEAGUE_SEASON_LIST = "seasonlist";
	final static public String SOCCER_DATA_LEAGUE_RANK_LIST = "ranklist";
	final static public String SOCCER_DATA_LEAGUE_ROUND_CUR = "curround";
	final static public String SOCCER_DATA_TEAM_LIST = "teamlist";
	final static public String SOCCER_DATA_TEAM_SEASON_RELATION = "teamrfseason";
	final static public String SOCCER_DATA_MATCH = "match";
	final static public String SOCCER_DATA_MATCH_LIST = "matchs";
	final static public String SOCCER_DATA_MATCH_BD_LIST = "matchbds";
	final static public String SOCCER_DATA_MATCH_JC_LIST = "matchjcs";
	final static public String SOCCER_DATA_MATCH_RESULT = "result";
	final static public String SOCCER_DATA_MATCH_RESULT_LIST = "resultlist";
	final static public String SOCCER_DATA_ODDS_NUM = "oddsnum";
	final static public String SOCCER_DATA_LOGO_LIST = "logolist";
	final static public String SOCCER_DATA_ODDS_OP_LIST = "oplist";
	final static public String SOCCER_DATA_ODDS_YP_LIST = "yplist";
	final static public String SOCCER_DATA_ODDS_NUM_LIST = "numlist";
	final static public String SOCCER_DATA_RECORD_ODDS_OP_LIST = "recoplist";
	final static public String SOCCER_DATA_RECORD_ODDS_YP_LIST = "recyplist";
	final static public String SOCCER_DATA_ODDS_SCORE_LIST = "scorelist";
	final static public String SOCCER_DATA_CASINO_COMP_LIST = "casinocomps";
	final static public String SOCCER_DATA_BETODDS_BD_LIST = "betbdodds";
	final static public String SOCCER_DATA_BETODDS_JC_LIST = "betjcodds";
	
	final static public String SOCCER_DATA_MATCH_OKOOO_BD_LIST = "okooo_matchbds";
	final static public String SOCCER_DATA_MATCH_OKOOO_JC_LIST = "okooo_matchjcs";
	final static public String SOCCER_DATA_MATCH_OKOOO_LIST = "okooo_matchs";
	final static public String SOCCER_DATA_LEAGUE_OKOOO_LIST = "okooo_leagues";
	final static public String SOCCER_DATA_ODDS_OKOOO_OP_LIST = "okooo_oplist";
	final static public String SOCCER_DATA_ODDS_OKOOO_YP_LIST = "okooo_yplist";
	final static public String SOCCER_DATA_CASINO_OKOOO_COMP_LIST = "okooo_casinocomps";
	final static public String SOCCER_DATA_TEAM_OKOOO_LIST = "okooo_teamlist";
	//final static public String SOCCER_DATA_ODDS_SCORE = "oddsscore";


	/** 北京单场 */
	final static public String LOTTERY_BD = "bd";

	/** 竞彩足球 */
	final static public String LOTTERY_JC = "jc";

	/** 足彩 */
	final static public String LOTTERY_ZC = "zc";

	/** 赛事类型是杯赛 */
	final static public String LEAGUE_TYPE_LEAGUE = "league";

	/** 赛事类型是杯赛 */
	final static public String LEAGUE_TYPE_CUP = "cup";

	/** 数据亚盘 */
	final static public String ODDS_TYPE_YP = "yp";

	/** 欧赔数据 */
	final static public String ODDS_TYPE_OP = "op";

	/** 比分数据 */
	final static public String ODDS_TYPE_SCORE = "score";

	/** 大小球数 */
	final static public String ODDS_TYPE_NUM = "num";
	
	/** 关键字名称 */
	final static public String NAME_FIELD_MID = "mid";
	final static public String NAME_FIELD_LID = "lid";
	final static public String NAME_FIELD_MATCHTIME = "matchtime";
	final static public String NAME_FIELD_SEASON = "season";
	final static public String NAME_FIELD_ISSUE = "issue";
	final static public String NAME_FIELD_TID = "tid";
	final static public String NAME_FIELD_ROUND = "round";
	final static public String NAME_FIELD_CORPID = "corpid";
	final static public String NAME_FIELD_PAGE = "page";
	final static public String NAME_FIELD_SOURCE_ID = "sourceid";
	
	/** 图标的类型 */
	final static public String LOGO_TYPE_LEAGUE = "league";
	final static public String LOGO_TYPE_TEAM = "team";
	
	/** 球队排名的类型 */
	final static public String RANK_TOTAL = "total";
	final static public String RANK_HOME= "home";
	final static public String RANK_CLIENT = "guest";
	final static public String RANK_LATEST_6 = "r6";
	final static public String RANK_FIRST_HALF = "firsthalf";
	final static public String RANK_SECOND_HALF = "secondhalf";
}
