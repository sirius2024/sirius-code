package com.sirius.redis.api.jedis;

import org.junit.Test;

public class JedisTest extends BaseJedisTest{


    /**
     * 1、计数器
     */
    @Test
    public void incr(){
        Long articleId = 1L;
        String key = String.format("article:readcount:%s", articleId);
        jedis.incr(key);
        System.out.println("文章:"+articleId +",阅读量:" + jedis.get(key));
    }

}
