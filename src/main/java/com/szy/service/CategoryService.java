package com.szy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szy.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
