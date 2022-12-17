package com.zerobase.fastlms.course.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;

    private String imagePath;
    private String keyword;
    private String subject;

    @Column(length = 1000)
    private String summary;

    @Lob
    private String contents;
    private long price;
    private long salePrice;
    private LocalDate saleEndDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
