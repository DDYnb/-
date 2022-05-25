package com.szy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szy.common.CustomException;
import com.szy.entity.Category;
import com.szy.entity.Dish;
import com.szy.entity.Setmeal;
import com.szy.mapper.CategoryMapper;
import com.szy.service.CategoryService;
import com.szy.service.DishService;
import com.szy.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    //根据id删除分类，删除之前需要判断
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品，如果已关联，抛出异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count > 0){
            throw new CustomException("当前分类关联了菜品，无法删除");
        }
        //查询当前分类是否关联了套餐，如果已关联，抛出异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if(count1 > 0){
            throw new CustomException("当前分类关联了套餐，无法删除");
        }
        super.removeById(id);
    }
}
