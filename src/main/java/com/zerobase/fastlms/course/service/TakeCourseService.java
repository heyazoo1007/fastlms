package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseParameter;

import java.util.List;

public interface TakeCourseService {
    List<TakeCourseDto> list(TakeCourseParameter parameter);

    ServiceResult updateStatus(long id, String status);
}
