package com.fu.demo.mapper;

import com.fu.demo.entity.Dept;

import java.util.List;

public interface DeptMapper {

    //根据ID查询
    Dept select(Long id);

    //查询全部
    List<Dept> selectAll();

    //新增
    int insert(Dept dept);

    //更新
    int update(Dept dept);

    //删除
    int delete(Long id);

}

