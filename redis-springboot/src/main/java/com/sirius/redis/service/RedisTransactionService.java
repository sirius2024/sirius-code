package com.sirius.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedisTransactionService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public void test(){
        //开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        //redis multi命令用于标记一个事务块的开始
        redisTemplate.multi();
        //事务块内的多条命令会安装先后顺序被放进一个队列当中
        redisTemplate.opsForValue().set("redis:transaction:key1", "1");
        redisTemplate.opsForValue().set("redis:transaction:key2", "2");
        //最后由exec命令原子性地执行
        redisTemplate.exec();
    }

    @Transactional
    public Long testTransactionAnnotations(){
        return redisTemplate.opsForValue().increment("count", 1);
    }
}
