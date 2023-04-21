package com.sirius.redis.controller;

import com.sirius.sb.redis.service.RedisTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class RedisTransactionController {

    @Autowired
    private RedisTransactionService redisTransactionService;

    @RequestMapping("/test")
    public Object test(){
        redisTransactionService.test();
        return null;
    }

    @RequestMapping("/test1")
    public Object test1(){
        return redisTransactionService.testTransactionAnnotations();
    }
}
