package com.szy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szy.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}