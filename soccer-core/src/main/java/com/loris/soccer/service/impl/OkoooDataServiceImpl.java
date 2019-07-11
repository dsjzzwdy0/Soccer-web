/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OkoooDataServiceImpl.java   
 * @Package com.loris.soccer.service.implcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.service.impl;

import org.springframework.stereotype.Service;

import com.loris.common.model.TableRecords;
import com.loris.soccer.service.OkoooDataService;

/**   
 * @ClassName:  OkoooDataServiceImpl.java   
 * @Description: 澳客数据服务类
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service("okoooDataService")
public class OkoooDataServiceImpl implements OkoooDataService
{

	/* (non-Javadoc)
	 * @see com.loris.soccer.service.OkoooDataService#saveTableRecords(com.loris.common.model.TableRecords)
	 */
	@Override
	public boolean saveTableRecords(TableRecords records)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
