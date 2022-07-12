package com.fu.demo.druid;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final String INDEX_DATA_SOURCE = "indexDataSource";
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        this.setDefaultTargetDataSource(defaultTargetDataSource);
        this.setTargetDataSources(targetDataSources);
        this.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return redisTemplate.opsForValue().get(INDEX_DATA_SOURCE);
    }
}
