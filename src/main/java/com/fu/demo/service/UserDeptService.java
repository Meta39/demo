package com.fu.demo.service;

import com.fu.demo.entity.UserDept;
import com.fu.demo.mapper.UserDeptMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserDeptService {
    @Resource
    private UserDeptMapper userDeptMapper;

    //根据ID查询
    public UserDept select(Long id) {
        return userDeptMapper.select(id);
    }

    //查询全部
    public List<UserDept> selectAll() {
        return userDeptMapper.selectAll();
    }

    //新增
    public Integer insert(UserDept userDept) {
        return userDeptMapper.insert(userDept);
    }

    //更新
    public Integer update(UserDept userDept) {
        return userDeptMapper.update(userDept);
    }

    //删除
    public Integer delete(Long id) {
        return userDeptMapper.delete(id);
    }
}

