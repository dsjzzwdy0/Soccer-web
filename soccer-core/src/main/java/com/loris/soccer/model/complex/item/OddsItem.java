/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  OddsItem.java   
 * @Package com.loris.soccer.model.complex.item   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model.complex.item;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.loris.common.util.DateUtil;
import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsYp;
import com.loris.soccer.model.RecordOddsOp;
import com.loris.soccer.model.RecordOddsYp;
import com.loris.soccer.model.base.OddsValue;

/**   
 * @ClassName:  OddsItem    
 * @Description: 赔率值  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsItem
{
	protected String corpid;
	protected String type;
	protected Date firsttime;
	protected Date lasttime;
	protected float[] values = new float[6];
	
	public OddsItem(String corpid, String type)
	{
		this.corpid = corpid;
		this.type = type;
	}
	
	public OddsItem(OddsValue odds, String type)
	{
		this.corpid = odds.getCorpid();
		this.type = type;
		addOdds(odds, type);
	}
	
	public OddsItem(RecordOddsOp op)
	{
		addRecordOddsOp(op);
	}
	
	public OddsItem(RecordOddsYp yp)
	{
		addRecordOddsYp(yp);
	}
	
	public String getCorpid()
	{
		return corpid;
	}
	public void setCorpid(String corpid)
	{
		this.corpid = corpid;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Date getFirsttime()
	{
		return firsttime;
	}
	public void setFirsttime(Date firsttime)
	{
		this.firsttime = firsttime;
	}
	public Date getLasttime()
	{
		return lasttime;
	}
	public void setLasttime(Date lasttime)
	{
		this.lasttime = lasttime;
	}
	public float[] getValues()
	{
		return values;
	}
	public void setValues(float[] values)
	{
		this.values = values;
	}
	
	public void addRecordOddsOp(RecordOddsOp op)
	{
		this.corpid = op.getCorpid();
		this.type = SoccerConstants.ODDS_TYPE_OP;
		if(firsttime == null || DateUtil.compareDate(firsttime, op.getFirsttime()) > 0)
		{
			firsttime = op.getFirsttime();
			values[0] = op.getFirstwinodds();
			values[1] = op.getFirstdrawodds();
			values[2] = op.getFirstloseodds();
		}
		if(lasttime == null || DateUtil.compareDate(lasttime, op.getOpentime()) < 0)
		{
			lasttime = op.getOpentime();
			values[3] = op.getWinodds();
			values[4] = op.getDrawodds();
			values[5] = op.getLoseodds();
		}
	}
	
	public void addRecordOddsYp(RecordOddsYp yp)
	{
		this.corpid = yp.getCorpid();
		this.type = SoccerConstants.ODDS_TYPE_YP;
		if(firsttime == null || DateUtil.compareDate(firsttime, yp.getFirsttime()) > 0)
		{
			firsttime = yp.getFirsttime();
			values[0] = yp.getFirstwinodds();
			values[1] = yp.getFirsthandicap();
			values[2] = yp.getFirstloseodds();
		}
		if(lasttime == null || DateUtil.compareDate(lasttime, yp.getOpentime()) < 0)
		{
			lasttime = yp.getOpentime();
			values[3] = yp.getWinodds();
			values[4] = yp.getHandicap();
			values[5] = yp.getLoseodds();
		}
	}
	
	public void addOdds(OddsValue oddsValue, String type)
	{
		if(StringUtils.equals(SoccerConstants.ODDS_TYPE_OP, type))
		{
			OddsOp op = (OddsOp)oddsValue;
			if(firsttime == null || DateUtil.compareDate(firsttime, op.getOpentime()) > 0)
			{
				firsttime = op.getOpentime();
				setOp(op, 0);
			}
			if(lasttime == null || DateUtil.compareDate(lasttime, op.getOpentime()) < 0)
			{
				lasttime = op.getOpentime();
				setOp(op,  3);
			}
		}
		else if(StringUtils.equals(SoccerConstants.ODDS_TYPE_YP, type))
		{
			OddsYp yp = (OddsYp) oddsValue;
			if(firsttime == null || DateUtil.compareDate(firsttime, yp.getOpentime()) > 0)
			{
				firsttime = yp.getOpentime();
				setYp(yp, 0);
			}
			if(lasttime == null || DateUtil.compareDate(lasttime, yp.getOpentime()) < 0)
			{
				lasttime = yp.getOpentime();
				setYp(yp,  3);
			}
		}
	}
	public void addOdds(OddsValue oddsValue)
	{
		if(oddsValue instanceof OddsOp)
		{
			addOdds(oddsValue, SoccerConstants.ODDS_TYPE_OP);
		}
		else if(oddsValue instanceof OddsYp)
		{
			addOdds(oddsValue, SoccerConstants.ODDS_TYPE_YP);
		}
	}
	
	protected void setOp(OddsOp op, int index)
	{
		values[index + 0] = op.getWinodds();
		values[index + 1] = op.getDrawodds();
		values[index + 2] = op.getLoseodds();
	}
	protected void setYp(OddsYp yp, int index)
	{
		values[index + 0] = yp.getWinodds();
		values[index + 1] = yp.getHandicap();
		values[index + 2] = yp.getLoseodds();
	}
}
