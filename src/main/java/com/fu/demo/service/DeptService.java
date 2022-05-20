package com.fu.demo.service;

import com.fu.demo.entity.Dept;
import com.fu.demo.mapper.DeptMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class DeptService {
    @Resource
    private DeptMapper deptMapper;

    //根据ID查询
    public Dept select(Long id) {
        return deptMapper.select(id);
    }

    //查询全部
    public List<Dept> selectAll() {
        return deptMapper.selectAll();
    }

    //新增
    public Integer insert(Dept dept) {
        return deptMapper.insert(dept);
    }

    //更新
    public Integer update(Dept dept) {
        return deptMapper.update(dept);
    }

    //删除
    public Integer delete(Long id) {
        return deptMapper.delete(id);
    }
}

