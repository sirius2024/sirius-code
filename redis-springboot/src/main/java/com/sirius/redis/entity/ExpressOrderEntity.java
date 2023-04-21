package com.sirius.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 快递订单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressOrderEntity implements Serializable {

    private String orderId;

    private String receiverUserName;

    private String sendUserName;
}
