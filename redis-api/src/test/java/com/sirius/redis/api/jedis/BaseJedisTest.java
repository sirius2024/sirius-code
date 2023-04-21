package com.sirius.redis.api.jedis;


import org.junit.Before;
import redis.clients.jedis.Jedis;

/**
 * jedis测试基础类
 */
public class BaseJedisTest {

    Jedis jedis;

    /**
     * 在执行test方法之前会先执行一次before方法
     */
    @Before
    public void init(){
       jedis = new Jedis("redis.sirius.com", 36379);
    }

}
