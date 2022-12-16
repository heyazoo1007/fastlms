package com.zerobase.fastlms.course.entity;

import lombok.*;

import javax.persistence.*;
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

    private String imagePath;
    private String keyword;
    private String subject;

    @Column(length = 1000)
    private String summary;

    @Lob
    private String contents;
    private long price;
    private long salePrice;
    private LocalDateTime saleEndTime;
    private LocalDateTime cratedAt;
    private LocalDateTime updatedAt;
}
