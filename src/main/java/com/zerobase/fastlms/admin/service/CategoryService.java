package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.CategoryDto;

import java.util.List;

public interface CategoryService {

    boolean add(String categoryName);
    boolean update(CategoryDto parameter);
    boolean delete(long id);

    List<CategoryDto> list();
}
