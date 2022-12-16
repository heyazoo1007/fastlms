package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParameter;
import com.zerobase.fastlms.course.service.CourseService;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController extends BaseController {
    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request
            , CourseInput parameter) {

        model.addAttribute("category", categoryService.list());

        boolean isEditMode = request.getRequestURI().contains("/edit.do");
        CourseDto courseDto = new CourseDto();

        if (isEditMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);

            if (existCourse == null) {
                model.addAttribute("message", "강좌 정보가 존재 하지 않습니다.");
                return "common/error";
            }
            model.addAttribute("isEditMode", isEditMode);
            model.addAttribute("courseDto", courseDto);

            return "admin/course/add";
        }


        return "admin/course/add";
    }

    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, CourseInput parameter, HttpServletRequest request) {

        boolean isEditMode = request.getRequestURI().contains("/edit.do");

        if (isEditMode) {
            long id = parameter.getId();
            CourseDto courseDto = courseService.getById(id);
            if (courseDto == null) {
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.modify(parameter);
        } else {
            courseService.add(parameter);
        }

        return "redirect:/admin/course/list.do";
    }

    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParameter parameter) {

        parameter.init();

        List<CourseDto> list = courseService.list(parameter);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = super.getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/course/list";
    }
}
