/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.loris.auth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loris.auth.dao.DeptMapper;
import com.loris.auth.model.Dept;
import com.loris.auth.node.ZTreeNode;
import com.loris.auth.service.DeptService;
import com.loris.common.util.ToolUtil;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门服务
 *
 * @author fengshuonan
 * @Date 2018/10/15 下午11:39
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Override
    @Transactional
    public void deleteDept(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);

        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper = wrapper.like("pids", "%[" + dept.getId() + "]%");
        List<Dept> subDepts = deptMapper.selectList(wrapper);
        for (Dept temp : subDepts) {
            temp.deleteById();
        }

        dept.deleteById();
    }

    @Override
    public List<ZTreeNode> tree() 
    {
    	List<Dept> depts = this.baseMapper.selectList(new QueryWrapper<>());
    	List<ZTreeNode> tree = new ArrayList<>();
    	for (Dept dept : depts)
		{
			ZTreeNode node = new ZTreeNode();
			node.setId((long)dept.getId());
			node.setName(dept.getSimplename());
			node.setpId((long)dept.getPid());
			node.setIsOpen(ToolUtil.isEmpty(dept.getPid()) || dept.getPid() == 0);
			tree.add(node);
		}
        return tree;
    }

    @Override
    public List<Map<String, Object>> list(String condition) {
    	QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
    	if(StringUtils.isNotBlank(condition))
    	{
    		queryWrapper.like("simplename", condition).or().like("fullname", condition);
    	}
    	queryWrapper.orderByAsc("num");
    	
    	return baseMapper.selectMaps(queryWrapper);
    }
}
