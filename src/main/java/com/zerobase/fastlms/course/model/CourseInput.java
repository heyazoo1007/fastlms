package com.zerobase.fastlms.course.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseInput {
    private long id;
    private long categoryId;
    private String subject;
    private String keyword;
    private String summary;
    private String contents;
    private long price;
    private long salePrice;
    private String saleEndTimeText;

    private String idList;
}
