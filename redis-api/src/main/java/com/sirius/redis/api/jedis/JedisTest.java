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

    /**
     * 3、加锁
     * setnx 将 key 的值设为 value ，当且仅当 key 不存在。
     *     设置成功，返回 1 。
     *     设置失败，返回 0 。
     */
    @Test
    public void setnx(){
        /**
         * 1、第一种(有问题)
         */
        jedis.setnx("lock:user:1", "1");
        System.out.println(jedis.setnx("lock:user:1", "1"));

        /**
         * 2、第2种(有问题)
         */
        jedis.setnx("lock:user:2", "1");
        jedis.expire("lock:user:2", 3600);
        System.out.println(jedis.setnx("lock:user:2", "1"));

        /**
         * 3、第3种(有问题)
         */
        jedis.setex("lock:user:3", 3600,"1");
        System.out.println(jedis.setnx("lock:user:3", "1"));
    }
}
