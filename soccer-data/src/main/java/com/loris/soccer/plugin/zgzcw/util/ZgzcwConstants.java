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
package com.loris.soccer.plugin.zgzcw.util;

import static com.loris.soccer.constant.SoccerConstants.*;
/**   
 * @ClassName:  League   
 * @Description: 中国足彩网的常量数据定义类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface ZgzcwConstants
{
	/** 数据来源定义 */
	public static final String SOURCE_ZGZCW = "zgzcw";
	
	/** 数据页面名称定义 */
	final static public String PAGE_LEAGUE_LEAGUE_ROUND = "leagueround";
	final static public String PAGE_LEAGUE_LEAGUE = LEAGUE_TYPE_LEAGUE;
	final static public String PAGE_LEAGUE_CUP = LEAGUE_TYPE_CUP;
	final static public String PAGE_CENTER = "center";
	final static public String PAGE_ODDS_OP = "op";
	final static public String PAGE_ODDS_YP = "yp";
	final static public String PAGE_ODDS_NUM = "num";
	final static public String PAGE_LOTTERY_JC = "jc";
	final static public String PAGE_LOTTERY_BD = "bd";
	final static public String PAGE_LOTTERY_ZC = "zc";
	final static public String PAGE_CALENDAR = "calendar";
	final static public String PAGE_SCORE_BD = "bdscore";
	final static public String PAGE_SCORE_JC = "jcscore";
}
