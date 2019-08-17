/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OddsUtil.java   
 * @Package com.loris.soccer.data.utilcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.data.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsYp;
import com.loris.soccer.model.RecordOddsOp;
import com.loris.soccer.model.RecordOddsYp;

/**   
 * @ClassName:  OddsUtil.java   
 * @Description: 赔率数据的处理 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
public class OddsUtil
{
	/**
	 * 创建RecordOddsOp
	 * @param ops 欧赔数据列表
	 * @return 数据列表
	 */
	public static DataList<RecordOddsOp> createRecordOddsOp(List<? extends OddsOp> ops)
	{
		DataList<RecordOddsOp> recordOddsOps = new DataList<>();
		for (OddsOp op : ops)
		{
			RecordOddsOp record = null;
			
			//查找是否存在的数据
			for (RecordOddsOp recordOddsOp : recordOddsOps)
			{
				if(StringUtils.equals(op.getMid(), recordOddsOp.getMid()) &&
						StringUtils.equals(op.getCorpid(), recordOddsOp.getCorpid()) &&
						StringUtils.equals(op.getSource(), recordOddsOp.getSource()))
				{
					record = recordOddsOp;
					break;
				}
			}
			
			//如果没有存在的数据，则新创建一个数据记录
			if(record == null)
			{
				record = new RecordOddsOp(op);
				recordOddsOps.add(record);
			}
			else
			{
				record.addOddsOp(op);
			}
		}		
		return recordOddsOps;
	}
	
	/**
	 * 创建RecordOddsOp
	 * @param ops 欧赔数据列表
	 * @return 数据列表
	 */
	public static DataList<RecordOddsYp> createRecordOddsYp(List<? extends OddsYp> yps)
	{
		DataList<RecordOddsYp> recordOddsOps = new DataList<>();
		for (OddsYp yp : yps)
		{
			RecordOddsYp record = null;
			
			//查找是否存在的数据
			for (RecordOddsYp recordOddsYp : recordOddsOps)
			{
				if(StringUtils.equals(yp.getMid(), recordOddsYp.getMid()) &&
						StringUtils.equals(yp.getCorpid(), recordOddsYp.getCorpid()) &&
						StringUtils.equals(yp.getSource(), recordOddsYp.getSource()))
				{
					record = recordOddsYp;
					break;
				}
			}
			
			//如果没有存在的数据，则新创建一个数据记录
			if(record == null)
			{
				record = new RecordOddsYp(yp);
				recordOddsOps.add(record);
			}
			else
			{
				record.addOddsYp(yp);
			}
		}		
		return recordOddsOps;
	}
}
