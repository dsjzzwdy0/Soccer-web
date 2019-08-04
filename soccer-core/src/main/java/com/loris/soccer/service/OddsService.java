/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsOpService.java   
 * @Package com.loris.soccer   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:33:06   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service;

import java.util.List;

import com.loris.soccer.model.CompSetting;
import com.loris.soccer.model.OddsNum;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.RecordOddsOp;
import com.loris.soccer.model.RecordOddsYp;
import com.loris.soccer.model.view.RecordOddsOpInfo;
import com.loris.soccer.model.view.RecordOddsYpInfo;
import com.loris.soccer.model.OddsScore;
import com.loris.soccer.model.OddsYp;

/**   
 * @ClassName:  OddsOpService   
 * @Description: 欧赔数据业务查询服务 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:33:06   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface OddsService
{
	/**
	 * 保存欧赔数据
	 * @param op 欧赔数据
	 * @return 保存成功的标志
	 */
	boolean save(OddsOp op);
	
	/**
	 * 通过比赛编号获得欧赔数据列表
	 * @param mid 比赛编号
	 * @return 欧赔数据列表
	 */
	List<OddsOp> selectOddsOp(String mid);
	
	/**
	 * 通过比赛编号与博彩公司的编号获得欧赔数据列表
	 * @param mid 比赛编号
	 * @param corpid 博彩公司编号
	 * @return 欧赔数据列且
	 */
	List<OddsOp> selectOddsOp(String mid, String corpid);
	
	/**
	 * 获得所有的欧赔数据
	 * @param mids 比赛编号
	 * @param corpids 博彩公司编号
	 * @return
	 */
	List<OddsOp> selectOddsOps(List<String> mids, List<String> corpids);
	
	/**
	 * 通过比赛编号获得欧赔数据的列表
	 * @param mid 比赛编号
	 * @return 数据列表
	 */
	List<RecordOddsOp> selectOddsOpRecords(String mid);
	
	/**
	 * 插入欧赔数据列表
	 * @param ops 欧赔数据列表
	 * @return 是否成功的标志
	 */
	boolean insertOddsOps(List<OddsOp> ops);
	
	/**
	 * 插入欧赔数据列表
	 * @param ops 欧赔数据
	 * @param overwrite 是否覆盖
	 * @return 是否成功的标志
	 */
	boolean insertOddsOps(List<OddsOp> ops, boolean overwrite);
	
	/**
	 * 插入亚盘数据列表
	 * @param oddsYps 亚盘数据列表
	 * @return 是否成功
	 */
	boolean insertOddsYps(List<OddsYp> oddsYps);
	
	/**
	 * 插入亚盘数据列表
	 * @param oddsYps 亚盘数据列表
	 * @param overwrite 是否覆盖
	 * @return 是否成功
	 */
	boolean insertOddsYps(List<OddsYp> oddsYps, boolean overwrite);
	
	/**
	 * 获得所有的欧赔数据
	 * @param mids 比赛编号
	 * @param corpids 公司编号
	 * @return
	 */
	List<OddsYp> selectOddsYps(List<String> mids, List<String> corpids);
	
	
	/**
	 * 插入大小比分据列表
	 * @param oddsYps 大小球数据列表
	 * @return 是否成功
	 */
	boolean insertOddsNums(List<OddsNum> oddsNums);
	
	/**
	 * 插入大小比分据列表
	 * @param oddsYps 大小球数据列表
	 * @param overwrite 是否覆盖
	 * @return 是否成功
	 */
	boolean insertOddsNums(List<OddsNum> oddsNums, boolean overwrite);
	
	/**
	 * 插入比分数据
	 * @param oddsScores 比分数据
	 * @return 是否成功
	 */
	boolean insertOddsScores(List<OddsScore> oddsScores);
	
	/**
	 * 插入比分数据
	 * @param oddsScores 比分数据
	 * @param overwrite 是否覆盖
	 * @return 是否成功
	 */
	boolean insertOddsScores(List<OddsScore> oddsScores, boolean overwrite);
	
	/**
	 * 插入欧赔记录数据
	 * @param ops 欧赔数据
	 * @return 是否成功的标志
	 */
	boolean insertRecordOddsOps(List<RecordOddsOp> ops);
	
	/**
	 * 插入欧赔数据记录
	 * @param ops 欧赔数据
	 * @param overwrite 是否覆盖
	 * @return
	 */
	boolean insertRecordOddsOps(List<RecordOddsOp> ops, boolean overwrite);
	
	/**
	 * 获得欧赔率的值
	 * @param mids
	 * @param corpids
	 * @return
	 */
	List<RecordOddsOp> getRecordOddsOps(List<String> mids, List<String> corpids);
	
	/**
	 * 获得初始和最新的欧赔值
	 * @param mids
	 * @param compSetting
	 * @return
	 */
	List<RecordOddsOpInfo> getRecordOddsOps(List<String> mids, CompSetting compSetting);
	
	/**
	 * 插入亚盘数据记录
	 * @param yps 亚盘数据
	 * @return 是不成功的标志
	 */
	boolean insertRecordOddsYps(List<RecordOddsYp> yps);
	
	/**
	 * 插入亚盘数据记录
	 * @param yps 亚盘数据
	 * @param overwrite 是否覆盖
	 * @return 是不成功的标志
	 */
	boolean insertRecordOddsYps(List<RecordOddsYp> yps, boolean overwrite);
	
	/**
	 * 获得亚盘赔率的值
	 * @param mids
	 * @param corpids
	 * @return
	 */
	List<RecordOddsYp> getRecordOddsYps(List<String> mids, List<String> corpids);
	
	/**
	 * 获得亚盘最新和初始赔率的值
	 * @param mids
	 * @param corpids
	 * @return
	 */
	List<RecordOddsYpInfo> getRecordOddsYps(List<String> mids, CompSetting compSetting);
	
	/**
	 * 更新数据列表
	 */
	void updateOpList();
}
