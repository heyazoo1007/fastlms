package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.admin.model.CommonParameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TakeCourseParameter extends CommonParameter {
    private Long id;
    private String status;
}
