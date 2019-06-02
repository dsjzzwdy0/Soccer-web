/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OpList.java   
 * @Package com.loris.soccer.collection   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.collection;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.util.DateUtil;
import com.loris.soccer.collection.base.DataList;
import com.loris.soccer.model.OddsOp;

/**   
 * @ClassName: OpList   
 * @Description: 欧赔数据的列表 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsOpList extends DataList<OddsOp>
{
	/**
	 *
	 */
	public static enum OddsOpListType
	{
		ALL,
		OnlyFirst,
		OnlyLast
	}
	
	/***/
	private static final long serialVersionUID = 1L;

	/**
	 * 创建欧赔列表数据
	 */
	public OddsOpList()
	{
	}
	
	/**
	 * 创建欧赔列表数据
	 * @param ops
	 */
	public OddsOpList(List<OddsOp> ops)
	{
		this.addAll(ops);
	}
	
	/**
	 * 欧赔数据列表
	 * @param ops
	 * @param type
	 */
	public OddsOpList(List<OddsOp> ops, OddsOpListType type)
	{
		if(type == OddsOpListType.ALL)
		{
			this.addAll(ops);
		}
		else
		{
			for(OddsOp op: ops)
			{
				this.addOddsOp(op, type);
			}
		}
	}
	
	/**
	 * 添加欧赔数据
	 * @param op
	 * @param type
	 * @return
	 */
	public boolean addOddsOp(OddsOp op, OddsOpListType type)
	{
		if(type == OddsOpListType.ALL)
		{
			return this.add(op);
		}
		OddsOp existOp = getOddsOp(op.getMid(), op.getCorpid());
		if(existOp == null)
		{
			return this.add(op);
		}
		else
		{
			int r = DateUtil.compareDate(existOp.getOpentime(), op.getOpentime());
			if(type == OddsOpListType.OnlyFirst)
			{
				if(r > 0)
				{
					this.remove(existOp);
					return this.add(op);
				}
			}
			else if(type == OddsOpListType.OnlyLast)
			{
				if(r < 0)
				{
					this.remove(existOp);
					return this.add(op);
				}
			}
		}
		return false;
	}
	
	/**
	 * 查找某场比赛的欧赔数据
	 * @param mid 比赛编号
	 * @param corpid 博彩公司编号
	 * @return 符合条件的欧赔数据
	 */
	public OddsOp getOddsOp(String mid, String corpid)
	{
		for (OddsOp op : this)
		{
			if(StringUtils.equals(op.getMid(), mid) && StringUtils.equals(op.getCorpid(), corpid))
				return op;
		}
		return null;
	}
	
	
	/**
	 * 查找最开始欧赔数据
	 * @param mid
	 * @param corpid
	 * @return
	 */
	public OddsOp getFirstOddsOp(String mid, String corpid)
	{
		return getOddsOp(mid, corpid, true);
	}
	
	/**
	 * 获得最新的欧赔数据
	 * @param mid
	 * @param corpid
	 * @return
	 */
	public OddsOp getLastOddsOp(String mid, String corpid)
	{
		return getOddsOp(mid, corpid, false);
	}
	
	/**
	 * 查找欧赔数据记录
	 * @param mid
	 * @param corpid
	 * @param first
	 * @return
	 */
	protected OddsOp getOddsOp(String mid, String corpid, boolean first)
	{
		OddsOp matchOp = null;
		for (OddsOp op : this)
		{
			if(StringUtils.equals(op.getMid(), mid) && StringUtils.equals(op.getCorpid(), corpid))
			{
				if(matchOp == null)
				{
					matchOp = op;
				}
				else
				{
					if(DateUtil.compareDate(matchOp.getOpentime(), op.getOpentime()) > 0)
					{
						if(first) matchOp = op;
					}
					else
					{
						if(!first) matchOp = op;
					}
				}
			}
		}
		return matchOp;
	}
}
