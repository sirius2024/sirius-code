package com.siriu.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Set实现点赞/收藏功能
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeAndCollectTest {

    private final String KEY_LIKE_ARTICLE_PROFIX = "like:article:";

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void test() {
        long articleId = 100;
        Long likeNum = like(articleId, 1001, 1002, 2001, 3005, 4003);

        unLike(articleId, 2001);
        likeNum = likeNum(articleId);
        boolean b2001 = isLike(articleId, 2001);
        boolean b3005 = isLike(articleId, 3005);
        log.info("文章：{} 点赞数量：{} 用户2001的点赞状态：{} 用户3005的点赞状态：{}", articleId, likeNum, b2001, b3005);
    }

    /**
     * 点赞
     * @param articleId 文章ID
     * @return 点赞数量
     */
    public Long like(Long articleId, Integer... userIds) {
        String key = KEY_LIKE_ARTICLE_PROFIX + articleId;
        /**
         * SADD key member1 [member2]：添加一个或者多个成员（点赞）
         */
        Long add = redisTemplate.opsForSet().add(key, userIds);
        return add;
    }

    /**
     * 取消点赞
     * @param articleId
     * @param userIds
     * @return
     */
    public Long unLike(Long articleId, Integer... userIds) {
        String key = KEY_LIKE_ARTICLE_PROFIX + articleId;
        /**
         * SREM key member1 [member2] ：移除一个或者多个成员（点赞数量）
         */
        Long remove = redisTemplate.opsForSet().remove(key, userIds);
        return remove;
    }

    /**
     * 点赞数量
     * @param articleId
     * @return
     */
    public Long likeNum(Long articleId) {
        String key = KEY_LIKE_ARTICLE_PROFIX + articleId;
        /**
         * SCARD key：获取所有成员的数量（点赞数量）
         */
        Long size = redisTemplate.opsForSet().size(key);
        return size;
    }

    /**
     * 判断用户是否点赞
     * @param articleId
     * @param userId
     * @return
     */
    public Boolean isLike(Long articleId, Integer userId) {
        String key = KEY_LIKE_ARTICLE_PROFIX + articleId;
        /**
         * SISMEMBER key member：判断成员是否存在（是否点赞）
         */
        return redisTemplate.opsForSet().isMember(key, userId);
    }
}
