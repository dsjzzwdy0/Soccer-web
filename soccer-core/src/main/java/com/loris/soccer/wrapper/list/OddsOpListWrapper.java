/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OddsOpListWrapper.java   
 * @Package com.loris.soccer.wrappercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.wrapper.list;

import java.util.ArrayList;
import java.util.List;

import com.loris.common.util.ArraysUtil;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.base.OddsValue;
import com.loris.soccer.wrapper.Wrapper;
import com.loris.soccer.wrapper.filter.OddsValueFilter;
import com.loris.soccer.wrapper.model.OddsOpRecord;

/**   
 * @ClassName:  OddsOpListWrapper.java   
 * @Description: 欧赔列表数据的包装类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsOpListWrapper implements Wrapper<List<OddsOp>>
{
	@Override
	public Object wrap(List<OddsOp> source)
	{
		List<OddsOpRecord> list = new ArrayList<>();
		OddsValueFilter filter = new OddsValueFilter();
		for (OddsOp oddsOp : source)
		{
			filter.setValue(oddsOp);
			OddsValue record = ArraysUtil.getSameObject(list, filter);
			if(record == null)
			{
				OddsOpRecord oddsOpRecord = new OddsOpRecord(oddsOp);
				list.add(oddsOpRecord);
			}
			else
			{
				OddsOpRecord oddsOpRecord = (OddsOpRecord) record;
				oddsOpRecord.addOddsOp(oddsOp);
			}
		}
		return list;
	}

}
