package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryDto> select(CategoryDto parameter);
}
