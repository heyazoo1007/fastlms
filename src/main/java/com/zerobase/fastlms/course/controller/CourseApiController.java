package com.zerobase.fastlms.course.controller;

import com.zerobase.common.model.ResponseResult;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CourseApiController {
    private final CourseService courseService;

    @PostMapping("/api/course/request.api")
    public ResponseEntity courseRequest(
            @RequestBody TakeCourseInput parameter,
            Principal principal
    ) {
        parameter.setUserId(principal.getName());

        ServiceResult serviceResult = courseService.request(parameter);
        if (!serviceResult.isResult()) {
            ResponseResult responseResult = new ResponseResult(false, serviceResult.getMessage());
            return ResponseEntity.badRequest().body(responseResult);
        }
        ResponseResult responseResult = new ResponseResult(true);

        return ResponseEntity.ok().body(responseResult);
    }
}
