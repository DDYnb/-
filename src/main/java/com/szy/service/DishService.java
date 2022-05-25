package com.szy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szy.dto.DishDto;
import com.szy.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，需要插入两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);
    //修改菜品信息和对应的口味信息
    public void updateWithFlavor(DishDto dishDto);
}
