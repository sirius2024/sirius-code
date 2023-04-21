package com.sirius.redis.service;

import com.sirius.redis.pojo.SceneReqVO;
import com.sirius.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryService {

    @Autowired
    private RedisUtil redisUtil;

    private static final String PREFIX_KEY = "lottery:";

    public void sadd(SceneReqVO reqVO) {
        String key = PREFIX_KEY + reqVO.getActivityId();
        redisUtil.sadd(key, reqVO.getUserId());
    }

    public Object spop(SceneReqVO reqVO) {
        String key = PREFIX_KEY + reqVO.getActivityId();
        return redisUtil.spop(key);
    }

    public List<Object> spops(SceneReqVO reqVO) {
        String key = PREFIX_KEY + reqVO.getActivityId();
        return redisUtil.spop(key, 2);
    }

    public Object srandmember(SceneReqVO reqVO) {
        String key = PREFIX_KEY + reqVO.getActivityId();
        return redisUtil.srandomMember(key);
    }

    public List<Object> srandomMembers(SceneReqVO reqVO) {
        String key = PREFIX_KEY + reqVO.getActivityId();
        return redisUtil.srandomMembers(key, 2);
    }
}
