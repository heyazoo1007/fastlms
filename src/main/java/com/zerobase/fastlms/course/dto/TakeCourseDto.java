package com.zerobase.fastlms.course.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getCreatedAtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return createdAt != null ? createdAt.format(formatter) : "";
    }
}
