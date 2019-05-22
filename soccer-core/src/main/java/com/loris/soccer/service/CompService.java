/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @CompService.java   
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

import com.baomidou.mybatisplus.extension.service.IService;
import com.loris.soccer.model.CasinoComp;
import com.loris.soccer.model.CompSetting;

/**   
 * @ClassName:  CompService.java   
 * @Description: 博彩公司服务类  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface CompService extends IService<CompSetting>
{
	/**
	 * 插入数据列表
	 * @param comps
	 */
	boolean insertCasinoComps(List<CasinoComp> comps);
	
	/**
	 * 加入博彩公司列表
	 * @param comps
	 * @param overwrite
	 */
	boolean insertCasinoComps(List<CasinoComp> comps, boolean overwrite);
	
	/**
	 * 获得博彩公司列表
	 * @return
	 */
	List<CasinoComp> getCasinoComps();
	
	/**
	 * 获得博彩公司列表
	 * @param type 类型
	 * @return
	 */
	List<CasinoComp> getCasinoComps(String type);
}
