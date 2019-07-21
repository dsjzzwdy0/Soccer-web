/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @StringUtils.java   
 * @Package com.loris.common.utilcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.common.util;

import java.util.ArrayList;

/**
 * @ClassName: StringUtils.java
 * @Description: 字符串的工具类
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的
 */
public class NameUtils
{
	/**
	 * 计算两个字符串的相似度
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return 相似度的值
	 */
	public static float distanceOfTwoStrings(String str1, String str2)
	{
		ArrayList<String> pairs1 = wordLetterPairs(str1.toUpperCase());
		ArrayList<String> pairs2 = wordLetterPairs(str2.toUpperCase());
		int intersection = 0;
		int union = pairs1.size() + pairs2.size();
		for (int i = 0; i < pairs1.size(); i++)
		{
			Object pair1 = pairs1.get(i);
			for (int j = 0; j < pairs2.size(); j++)
			{
				Object pair2 = pairs2.get(j);
				if (pair1.equals(pair2))
				{
					intersection++;
					pairs2.remove(j);
					break;
				}
			}
		}
		return (2.0f * intersection) / union * 100;
	}

	private static ArrayList<String> wordLetterPairs(String str)
	{
		ArrayList<String> allPairs = new ArrayList<>();
		String[] words = str.split("\\s");
		for (int w = 0; w < words.length; w++)
		{
			String[] pairsInWord = letterPairs(words[w]);
			for (int p = 0; p < pairsInWord.length; p++)
			{
				allPairs.add(pairsInWord[p]);
			}
		}
		return allPairs;
	}

	private static String[] letterPairs(String str)
	{
		int numPairs = str.length() - 1;
		String[] pairs = new String[numPairs];
		for (int i = 0; i < numPairs; i++)
		{
			pairs[i] = str.substring(i, i + 2);
		}
		return pairs;
	}
}
