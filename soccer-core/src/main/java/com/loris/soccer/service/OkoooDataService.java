/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooDataService.java   
 * @Package com.loris.soccer.servicecom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service;

import java.util.List;

import com.loris.soccer.model.OkoooCasinoComp;
import com.loris.soccer.model.OkoooIssueMatch;
import com.loris.soccer.model.OkoooLeague;
import com.loris.soccer.model.OkoooMatch;
import com.loris.soccer.model.OkoooOddsOp;
import com.loris.soccer.model.OkoooOddsYp;

/**   
 * @ClassName:  OkoooDataService.java   
 * @Description: 澳客数据服务类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface OkoooDataService
{
	/**
	 * 插入联赛数据类
	 * 
	 * @param leagues 联赛数据列表
	 * @return 是否成功的标志
	 */
	boolean insertOkoooLeagues(List<OkoooLeague> leagues);
	
	/**
	 * 插入联赛数据
	 * @param leagues 联赛数据
	 * @param overwrite 是否覆盖数据
	 * @return 插入成功的标志
	 */
	boolean insertOkoooLeagues(List<OkoooLeague> leagues, boolean overwrite);
	
	/**
	 * 插入比赛数据
	 * @param matchs 比赛列表
	 * @return 是否成功的标志
	 */
	boolean insertOkoooMatchs(List<OkoooMatch> matchs);
	
	/**
	 * 插入比赛数据
	 * @param matchs 比赛列表
	 * @param overwrite 是否覆盖原有的数据
	 * @return 是否成功的标志
	 */
	boolean insertOkoooMatchs(List<OkoooMatch> matchs, boolean overwrite);
	
	/**
	 * 插入比赛期号的数据
	 * @param issueMatchs 开盘的数据
	 * @return 是否成功的标志
	 */
	boolean insertOkoooIssueMatch(List<OkoooIssueMatch> issueMatchs);
	
	/**
	 * 插入比赛期号的数据
	 * @param issueMatchs 开盘的数据
	 * @param overwrite 是否覆盖原有的数据
	 * @return 是否成功的标志
	 */
	boolean insertOkoooIssueMatch(List<OkoooIssueMatch> issueMatchs, boolean overwrite);
	
	/**
	 * 插入欧赔数据列表
	 * @param ops 欧赔列表
	 * @return 是否成功的标志
	 */
	boolean insertOkoooOddsOps(List<OkoooOddsOp> ops);
	
	/**
	 * 插入欧赔数据列表
	 * @param ops 欧赔列表
	 * @param overwrite 是否覆盖原有的数据
	 * @return 是否成功的标志
	 */
	boolean insertOkoooOddsOps(List<OkoooOddsOp> ops, boolean overwrite);
	
	/**
	 * 是否插入亚盘数据列表
	 * @param yps 亚盘列表
	 * @return 是否成功的标志
	 */
	boolean insertOkoooOddsYps(List<OkoooOddsYp> yps);
	
	/**
	 * 是否插入亚盘数据列表
	 * @param yps 亚盘列表
	 * @param overwrite 是否覆盖原有的数据
	 * @return 是否成功的标志
	 */
	boolean insertOkoooOddsYps(List<OkoooOddsYp> yps, boolean overwrite);
	
	/**
	 * 插入澳客博彩公司列表
	 * @param comps 公司列表
	 * @param overwrite 是否覆盖原有的数据
	 * @return 是否成功的标志
	 */
	boolean insertOkoooCasinoComps(List<OkoooCasinoComp> comps);
	
	/**
	 * 插入澳客博彩公司列表
	 * @param comps 公司列表
	 * @param overwrite 是否覆盖原有的数据
	 * @return 是否成功的标志
	 */
	boolean insertOkoooCasinoComps(List<OkoooCasinoComp> comps, boolean overwrite);
}
