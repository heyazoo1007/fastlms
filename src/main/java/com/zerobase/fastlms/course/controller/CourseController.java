package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.CategoryDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseParameter;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController extends BaseController {
    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/course")
    public String course(Model model,
                         CourseParameter parameter) {

        List<CourseDto> list = courseService.frontList(parameter);
        model.addAttribute("list", list);

        int courseTotalCount = 0;
        List<CategoryDto> categoryDtoList =
                categoryService.frontList(CategoryDto.builder().build());
        if (categoryDtoList != null) {
            for (CategoryDto categoryDto : categoryDtoList) {
                courseTotalCount += categoryDto.getCourseCount();
            }
        }

        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("courseTotalCount", courseTotalCount);

        return "course/index";
    }

    @GetMapping("/course/{id}")
    public String courseDetail(Model model,
                               CourseParameter parameter) {
        CourseDto courseDto = courseService.frontDetail(parameter.getId());
        model.addAttribute("courseDto", courseDto);

        return "course/detail";
    }
}
