package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.CategoryDto;
import com.zerobase.fastlms.admin.model.CategoryInput;

import java.util.List;

public interface CategoryService {

    boolean add(String categoryName);

    boolean update(CategoryInput parameter);

    boolean delete(long id);

    List<CategoryDto> list();

    List<CategoryDto> frontList(CategoryDto parameter);
}
