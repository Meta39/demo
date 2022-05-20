package com.fu.demo.service;

import com.fu.demo.entity.Role;
import com.fu.demo.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    //根据ID查询
    public Role select(Long id) {
        return roleMapper.select(id);
    }

    //查询全部
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    //新增
    public Integer insert(Role role) {
        return roleMapper.insert(role);
    }

    //更新
    public Integer update(Role role) {
        return roleMapper.update(role);
    }

    //删除
    public Integer delete(Long id) {
        return roleMapper.delete(id);
    }
}

