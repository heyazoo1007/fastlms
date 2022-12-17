package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Category;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    Long id;
    String categoryName;
    int sortValue;
    boolean usingYn;

    int courseCount;

    public static List<CategoryDto> of(List<Category> categories) {
        if (categories != null) {
            List<CategoryDto> categoryList = new ArrayList<>();
            for (Category x : categories) {
                categoryList.add(of(x));
            }
            return categoryList;
        }
        return null;
    }

    public static CategoryDto of(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .usingYn(true)
                .sortValue(category.getSortValue())
                .build();
    }
}
