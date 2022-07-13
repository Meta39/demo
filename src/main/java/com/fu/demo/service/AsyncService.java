package com.fu.demo.service;

import com.fu.demo.mapper.DeptMapper;
import com.fu.demo.mapper.RoleMapper;
import com.fu.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AsyncService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private RoleMapper roleMapper;

    @Async("demoAsync")
    public void selectByAsync(){
        long startTime = System.currentTimeMillis();
        log.info("开始时间：{}",startTime);
        userMapper.select(1L);
        userMapper.select(2L);
        deptMapper.select(1L);
        deptMapper.select(2L);
        roleMapper.select(1L);
        roleMapper.select(2L);
        long endTime = System.currentTimeMillis();
        log.info("结束时间：{}",endTime);
        log.info("耗时：{}",endTime - startTime);
    }
}
