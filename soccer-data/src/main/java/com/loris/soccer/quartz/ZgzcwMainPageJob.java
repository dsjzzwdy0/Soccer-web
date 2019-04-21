/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  ZgzcwMainPageJob.java   
 * @Package com.loris.soccer.quartz   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.quartz;

import com.loris.client.model.SchedulerInfo;
import com.loris.soccer.data.ZgzcwLeagueDataPlugin;

/**   
 * @ClassName: ZgzcwMainPageJob   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class ZgzcwMainPageJob extends DataJob
{

	/* (non-Javadoc)
	 * @see com.loris.soccer.quartz.DataJob#createSchedulerInfo()
	 */
	@Override
	protected SchedulerInfo createSchedulerInfo()
	{
		SchedulerInfo info = new SchedulerInfo();
		info.setId("101-190");
		info.setName("数据下载的信息");
		info.setIntervaltime(5000);
		info.setMaxActiveTaskThread(3);
		info.setRandTimeSeed(200);
		info.setType("zgzcw.downloader");
		info.addPlugin(SchedulerInfo.PLUGIN_CLASS, ZgzcwLeagueDataPlugin.class.getName());
		return info;
	}

}
