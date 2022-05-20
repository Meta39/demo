package com.fu.demo.service;

import com.fu.demo.entity.RoleMenu;
import com.fu.demo.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class RoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    //根据ID查询
    public RoleMenu select(Long id) {
        return roleMenuMapper.select(id);
    }

    //查询全部
    public List<RoleMenu> selectAll() {
        return roleMenuMapper.selectAll();
    }

    //新增
    public Integer insert(RoleMenu roleMenu) {
        return roleMenuMapper.insert(roleMenu);
    }

    //更新
    public Integer update(RoleMenu roleMenu) {
        return roleMenuMapper.update(roleMenu);
    }

    //删除
    public Integer delete(Long id) {
        return roleMenuMapper.delete(id);
    }
}

