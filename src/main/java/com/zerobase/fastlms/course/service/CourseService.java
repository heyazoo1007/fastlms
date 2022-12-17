package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.*;

import java.util.List;

public interface CourseService {

    boolean add(CourseInput parameter);

    boolean modify(CourseInput parameter);

    boolean delete(String idList);

    List<CourseDto> list(CourseParameter parameter);

    CourseDto getById(long id);

    List<CourseDto> frontList(CourseParameter parameter);

    CourseDto frontDetail(long id);

    // 수강신청 요청
    ServiceResult request(TakeCourseInput parameter);
}
