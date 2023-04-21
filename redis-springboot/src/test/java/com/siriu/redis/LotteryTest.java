package com.siriu.redis;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 抽奖
 * 曾几何时，抽奖是互联网APP热衷的一种推广、拉新的方式，节假日没有好的策划，那就抽个奖吧！一堆用户参与进来，然后随机抽取几个幸运用户给予实物/虚拟的奖品；此时，开发人员就需要写上一个抽奖的算法，来实现幸运用户的抽取；其实我们完全可以利用Redis的集合（Set），就能轻松实现抽奖的功能；
 *  https://mp.weixin.qq.com/s/zkDgc8z0bSopOeu4NsP6cw
 * 　1.@RunWith(SpringRunner.class)：让自动注入的注解生效
 * 　2.其次是测试类要和该项目下的启动类的目录保持一致。
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LotteryTest {
    /**
     * redis-key的前缀
     */
    private final String KEY_RAFFLE_PROFIX = "raffle:";
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() {
        //活动ID
        Integer raffleId = 1;
        join(raffleId, 1000, 1001, 2233, 7890, 44556, 74512);
        List lucky = lucky(raffleId, 2);
        log.info("活动：{} 的幸运中奖用户是：{}", raffleId, lucky);
    }

    /**
     * 参加的人
     * @param raffleId
     * @param userIds
     */
    public void join(Integer raffleId, Integer... userIds) {
        String key = KEY_RAFFLE_PROFIX + raffleId;
        /**
         * SADD key member1 [member2]：添加一个或者多个参与用户
         */
        redisTemplate.opsForSet().add(key, userIds);
    }

    public List lucky(Integer raffleId, long num) {
        String key = KEY_RAFFLE_PROFIX + raffleId;
        // 随机抽取 抽完之后将用户移除奖池
        /**
         * SPOP key：随机返回一个或者多个用户，并删除返回的用户；
         */
        List list = redisTemplate.opsForSet().pop(key, num);

        /**
         * SRANDMEMBER KEY [count]：随机返回一个或者多个用户；
         */
        // 随机抽取 抽完之后用户保留在池子里
        //List list = redisTemplate.opsForSet().randomMembers(key, num);
        return list;
    }

}
