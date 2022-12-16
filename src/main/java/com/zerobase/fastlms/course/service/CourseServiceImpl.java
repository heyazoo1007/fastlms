package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public boolean add(CourseInput parameter) {
        courseRepository.save(Course.builder()
                .subject(parameter.getSubject())
                .createdAt(LocalDateTime.now())
                .build());

        return true;
    }
}
