package com.zerobase.fastlms.course.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseDto {
    Long id;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDateTime saleEndTime;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    long totalCount;
    long sequence;
}
