package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.TakeCourse;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TakeCourseDto {
    private Long id;
    private Long courseId;
    private String userId;

    private Long payPrice;
    private String status;

    private LocalDateTime createdAt;

    private String userName;
    private String phone;
    private String subject;

    private Long totalCount;
    private Long sequence;

    public static TakeCourseDto of(TakeCourse takeCourse) {
        return TakeCourseDto.builder()
                .id(takeCourse.getId())
                .courseId(takeCourse.getCourseId())
                .userId(takeCourse.getUserId())
                .payPrice(takeCourse.getPayPrice())
                .status(takeCourse.getStatus())
                .createdAt(takeCourse.getCreatedAt())
                .build();
    }

    public String getCreatedAtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return createdAt != null ? createdAt.format(formatter) : "";
    }


}
