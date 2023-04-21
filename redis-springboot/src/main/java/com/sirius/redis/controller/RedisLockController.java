package com.sirius.redis.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/lock")
public class RedisLockController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private Redisson redisson;


    @RequestMapping("/test")
    public Object test(){
        String lockKey = "lock:product:1001";
        String clientId = UUID.randomUUID().toString();

        //使用jedis.setnx
//        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, "1");
//        redisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);

        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
        if(!result){
            return "error_code";
        }

        try {
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if(stock > 0){
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock+"");
                System.out.println("扣减库存成功，剩余库存:" + realStock);
            }else {
                System.out.println("扣款失败, 库存不足");
            }
        }finally {
            if(clientId.equals(redisTemplate.opsForValue().get(lockKey))){
                //9.9秒卡主 超过10 把别的锁删了
                redisTemplate.delete(lockKey);
            }
        }

        return "ok";
    }


    @RequestMapping("/test2")
    public Object test2(){
        String lockKey = "lock:product:1001";
        RLock redissonLock = redisson.getLock(lockKey);
        redissonLock.lock();//加锁
        try {
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if(stock > 0){
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock+"");
                System.out.println("扣减库存成功，剩余库存:" + realStock);
            }else {
                System.out.println("扣款失败, 库存不足");
            }
        }finally {
            redissonLock.unlock();
        }
        return "ok";
    }
}
