package com.sirius.redis.api.redission;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedissionTest extends BaseRedissionTest{


    /**
     * 红锁
     */
    @Test
    public void redlock(){
        Config config1 = new Config();
        config1.useSingleServer().setAddress("redis//redis.sirius.com:36379").setDatabase(0);
        RedissonClient redissonClient1 = Redisson.create(config1);

        Config config2 = new Config();
        config2.useSingleServer().setAddress("redis//redis.sirius.com:36380").setDatabase(0);
        RedissonClient redissonClient2 = Redisson.create(config2);

        Config config3 = new Config();
        config3.useSingleServer().setAddress("redis//redis.sirius.com:36381").setDatabase(0);
        RedissonClient redissonClient3 = Redisson.create(config3);

        String resourceName = "REDLOCK_KEY";
        RLock lock1 = redissonClient1.getLock(resourceName);
        RLock lock2 = redissonClient2.getLock(resourceName);
        RLock lock3 = redissonClient3.getLock(resourceName);

        // 向3个redis实例尝试加锁
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);
        boolean isLock;
        try {
            // isLock = redLock.tryLock(); // 500ms拿不到锁, 就认为获取锁失败。10000ms即10s是锁失效时间。
            /**
             * waitTime：尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
             * leaseTime：锁的持有时间，超过这个时间锁会自动失效（值应该设置为大于业务处理的时间，确保在锁有效期内业务能处理完）
             */
            isLock = redLock.tryLock(500, 10000, TimeUnit.MILLISECONDS);
            System.out.println("isLock = " + isLock);
            if (isLock) {
                //TODO if get lock success, do something;
                //成功获取锁，在这里处理业务
            }
        } catch (Exception e) {
            throw new RuntimeException("lock fail");
        } finally {
            // 无论如何, 最后都要解锁
            redLock.unlock();
        }
    }
}
