package com.fu.demo.mapper;

import com.fu.demo.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper {

    //根据ID查询
    RoleMenu select(Long id);

    //查询全部
    List<RoleMenu> selectAll();

    //新增
    int insert(RoleMenu roleMenu);

    //更新
    int update(RoleMenu roleMenu);

    //删除
    int delete(Long id);

}

