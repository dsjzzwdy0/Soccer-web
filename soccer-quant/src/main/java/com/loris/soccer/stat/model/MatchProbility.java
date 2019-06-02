/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @MatchProbility.java   
 * @Package com.loris.soccer.stat.modelcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.stat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loris.common.bean.AutoIdEntity;
import com.loris.common.util.NumberUtil;
import com.loris.soccer.util.PossionUtil;

/**   
 * @ClassName:  MatchProbility.java   
 * @Description: 比赛数据   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class MatchProbility extends AutoIdEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String mid;
	protected double[] predictProbs;
	protected double[][] probsMatrix;
	
	/**
	 * Create a new instance of MatchProbility
	 */
	public MatchProbility()
	{
	}
	
	/**
	 * Create a new instance of MatchProbility
	 * @param mid
	 */
	public MatchProbility(String mid)
	{
		this.mid = mid;
	}
	
	/**
	 * Create a new instance of MatchProbility
	 * @param mid
	 */
	public MatchProbility(String mid, TeamCapability homeTeam, TeamCapability clientTeam)
	{
		this.mid = mid;
		this.setTeamCapability(homeTeam, clientTeam);
	}
	
	/**
	 * 设置期望值
	 * @param homeGoal
	 * @param clientGoal
	 */
	public void setExpectGoal(float winExpectGoal, float loseExpectGoal)
	{
		int k = PossionUtil.POSSION_K;
		this.probsMatrix = PossionUtil.computeProb(winExpectGoal, loseExpectGoal, k);
		this.predictProbs = PossionUtil.computeOddsProb(probsMatrix, k);	
	}
	
	/**
	 * 设置球队战力值
	 * @param homeTeam
	 * @param clientTeam
	 */
	public void setTeamCapability(TeamCapability homeTeam, TeamCapability clientTeam)
	{
		float winExpectGoal = homeTeam.getExpectGoal();
		float loseExpectGoal = clientTeam.getExpectGoal();
		System.out.println("Home: " + homeTeam);
		System.out.println("Client: " + clientTeam);
		this.setExpectGoal(winExpectGoal, loseExpectGoal);
	}
	
	/**
	 * @return the mid
	 */
	public String getMid()
	{
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(String mid)
	{
		this.mid = mid;
	}
	/**
	 * @return the predictProbs
	 */
	public double[] getPredictProbs()
	{
		return predictProbs;
	}
	/**
	 * @param predictProbs the predictProbs to set
	 */
	public void setPredictProbs(double[] predictProbs)
	{
		this.predictProbs = predictProbs;
	}
	/**
	 * @return the probsMatrix
	 */
	public double[][] getProbsMatrix()
	{
		return probsMatrix;
	}
	/**
	 * @param probsMatrix the probsMatrix to set
	 */
	public void setProbsMatrix(double[][] probsMatrix)
	{
		this.probsMatrix = probsMatrix;
	}
	
	@JsonIgnore
	public double getWinprob()
	{
		return getProb(0);
	}
	@JsonIgnore
	public double getDrawprob()
	{
		return getProb(1);
	}
	@JsonIgnore
	public double getLoseprob()
	{
		return getProb(2);
	}
	/**
	 * 获得概率的值
	 * @param index
	 * @return
	 */
	protected double getProb(int index)
	{
		if(predictProbs == null)
			throw new IllegalArgumentException("Error, the predict prob is not set.");
		else if(index >= predictProbs.length)
			throw new IllegalArgumentException("Error, the index value of " + index + " exceed the predict probs value.");
		return predictProbs[index] * 100.0f;
	}

	/**
	 *  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MatchProbility [mid=" + mid + ", predictProbs=[" + NumberUtil.formatDouble(2, predictProbs[0])
				+ ", " + NumberUtil.formatDouble(2, predictProbs[1])
				+ ", " + NumberUtil.formatDouble(2, predictProbs[2]) + "]";
	}
}
