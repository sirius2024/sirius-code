package com.sirius.redis.service;

import com.sirius.redis.entity.ExpressOrderEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpressOrderService {

    public ExpressOrderEntity queryByOrderId(String orderId){
        List<ExpressOrderEntity> list = allExpressOrder();
        if(CollectionUtils.isEmpty(list)){
            return null;
        }

        Optional<ExpressOrderEntity> first = list.stream().filter(item -> orderId.equals(item.getOrderId())).findFirst();

        return first.isPresent() ? first.get() : null;
    }

    public List<ExpressOrderEntity> allExpressOrder(){
        List<ExpressOrderEntity> list = new ArrayList<>();
        list.add(new ExpressOrderEntity("1", "admin", "admin1"));
        list.add(new ExpressOrderEntity("2", "admin2", "admin3"));
        list.add(new ExpressOrderEntity("3", "admin4", "admin5"));
        list.add(new ExpressOrderEntity("4", "admin6", "admin7"));
        return list;
    }
}
