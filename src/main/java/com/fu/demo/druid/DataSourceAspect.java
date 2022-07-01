package com.fu.demo.druid;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Around("execution(public * com.fu.demo.mapper.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            robin();//轮询数据库
            return point.proceed();
        } catch (MyBatisSystemException e) {
            robin();//连接不上就再轮询一次，获取另外一个mysql数据库连接
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDateSourceType();
        }
    }

    /**
     * 轮询mysql数据库
     */
    public void robin() {
        String key = "index";
        if (!redisTemplate.hasKey(key)) {//如果key不存在就创建key,并设置下标初始值为0
            redisTemplate.opsForValue().set(key, 0);
        }
        int index = redisTemplate.opsForValue().get(key);//有下标则直接获取
        List<String> list = new ArrayList<>();
        list.add("mysql1");
        list.add("mysql2");
        if (index >= list.size()) {//超过list集合的值就重新赋值（轮询）
            index = 0;
        }
        DynamicDataSourceContextHolder.setDateSourceType(list.get(index));
        redisTemplate.opsForValue().set(key, ++index);//利用redis单线程的特性存放全局index下标
    }
}
