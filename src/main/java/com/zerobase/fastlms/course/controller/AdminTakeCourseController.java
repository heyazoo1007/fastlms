package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseParameter;
import com.zerobase.fastlms.course.service.CourseService;
import com.zerobase.fastlms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminTakeCourseController extends BaseController {
    private final CourseService courseService;
    private final TakeCourseService takeCourseService;

    @GetMapping("/admin/takecourse/list.do")
    public String list(Model model,
                       TakeCourseParameter parameter) {

        parameter.init();

        List<TakeCourseDto> list = takeCourseService.list(parameter);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = super.getPagerHtml(
                totalCount,
                parameter.getPageSize(),
                parameter.getPageIndex(),
                queryString
        );

        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        List<CourseDto> courseList = courseService.listAll();
        model.addAttribute("courseList", courseList);

        return "admin/takecourse/list";
    }

    @GetMapping("/admin/takecourse/status.do")
    public String status(Model model, TakeCourseParameter parameter) {
        ServiceResult serviceResult =
                takeCourseService.updateStatus(parameter.getId(), parameter.getStatus());
        if (!serviceResult.isResult()) {
            model.addAttribute("message", serviceResult.getMessage());
            return "common/error";
        }

        return "redirect:/admin/takecourse/list.do";
    }
}
