/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MatchOdds.java   
 * @Package com.loris.soccer.model.complexcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.model.complex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.loris.soccer.constant.SoccerConstants;
import com.loris.soccer.model.OddsOp;
import com.loris.soccer.model.OddsYp;
import com.loris.soccer.model.RecordOddsOp;
import com.loris.soccer.model.RecordOddsYp;
import com.loris.soccer.model.complex.item.OddsItem;
import com.loris.soccer.model.view.IssueMatchInfo;
import com.loris.soccer.model.view.MatchInfo;

/**   
 * @ClassName:  MatchOdds.java   
 * @Description: 比赛与赔率信息  
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchOdds
{
	protected String ordinary;
	protected String issue;			//期号
	protected String mid;			//比赛编号
	protected String lid;
	protected String leaguename;
	protected String season;
	protected String round;
	protected String homeid;
	protected String homename;
	protected String homerank;
	protected String clientid;
	protected String clientname;
	protected String clientrank;
	protected Date matchtime;
	protected Date closetime;
	protected List<OddsItem> odds = new ArrayList<>();
	
	public MatchOdds()
	{
	}
	
	public MatchOdds(IssueMatchInfo matchInfo)
	{
		this.setIssueMatchInfo(matchInfo);
	}
	
	public MatchOdds(int index, MatchInfo matchInfo)
	{
		this.setMatchInfo(index, matchInfo);
	}
	
	public String getOrdinary()
	{
		return ordinary;
	}
	public void setOrdinary(String ordinary)
	{
		this.ordinary = ordinary;
	}
	public String getIssue()
	{
		return issue;
	}
	public void setIssue(String issue)
	{
		this.issue = issue;
	}
	public String getMid()
	{
		return mid;
	}
	public void setMid(String mid)
	{
		this.mid = mid;
	}
	public String getLid()
	{
		return lid;
	}
	public void setLid(String lid)
	{
		this.lid = lid;
	}
	public String getLeaguename()
	{
		return leaguename;
	}
	public void setLeaguename(String leaguename)
	{
		this.leaguename = leaguename;
	}
	public String getSeason()
	{
		return season;
	}
	public void setSeason(String season)
	{
		this.season = season;
	}
	public String getRound()
	{
		return round;
	}
	public void setRound(String round)
	{
		this.round = round;
	}
	public String getHomeid()
	{
		return homeid;
	}
	public void setHomeid(String homeid)
	{
		this.homeid = homeid;
	}
	public String getHomename()
	{
		return homename;
	}
	public void setHomename(String homename)
	{
		this.homename = homename;
	}
	public String getHomerank()
	{
		return homerank;
	}
	public void setHomerank(String homerank)
	{
		this.homerank = homerank;
	}
	public String getClientid()
	{
		return clientid;
	}
	public void setClientid(String clientid)
	{
		this.clientid = clientid;
	}
	public String getClientname()
	{
		return clientname;
	}
	public void setClientname(String clientname)
	{
		this.clientname = clientname;
	}
	public String getClientrank()
	{
		return clientrank;
	}
	public void setClientrank(String clientrank)
	{
		this.clientrank = clientrank;
	}
	public Date getMatchtime()
	{
		return matchtime;
	}
	public void setMatchtime(Date matchtime)
	{
		this.matchtime = matchtime;
	}
	public Date getClosetime()
	{
		return closetime;
	}
	public void setClosetime(Date closetime)
	{
		this.closetime = closetime;
	}
	public List<OddsItem> getOdds()
	{
		return odds;
	}
	public void setOdds(List<OddsItem> odds)
	{
		this.odds.addAll(odds);
	}
	public OddsItem getOddsItem(String corpid, String type)
	{
		for (OddsItem oddsItem : odds)
		{
			if(StringUtils.equals(corpid, oddsItem.getCorpid())
					&& StringUtils.equals(type, oddsItem.getType()))
			{
				return oddsItem;
			}
		}
		return null;
	}
	
	public void addOddsOp(OddsOp op)
	{
		OddsItem item = getOddsItem(op.getCorpid(), SoccerConstants.ODDS_TYPE_OP);
		if(item == null)
		{
			item = new OddsItem(op, SoccerConstants.ODDS_TYPE_OP);
			odds.add(item);
		}
		else
		{
			item.addOdds(op, SoccerConstants.ODDS_TYPE_OP);
		}
	}
	public void addRecordOddsOp(RecordOddsOp op)
	{
		OddsItem item = getOddsItem(op.getCorpid(), SoccerConstants.ODDS_TYPE_OP);
		if(item == null)
		{
			item = new OddsItem(op);
			odds.add(item);
		}
		else
		{
			item.addRecordOddsOp(op);
		}
	}
	public void addRecordOddsYp(RecordOddsYp yp)
	{
		OddsItem item = getOddsItem(yp.getCorpid(), SoccerConstants.ODDS_TYPE_OP);
		if(item == null)
		{
			item = new OddsItem(yp);
			odds.add(item);
		}
		else
		{
			item.addRecordOddsYp(yp);
		}
	}
	public void addOddsYp(OddsYp yp)
	{
		OddsItem item = getOddsItem(yp.getCorpid(), SoccerConstants.ODDS_TYPE_YP);
		if(item == null)
		{
			item = new OddsItem(yp, SoccerConstants.ODDS_TYPE_YP);
			odds.add(item);
		}
		else
		{
			item.addOdds(yp, SoccerConstants.ODDS_TYPE_YP);
		}
	}
	
	protected void setIssueMatchInfo(IssueMatchInfo matchInfo)
	{
		this.ordinary = matchInfo.getOrdinary();
		this.issue = matchInfo.getIssue();
		this.mid = matchInfo.getMid();
		this.lid = matchInfo.getLid();
		this.leaguename = matchInfo.getLeaguename();
		this.matchtime = matchInfo.getMatchtime();
		this.homeid = matchInfo.getHomeid();
		this.homename = matchInfo.getHomename();
		this.homerank = matchInfo.getHomerank();
		this.clientid = matchInfo.getClientid();
		this.clientname = matchInfo.getClientname();
		this.clientrank = matchInfo.getClientrank();
		this.season = matchInfo.getSeason();
		this.round = matchInfo.getRound();
		this.closetime = matchInfo.getClosetime();
	}
	protected void setMatchInfo(int index, MatchInfo matchInfo)
	{
		this.ordinary = index + "";
		this.issue = matchInfo.getRound();
		this.mid = matchInfo.getMid();
		this.lid = matchInfo.getLid();
		this.leaguename = matchInfo.getLeaguename();
		this.matchtime = matchInfo.getMatchtime();
		this.homeid = matchInfo.getHomeid();
		this.homename = matchInfo.getHomename();
		this.homerank = matchInfo.getHomerank();
		this.clientid = matchInfo.getClientid();
		this.clientname = matchInfo.getClientname();
		this.clientrank = matchInfo.getClientrank();
		this.season = matchInfo.getSeason();
		this.round = matchInfo.getRound();
	}
}
