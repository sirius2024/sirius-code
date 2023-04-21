package com.siriu.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * 排行榜
 * 排名、排行榜、热搜榜是很多APP、游戏都有的功能，常用于用户活动推广、竞技排名、热门信息展示等功能；
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RankingTest {

    private final String KEY_RANKING = "ranking";

    @Autowired
    RedisTemplate redisTemplate;


    @Test
    public void test() {
        add(1001, (double) 60);
        add(1002, (double) 80);
        add(1003, (double) 100);
        add(1004, (double) 90);
        add(1005, (double) 70);

        // 取所有
        Set<DefaultTypedTuple> range = range(0, -1);
        log.info("所有用户排序：{}", range);

        // 前三名
        range = range(0, 2);
        log.info("前三名排序：{}", range);
    }

    /**
     * ZADD key score1 member1 [score2 member2]：添加并设置SCORES，支持一次性添加多个；
     * @param userId
     * @param score
     * @return
     */
    public Boolean add(Integer userId, Double score) {
        Boolean add = redisTemplate.opsForZSet().add(KEY_RANKING, userId, score);
        return add;
    }

    /**
     * ZREVRANGE key start stop [WITHSCORES] ：根据SCORES降序排列；
     * ZRANGE key start stop [WITHSCORES] ：根据SCORES降序排列；
     * @param min
     * @param max
     * @return
     */
    public Set<DefaultTypedTuple> range(long min, long max) {
        // 降序
        Set<DefaultTypedTuple> set = redisTemplate.opsForZSet().reverseRangeWithScores(KEY_RANKING, min, max);
        // 升序
        //Set<DefaultTypedTuple> set = redisTemplate.opsForZSet().rangeWithScores(KEY_RANKING, min, max);
        return set;
    }
}
