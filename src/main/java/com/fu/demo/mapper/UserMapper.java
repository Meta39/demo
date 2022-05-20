package com.fu.demo.mapper;

import com.fu.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    //根据ID查询
    User select(Long id);

    //查询全部
    List<User> selectAll();

    //新增
    int insert(User user);

    //更新
    int update(User user);

    //删除
    int delete(Long id);

}

