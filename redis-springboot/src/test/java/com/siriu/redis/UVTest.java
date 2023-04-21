package com.siriu.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 模拟测试10000个用户访问id为2的页面
 * 由于存在误差，这里访问的实际访问的数量是1万，统计出来的多了23个，在标准的误差（0.81%）范围内，加上UV数据不是必须要求准确，因此这个误差是可以接受的。
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UVTest {

    private final String KEY_UV_PAGE_PROFIX = "uv:page:";

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void uvTest() {
        Integer pageId = 2;
        for (int i = 0; i < 10000; i++) {
            uv(pageId, i);
        }
        for (int i = 0; i < 10000; i++) {
            uv(pageId, i);
        }

        Long uv = getUv(pageId);
        log.info("pageId:{} uv:{}", pageId, uv);
    }

    /**
     *      * 用户访问页面
     *      * @param pageId
     *      * @param userId
     *      * @return
     *      
     */
    private Long uv(Integer pageId, Integer userId) {
        String key = KEY_UV_PAGE_PROFIX + pageId;
        return redisTemplate.opsForHyperLogLog().add(key, userId);
    }

    /**
     *      * 统计页面的UV
     *      * @param pageId
     *      * @return
     *      
     */
    private Long getUv(Integer pageId) {
        String key = KEY_UV_PAGE_PROFIX + pageId;
        return redisTemplate.opsForHyperLogLog().size(key);
    }
}
