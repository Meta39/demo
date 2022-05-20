package com.fu.demo.mapper;

import com.fu.demo.entity.Menu;

import java.util.List;

public interface MenuMapper {

    //根据ID查询
    Menu select(Long id);

    //查询全部
    List<Menu> selectAll();

    //新增
    int insert(Menu menu);

    //更新
    int update(Menu menu);

    //删除
    int delete(Long id);

}

