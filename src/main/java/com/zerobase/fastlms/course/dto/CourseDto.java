package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.Course;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private Long categoryId;
    private String imagePath;
    private String keyword;
    private String subject;
    private String summary;
    private String contents;
    private long price;
    private long salePrice;
    private LocalDate saleEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String saveFileName;
    private String urlFileName;

    private long totalCount;
    private long sequence;

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
                .saveFileName(course.getSaveFileName())
                .urlFileName(course.getUrlFileName())
                .build();
    }

    public static List<CourseDto> of(List<Course> courses) {

        if (courses == null) {
            return null;
        }

        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            courseDtoList.add(CourseDto.of(course));
        }
        return courseDtoList;
    }
}
