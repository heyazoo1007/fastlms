package com.zerobase.fastlms.admin.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bannerName;
    private String saveFilename;
    private String urlFilename;
    private String linkAddress;
    private String sortSequence;
    private boolean publicOrNot;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
