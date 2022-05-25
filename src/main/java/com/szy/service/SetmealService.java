package com.szy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szy.dto.SetmealDto;
import com.szy.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);

    public SetmealDto getByIdWithDish(Long id);

    public void updateWithDish(SetmealDto setmealDto);
}
