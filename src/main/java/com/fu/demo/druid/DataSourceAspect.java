package com.fu.demo.druid;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    private static final int OVERTIME = 30;
    private static final String INDEX = "index";
    private static final String DATA_SOURCE_LIST = "dataSourceList";
    private static final String INDEX_DATA_SOURCE = "indexDataSource";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Around("execution(public * com.fu.demo.mapper.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            robin();//轮询数据库
            return point.proceed();
        } catch (MyBatisSystemException e) {
            robin();//连接不上就再轮询一次，获取另外一个mysql数据库连接
            return point.proceed();
        }
    }

    /**
     * 轮询mysql数据库
     */
    public void robin() {
        //如果key不存在就创建key,并设置下标初始值为0
        if (!redisTemplate.hasKey(INDEX)) {
            redisTemplate.opsForValue().set(INDEX, 0,OVERTIME, TimeUnit.SECONDS);
        }
        //有下标则直接获取
        int getIndex = (int) redisTemplate.opsForValue().get(INDEX);
        if (!redisTemplate.hasKey(DATA_SOURCE_LIST)) {
            redisTemplate.opsForList().rightPushAll(DATA_SOURCE_LIST, "mysql1", "mysql2");
            redisTemplate.expire(DATA_SOURCE_LIST,OVERTIME, TimeUnit.SECONDS);
        }else {
            redisTemplate.expire(DATA_SOURCE_LIST,OVERTIME, TimeUnit.SECONDS);
        }
        //超过list集合的值就重新赋值（轮询）
        if (getIndex >= redisTemplate.opsForList().size(DATA_SOURCE_LIST)) {
            getIndex = 0;
        }
        //设置当前连接的数据库名称
        redisTemplate.opsForValue().set(INDEX_DATA_SOURCE, redisTemplate.opsForList().index(DATA_SOURCE_LIST, getIndex),OVERTIME, TimeUnit.SECONDS);
        //利用redis单线程的特性存放全局index下标
        redisTemplate.opsForValue().set(INDEX, ++getIndex,OVERTIME, TimeUnit.SECONDS);
    }
}
