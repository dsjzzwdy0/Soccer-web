/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.dict;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: League
 * @Description: 让球字典数据, 主要用于解决让球数与中文名称的映射关系
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class HandicapDict
{
	private static List<HandicapValue> values = new ArrayList<>();

	static
	{
		values.add(new HandicapValue("平手", 0.0f));
		values.add(new HandicapValue("平/半", 0.25f));
		values.add(new HandicapValue("半球", 0.5f));
		values.add(new HandicapValue("半/一", 0.75f));
		values.add(new HandicapValue("一球", 1.0f));
		values.add(new HandicapValue("一/球半", 1.25f));
		values.add(new HandicapValue("球半", 1.5f));
		values.add(new HandicapValue("球半/两", 1.75f));
		values.add(new HandicapValue("两球", 2.0f));
		values.add(new HandicapValue("两/两半", 2.25f));
		values.add(new HandicapValue("两半", 2.5f));
		values.add(new HandicapValue("两半/三", 2.75f));
		values.add(new HandicapValue("三球", 3.0f));
		values.add(new HandicapValue("受平/半", -0.25f));
		values.add(new HandicapValue("受半球", -0.5f));
		values.add(new HandicapValue("受半/一", -0.75f));
		values.add(new HandicapValue("受一球", -1.0f));
		values.add(new HandicapValue("受一/球半", -1.25f));
		values.add(new HandicapValue("受球半", -1.5f));
		values.add(new HandicapValue("受球半/两", -1.75f));
		values.add(new HandicapValue("受两球", -2.0f));
		values.add(new HandicapValue("受两/两半", -2.25f));
		values.add(new HandicapValue("受两半", -2.5f));
		values.add(new HandicapValue("受两半/三", -2.75f));
		values.add(new HandicapValue("受三球", -3.0f));
	}

	public static final String[][] HANDICAP_STANDRARS =
	{
			{ "平", "平手" },
			{ "平手/半球", "平/半" },
			{ "半球/一球", "半/一" },
			{ "一球/球半", "一/球半" },
			{ "球半/两球", "球半/两" },
			{ "两球/两球半", "两/两半" },
			{ "两球半/三球", "两半/三" },
			{ "两球半/三", "两半/三" },
			{ "球半/两球", "球半/两" },
			{ "受平手/半球", "受平/半" },
			{ "受半球/一球", "受半/一" },
			{ "受一球/球半", "受一/球半" },
			{ "受球半/两球", "受球半/两" },
			{ "受两球/两球半", "受两/两球半" },
			{ "受球半/两球", "受球半/两球" } };

	public static final String[] WATER_LEVEL =
	{ "超低水", "低水", "中低水", "中水", "中高水", "高水", "超高水" };
	
	public static final double[][] WATER_LEVEL_THRESHOLD =
	{
			{ 0.000, 0.75999 },
			{ 0.760, 0.84999 },
			{ 0.850, 0.89999 },
			{ 0.900, 0.94999 },
			{ 0.950, 0.99999 },
			{ 1.000, 1.99999 },
			{ 2.000, 10000.0 } 
	};

	public static final double[][] OP_YP_TABLE =
	{
			{ 2.500, 0.750, 2.550, 0.775, 2.600, 0.800, 2.650, 0.825, 2.700, 0.850, 2.750, 0.875, 2.800, 0.900, 2.850,
					0.925, 2.900, 0.950, 2.950, 0.975, 3.000, 1.000, 3.050, 1.025, 3.100, 1.050, 3.150, 1.075, 3.200,
					1.100 }, // 平手
			{ 2.000, 0.750, 2.030, 0.775, 2.070, 0.800, 2.100, 0.825, 2.130, 0.850, 2.170, 0.875, 2.200, 0.900, 2.230,
					0.925, 2.270, 0.950, 2.300, 0.975, 2.330, 1.000, 2.370, 1.025, 2.400, 1.050, 2.430, 1.075, 2.460,
					1.100 }, // 平半
			{ 1.750, 0.750, 1.780, 0.775, 1.800, 0.800, 1.830, 0.825, 1.850, 0.850, 1.880, 0.875, 1.900, 0.900, 1.930,
					0.925, 1.950, 0.950, 1.980, 0.975, 2.000, 1.000, 2.030, 1.025, 2.050, 1.050, 2.080, 1.075, 2.100,
					1.100 }, // 半球
			{ 1.500, 0.750, 1.520, 0.775, 1.530, 0.800, 1.550, 0.825, 1.570, 0.850, 1.580, 0.875, 1.600, 0.900, 1.620,
					0.925, 1.630, 0.950, 1.650, 0.975, 1.670, 1.000, 1.680, 1.025, 1.700, 1.050, 1.720, 1.075, 1.730,
					1.100 }, // 半/一
			{ 1.380, 0.750, 1.390, 0.775, 1.400, 0.800, 1.410, 0.825, 1.430, 0.850, 1.440, 0.875, 1.450, 0.900, 1.460,
					0.925, 1.480, 0.950, 1.490, 0.975, 1.500, 1.000, 1.510, 1.025, 1.530, 1.050, 1.540, 1.075, 1.550,
					1.100 }, // 一球
			{ 1.300, 0.750, 1.310, 0.775, 1.320, 0.800, 1.330, 0.825, 1.340, 0.850, 1.350, 0.875, 1.360, 0.900, 1.370,
					0.925, 1.380, 0.950, 1.390, 0.975, 1.400, 1.000, 1.410, 1.025, 1.420, 1.050, 1.430, 1.075, 1.440,
					1.100 }, // 一/球半
			{ 1.250, 0.750, 1.260, 0.775, 1.270, 0.800, 1.280, 0.825, 1.280, 0.850, 1.290, 0.875, 1.300, 0.900, 1.310,
					0.925, 1.320, 0.950, 1.330, 0.975, 1.330, 1.000, 1.340, 1.025, 1.350, 1.050, 1.360, 1.075, 1.370,
					1.100 }, // 球半
			{ 1.210, 0.750, 1.220, 0.775, 1.220, 0.800, 1.240, 0.825, 1.240, 0.850, 1.250, 0.875, 1.260, 0.900, 1.260,
					0.925, 1.270, 0.950, 1.280, 0.975, 1.290, 1.000, 1.290, 1.025, 1.300, 1.050, 1.310, 1.075, 1.310,
					1.100 }, // 球半/两
			{ 1.190, 0.750, 1.190, 0.775, 1.200, 0.800, 1.210, 0.825, 1.210, 0.850, 1.220, 0.875, 1.230, 0.900, 1.230,
					0.925, 1.240, 0.950, 1.240, 0.975, 1.250, 1.000, 1.260, 1.025, 1.260, 1.050, 1.270, 1.075, 1.280,
					1.100 }, // 两球
			{ 1.170, 0.750, 1.170, 0.775, 1.180, 0.800, 1.180, 0.825, 1.190, 0.850, 1.190, 0.875, 1.200, 0.900, 1.210,
					0.925, 1.210, 0.950, 1.220, 0.975, 1.220, 1.000, 1.230, 1.025, 1.230, 1.050, 1.240, 1.075, 1.240,
					1.100 }, // 两/两球半
			{ 1.150, 0.750, 1.150, 0.775, 1.160, 0.800, 1.160, 0.825, 1.170, 0.850, 1.170, 0.875, 1.180, 0.900, 1.190,
					0.925, 1.190, 0.950, 1.200, 0.975, 1.200, 1.000, 1.210, 1.025, 1.210, 1.050, 1.220, 1.075, 1.220,
					1.100 }, // 两球半
			{ 1.140, 0.750, 1.140, 0.775, 1.150, 0.800, 1.150, 0.825, 1.160, 0.850, 1.160, 0.875, 1.160, 0.900, 1.170,
					0.925, 1.170, 0.950, 1.180, 0.975, 1.180, 1.000, 1.190, 1.025, 1.190, 1.050, 1.200, 1.075, 1.200,
					1.100 }, // 两球半/三球
			{ 1.130, 0.750, 1.130, 0.775, 1.130, 0.800, 1.130, 0.825, 1.140, 0.850, 1.140, 0.875, 1.150, 0.900, 1.150,
					0.925, 1.150, 0.950, 1.160, 0.975, 1.170, 1.000, 1.170, 1.025, 1.170, 1.050, 1.180, 1.075, 1.180,
					1.100 }, }; // 三球

	/**
	 * 让球中文名的标准化处理
	 * 
	 * @param name
	 *            让球名称
	 * @return 标准名称
	 */
	public static String formatHandicapName(String name)
	{
		if (StringUtils.isEmpty(name))
		{
			return "";
		}
		for (String[] strings : HANDICAP_STANDRARS)
		{
			if (name.equalsIgnoreCase(strings[0]))
			{
				return strings[1];
			}
		}
		return name;
	}
	
	/**
	 * 通过名称获得让球值
	 * @param name 名称,如球半、两球等
	 * @return 让球值
	 */
	public static float getHandicapValue(String name)
	{
		String stdName = formatHandicapName(name);
		for (HandicapValue value : values)
		{
			if(value.isSameName(stdName))
			{
				return value.getValue();
			}
		}
		return -100.0f;
	}
}

/**
 * 让球名称与让球数的映射关系值
 * @ClassName:  League   
 * @Description: 让球名称与让球数的映射关系值   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
class HandicapValue
{
	float value;
	String name;
	
	public HandicapValue()
	{
	}
	
	public HandicapValue(String name, float value)
	{
		this.name = name;
		this.value = value;
	}
	
	public float getValue()
	{
		return value;
	}
	public void setValue(float value)
	{
		this.value = value;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isSameName(String n)
	{
		if(name.equalsIgnoreCase(n))
		{
			return true;
		}
		return false;
	}
}