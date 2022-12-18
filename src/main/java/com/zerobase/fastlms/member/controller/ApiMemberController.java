package com.zerobase.fastlms.member.controller;

import com.zerobase.common.model.ResponseResult;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ApiMemberController {
    private final TakeCourseService takeCourseService;

    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> cancelCourse(
            @RequestBody TakeCourseInput parameter,
            Principal principal
    ) {
        String userId = principal.getName();

        TakeCourseDto takeCourseDto =
                takeCourseService.cancelCourseDetail(parameter.getCourseId());
        if (takeCourseDto == null) {
            // 취소하려는 수강내역이 없을 때
            ResponseResult responseResult = new ResponseResult(false, "수강 신청 정보가 존재하지 않습니다.");
            return ResponseEntity.badRequest().body(responseResult);
        }

        if (userId != null || !userId.equals(takeCourseDto.getUserId())) {
            // 내 수강신청 정보가 아닌 걸 취소하려고 할 때
            ResponseResult responseResult = new ResponseResult(false, "본인의 수강신청 정보만 취소할 수 있습니다.");
            return ResponseEntity.badRequest().body(responseResult);
        }

        ServiceResult serviceResult = takeCourseService.cancelCourse(parameter.getCourseId());
        if (!serviceResult.isResult()) {
            ResponseResult responseResult = new ResponseResult(false, "본인의 수강신청 정보만 취소할 수 있습니다.");
            return ResponseEntity.badRequest().body(responseResult);
        }

        return ResponseEntity.ok().body(new ResponseResult(true));
    }
}
