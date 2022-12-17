package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.admin.model.CommonParameter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseParameter extends CommonParameter {
    private long id;
    private long categoryId;
}
