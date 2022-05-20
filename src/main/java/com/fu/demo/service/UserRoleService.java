package com.fu.demo.service;

import com.fu.demo.entity.UserRole;
import com.fu.demo.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

    //根据ID查询
    public UserRole select(Long id) {
        return userRoleMapper.select(id);
    }

    //查询全部
    public List<UserRole> selectAll() {
        return userRoleMapper.selectAll();
    }

    //新增
    public Integer insert(UserRole userRole) {
        return userRoleMapper.insert(userRole);
    }

    //更新
    public Integer update(UserRole userRole) {
        return userRoleMapper.update(userRole);
    }

    //删除
    public Integer delete(Long id) {
        return userRoleMapper.delete(id);
    }
}

