/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @PossionUtil.java   
 * @Package com.loris.soccer.utilcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.util;

/**   
 * @ClassName:  PossionUtil.java   
 * @Description: 泊松分布计算   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
/**
 * 盘口数据计算器
 * @author deng
 *
 */
public class PossionUtil
{
	/** 泊松分布值 */
	public static final int POSSION_K = 7;
	
	/**
	 * 计算各种结果的比率
	 * @param lamda1 期望值1
	 * @param lamda2 期望值2
	 * @param k K值
	 * @return 结果
	 */
	public static double[] computeOddsProb(double lamda1, double lamda2)
	{
		return computeOddsProb(lamda1, lamda2, POSSION_K);
	}
	
	/**
	 * 计算各种结果的比率
	 * @param lamda1 期望值1
	 * @param lamda2 期望值2
	 * @param k K值
	 * @return 结果
	 */
	public static double[] computeOddsProb(double lamda1, double lamda2, int k)
	{
		double[][] p = computeProb(lamda1, lamda2, k);
		return computeOddsProb(p, k);
	}
	
	/**
	 * 计算赔率的值
	 * @param p
	 * @param k
	 * @return
	 */
	public static double[] computeOddsProb(double[][] p, int k)
	{
		double[] prob = new double[3];
		for(int i = 0; i < k; i ++)
		{
			for(int j = 0; j < k; j ++)
			{
				if(i > j)
    			{
    				prob[0] += p[i][j];
    			}
    			if(i == j)
    			{
    				prob[1] += p[i][j];
    			}
    			if(i < j)
    			{
    				prob[2] += p[i][j];
    			}
			}
		}
		return prob;
	}
	
	/**
	 * 计算各种可能的概率值
	 * @param lamda1 期望值1
	 * @param lamda2 期望值2
	 * @param k K值
	 * @return 概率分布
	 */
	public static double[][] computeProb(double lamda1, double lamda2, int k)
	{
		double[][] p = new double[k][k];
		
		double[] p1 = getPossoinProb(lamda1, k);
		double[] p2 = getPossoinProb(lamda2, k);
		for(int i = 0; i < k; i ++)
    	{
    		for(int j = 0; j < k; j ++)
    		{
    			p[i][j] = p1[i] * p2[j];
    		}
    	}
		return p;
	}
	
	/**
	 * 计算泊松分布概率数据
	 * @param lamda 期望值
	 * @return 概率分布数据
	 */
	public static double[] getPossoinProb(double lamda)
	{
		return getPossoinProb(lamda, POSSION_K);
	}
	
	/**
	 * 计算泊松分布概率数据
	 * @param lamda 期望值
	 * @param k 最大个数
	 * @return 概率分布数据
	 */
	public static double[] getPossoinProb(double lamda, int k)
	{
		double[] p = new double[k];
		double total = 0.0;
		for (int i = 0; i < k - 1; i ++)
		{
			p[i] = computePossion(lamda, i);
			total += p[i];
		}
		p[k - 1] = 1.0 - total;		
		return p;
	}
	
	/**
	 * 计算泊松分布的值
	 * @param lamda 期望值
	 * @param k K的值
	 * @return 分布概率
	 */
	public static double computePossion(double lamda, int k)
	{
		double c = Math.exp(-lamda), sigma = 1;
		for (int i = 1; i <= k; i++) {
			sigma *= lamda / i;
		}
		return sigma * c;
	}
	
	/**
	 * 计算期望Lamda期望值的情况下，分布值的数值
	 * @param lamda
	 * @return
	 */
	public static int getPossionVariable(double lamda, double prob) {
		int x = 0;
		double cdf = computePossion(lamda, 0);
		while (cdf < prob) {
			x++;
			cdf += computePossion(lamda, 0);
		}
		return x;
	}
}

