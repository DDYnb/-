package com.szy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szy.entity.Orders;

public interface OrdersService extends IService<Orders> {
    //用户下单
    public void submit(Orders orders);
}
