package com.sirius.redis.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SceneReqVO implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 活动ID
     */
    private Long activityId;
}
