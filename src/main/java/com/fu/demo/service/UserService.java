package com.fu.demo.service;

import com.fu.demo.entity.User;
import com.fu.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    //根据ID查询
    public User select(Long id) {
        return userMapper.select(id);
    }

    //查询全部
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    //新增
    public Integer insert(User user) {
        return userMapper.insert(user);
    }

    //更新
    public Integer update(User user) {
        return userMapper.update(user);
    }

    //删除
    public Integer delete(Long id) {
        return userMapper.delete(id);
    }
}

