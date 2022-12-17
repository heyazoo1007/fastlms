package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.entity.TakeCourse;
import com.zerobase.fastlms.course.entity.TakeCourseCode;
import com.zerobase.fastlms.course.mapper.CourseMapper;
import com.zerobase.fastlms.course.model.*;
import com.zerobase.fastlms.course.repository.CourseRepository;
import com.zerobase.fastlms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final TakeCourseRepository takeCourseRepository;
    private final CourseMapper courseMapper;


    @Override
    public boolean add(CourseInput parameter) {
        LocalDate saleEndDate = getLocalDate(parameter.getSaleEndTimeText());

        courseRepository.save(Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .keyword(parameter.getKeyword())
                .summary(parameter.getSummary())
                .contents(parameter.getContents())
                .price(parameter.getPrice())
                .salePrice(parameter.getSalePrice())
                .saleEndDate(saleEndDate)
                .createdAt(LocalDateTime.now())
                .build());

        return true;
    }

    @Override
    public boolean modify(CourseInput parameter) {
        LocalDate saleEndDate = getLocalDate(parameter.getSaleEndTimeText());

        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if (!optionalCourse.isPresent()) {
            return false;
        }

        Course course = optionalCourse.get();
        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setSaleEndDate(saleEndDate);
        course.setUpdatedAt(LocalDateTime.now());

        courseRepository.save(course);

        return true;
    }

    @Override
    public boolean delete(String idList) {
        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {

                }
                if (id > 0) {
                    courseRepository.deleteById(id);
                }
            }
        }
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

    @Override
    public List<CourseDto> frontList(CourseParameter parameter) {
        if (parameter.getCategoryId() < 1) {
            List<Course> courseList = courseRepository.findAll();
            return CourseDto.of(courseList);
        }

        return courseRepository.findByCategoryId(parameter.getCategoryId())
                .map(CourseDto :: of).orElse(null);
    }

    @Override
    public CourseDto frontDetail(long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            return CourseDto.of(optionalCourse.get());
        }
        return null;
    }

    @Override
    public ServiceResult request(TakeCourseInput parameter) {
        ServiceResult serviceResult = new ServiceResult();

        Optional<Course> optionalCourse =
                courseRepository.findById(parameter.getCourseId());
        if (!optionalCourse.isPresent()) {
            serviceResult.setResult(false);
            serviceResult.setMessage("강좌 정보가 존재하지 않습니다.");
            return serviceResult;
        }

        Course course = optionalCourse.get();

        String[] statusList = {TakeCourse.STATUS_REQUEST, TakeCourse.STATUS_COMPLETE};
        long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(
                course.getId(), parameter.getUserId(), Arrays.asList(statusList)
        );

        if (count > 0) {
            serviceResult.setResult(false);
            serviceResult.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return serviceResult;
        }

        takeCourseRepository.save(TakeCourse.builder()
                .courseId(course.getId())
                .userId(parameter.getUserId())
                .payPrice(course.getSalePrice())
                .status(TakeCourseCode.STATUS_REQUEST)
                .createdAt(LocalDateTime.now())
                .build());

        serviceResult.setResult(true);
        serviceResult.setMessage("강좌 신청 성공");

        return serviceResult;
    }

    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {

        }

        return null;
    }
}
