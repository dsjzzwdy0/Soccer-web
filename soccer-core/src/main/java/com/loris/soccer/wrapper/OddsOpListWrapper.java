/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  @OddsOpListWrapper.java   
 * @Package com.loris.soccer.wrappercom.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.soccer.wrapper;

import java.util.List;

import com.loris.soccer.model.OddsOp;

/**   
 * @ClassName:  OddsOpListWrapper.java   
 * @Description: 欧赔列表数据的包装类 
 * @author: 东方足彩
 * @date:   2019年1月28日 下午8:59:32   
 *     
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public class OddsOpListWrapper implements Wrapper<List<OddsOp>>
{
	@Override
	public Object wrap(List<OddsOp> source)
	{
		return null;
	}

}
