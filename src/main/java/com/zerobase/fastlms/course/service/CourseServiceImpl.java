package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.mapper.CourseMapper;
import com.zerobase.fastlms.course.model.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParameter;
import com.zerobase.fastlms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public boolean add(CourseInput parameter) {
        courseRepository.save(Course.builder()
                .subject(parameter.getSubject())
                .createdAt(LocalDateTime.now())
                .build());

        return true;
    }

    @Override
    public boolean modify(CourseInput parameter) {
        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if (!optionalCourse.isPresent()) {
            return false;
        }

        Course course = optionalCourse.get();
        course.setSubject(parameter.getSubject());
        course.setUpdatedAt(LocalDateTime.now());
        courseRepository.save(course);

        return true;
    }

    @Override
    public List<CourseDto> list(CourseParameter parameter) {
        long totalCount = courseMapper.selectListCount(parameter);

        List<CourseDto> list = courseMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (CourseDto x : list) {
                x.setTotalCount(totalCount);
                x.setSequence(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return null;
    }

    @Override
    public CourseDto getById(long id) {
        return courseRepository.findById(id).map(CourseDto::of).orElse(null);
    }
}
