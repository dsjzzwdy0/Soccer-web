/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @RecordOddsYpInfo.java   
 * @Package com.loris.soccer.model.viewcom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄及用于其他的商业目的
 */
package com.loris.soccer.model.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.loris.soccer.model.RecordOddsYp;

/**   
 * @ClassName:  RecordOddsYpInfo.java   
 * @Description: 亚盘数据记录 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄及用于其他的商业目的 
 */
@TableName("soccer_record_odds_yp_info")
public class RecordOddsYpInfo extends RecordOddsYp
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String destid;

	/**
	 * @return the destid
	 */
	public String getDestid()
	{
		return destid;
	}

	/**
	 * @param destid the destid to set
	 */
	public void setDestid(String destid)
	{
		this.destid = destid;
	}
}
