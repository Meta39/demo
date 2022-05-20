package com.fu.demo.mapper;

import com.fu.demo.entity.Role;

import java.util.List;

public interface RoleMapper {

    //根据ID查询
    Role select(Long id);

    //查询全部
    List<Role> selectAll();

    //新增
    int insert(Role role);

    //更新
    int update(Role role);

    //删除
    int delete(Long id);

}

