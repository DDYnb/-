package com.szy.controller;

import com.szy.common.R;
import com.szy.entity.Orders;
import com.szy.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order){
        ordersService.submit(order);
        return R.success("下单成功");
    }
}
