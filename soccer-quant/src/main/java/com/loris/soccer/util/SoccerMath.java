/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @SoccerMath.java   
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
 * @ClassName:  SoccerMath.java   
 * @Description: 数学计算公式工具
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class SoccerMath
{
	/**
	 * sigmoid函数计算
	 * @param value 值
	 * @return sigmoid的值
	 */
	public static double sigmoid(double value)
	{
		double ey = Math.pow(Math.E, -value);
        double result = 1.0 / (1.0 + ey);
        return result;
	}
	
	/**
	 * Tanh函数计算
	 * @param value
	 * @return
	 */
	public static double tanh(double value)
	{
		double ex = Math.pow(Math.E, value);// e^x
        double ey = Math.pow(Math.E, -value);//e^(-x)
        double sinhx = ex-ey;
        double coshx = ex+ey;
        double result = sinhx/coshx;
        return result;
	}
}
