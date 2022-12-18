package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.entity.TakeCourse;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseParameter;

import java.util.List;

public interface TakeCourseService {
    List<TakeCourseDto> list(TakeCourseParameter parameter);

    ServiceResult updateStatus(long id, String status);

    // 과거부터 내 수강내역 조회
    List<TakeCourseDto> myCourse(String userId);

    TakeCourseDto cancelCourseDetail(long id);

    ServiceResult cancelCourse(long id);
}
