package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParameter;

import java.util.List;

public interface CourseService {

    boolean add(CourseInput parameter);

    boolean modify(CourseInput parameter);

    boolean delete(String idList);

    List<CourseDto> list(CourseParameter parameter);

    CourseDto getById(long id);
}
