package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.entity.TakeCourse;
import com.zerobase.fastlms.course.entity.TakeCourseCode;
import com.zerobase.fastlms.course.mapper.TakeCourseMapper;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseParameter;
import com.zerobase.fastlms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TakeCourseServiceImpl implements TakeCourseService {
    private final TakeCourseMapper takeCourseMapper;
    private final TakeCourseRepository takeCourseRepository;

    // 관리자 차원에서 수강 내역을 보는 것
    @Override
    public List<TakeCourseDto> list(TakeCourseParameter parameter) {
        long totalCount = takeCourseMapper.selectListCount(parameter);

        List<TakeCourseDto> list = takeCourseMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (TakeCourseDto takeCourseDto : list) {
                takeCourseDto.setTotalCount(totalCount);
                takeCourseDto.setSequence(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return null;
    }

    @Override
    public ServiceResult updateStatus(long id, String status) {
        Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
        if (!optionalTakeCourse.isPresent()) {
            return new ServiceResult(false, "수강정보가 존재하지 않습니다.");
        }

        TakeCourse takeCourse = optionalTakeCourse.get();

        takeCourse.setStatus(status);
        takeCourseRepository.save(takeCourse);

        return new ServiceResult(true);
    }

    // 회원 입장에서 수강 내역을 보는 것
    @Override
    public List<TakeCourseDto> myCourse(String userId) {
        TakeCourseParameter parameter = new TakeCourseParameter();
        parameter.setUserId(userId);

        return takeCourseMapper.selectListMyCourse(parameter);
    }

    @Override
    public TakeCourseDto cancelCourseDetail(long id) {
        Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
        if (optionalTakeCourse.isPresent()) {
            return TakeCourseDto.of(optionalTakeCourse.get());
        }

        return null;
    }

    @Override
    public ServiceResult cancelCourse(long id) {
        Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
        if (!optionalTakeCourse.isPresent()) {
            return new ServiceResult(false, "수강정보가 존재하지 않습니다.");
        }
        TakeCourse takeCourse = optionalTakeCourse.get();

        takeCourse.setStatus(TakeCourseCode.STATUS_CANCEL);
        takeCourseRepository.save(takeCourse);

        return new ServiceResult(true);
    }
}
