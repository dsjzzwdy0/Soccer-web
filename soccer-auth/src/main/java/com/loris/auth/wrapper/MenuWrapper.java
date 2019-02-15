package com.loris.auth.wrapper;

import java.util.Map;
import java.util.List;

import com.loris.common.constant.factory.ConstantFactory;
import com.loris.common.wrapper.BaseControllerWarpper;
import com.loris.common.wrapper.tips.IsMenu;

public class MenuWrapper extends BaseControllerWarpper 
{

    public MenuWrapper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("status")));
        map.put("isMenuName", IsMenu.valueOf((Integer) map.get("ismenu")));
    }
}
