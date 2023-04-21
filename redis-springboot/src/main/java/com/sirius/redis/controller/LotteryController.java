package com.sirius.redis.controller;

import com.sirius.redis.pojo.SceneReqVO;
import com.sirius.redis.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/scene")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    /**
     * SADD key member1 [member2]：添加一个或者多个参与用户；
     * @param reqVO
     * @return
     */
    @RequestMapping("/sadd")
    public Object sadd(@RequestBody SceneReqVO reqVO){
        lotteryService.sadd(reqVO);
        return 1;
    }

    /**
     * SRANDMEMBER KEY [count]：随机返回一个或者多个用户；
     * @param reqVO
     * @return
     */
    @RequestMapping("/srandmember")
    public Object srandmember(@RequestBody SceneReqVO reqVO){
        return lotteryService.srandmember(reqVO);
    }

    /**
     * SRANDMEMBER KEY [count]：随机返回一个或者多个用户；
     * @param reqVO
     * @return
     */
    @RequestMapping("/srandomMembers")
    public Object srandomMembers(@RequestBody SceneReqVO reqVO){
        return lotteryService.srandomMembers(reqVO);
    }
}
