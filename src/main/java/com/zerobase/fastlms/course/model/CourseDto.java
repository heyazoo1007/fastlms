package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.course.entity.Course;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    Long id;
    Long categoryId;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDate saleEndDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    long totalCount;
    long sequence;

    public static CourseDto of(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDate(course.getSaleEndDate())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
