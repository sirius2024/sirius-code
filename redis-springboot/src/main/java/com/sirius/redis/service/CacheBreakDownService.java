package com.sirius.redis.service;

import com.alibaba.fastjson2.JSON;
import com.sirius.sb.redis.entity.ExpressOrderEntity;
import com.sirius.sb.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 缓存击穿
 */
@Service
public class CacheBreakDownService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ExpressOrderService expressOrderService;

    public ExpressOrderEntity queryByOrderId(String orderId){
        Object o = redisUtil.get("order:" + orderId);
        if(Objects.nonNull(o)){
            return JSON.parseObject(JSON.toJSONString(o), ExpressOrderEntity.class);
        }

        ExpressOrderEntity expressOrderEntity = expressOrderService.queryByOrderId(orderId);

        return expressOrderEntity;
    }
}
