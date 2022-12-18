package com.zerobase.fastlms.course.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class TakeCourseInput {
    long courseId;
    String userId;

    long takeCourseId;
}
