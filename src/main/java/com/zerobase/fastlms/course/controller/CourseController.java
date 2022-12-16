package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/admin/course/add.do")
    public String add(Model model) {

        return "admin/course/add";
    }

    @PostMapping("/admin/course/add.do")
    public String addSubmit(Model model, CourseInput parameter) {

        return "admin/course/add";
    }

    @GetMapping("/admin/course/list.do")
    public String list(Model model) {

        return "admin/course/list";
    }


}
