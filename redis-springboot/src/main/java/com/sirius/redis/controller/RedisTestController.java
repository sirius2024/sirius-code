package com.sirius.redis.controller;

import com.sirius.sb.redis.entity.ExpressOrderEntity;
import com.sirius.sb.redis.service.CacheBreakDownService;
import com.sirius.sb.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redisTest")
public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CacheBreakDownService cacheBreakDownService;

    @GetMapping
    public String  testRedis(){
        //设置值到redis里面去
        redisUtil.set("name","lucy");
        //获取值
        String name = (String) redisUtil.get("name");
        return name;
    }

    @PostMapping("/queryOrderInfo")
    public ExpressOrderEntity queryOrderInfo(@RequestBody ExpressOrderEntity expressOrderEntity){
        return cacheBreakDownService.queryByOrderId(expressOrderEntity.getOrderId());
    }
}
