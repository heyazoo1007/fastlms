package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {
    private Long id;
    private String bannerName;
    private String saveFilename;
    private String urlFilename;
    private LocalDate createdAt;

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .saveFilename(banner.getSaveFilename())
                .urlFilename(banner.getUrlFilename())
                .createdAt(LocalDateTime.now().toLocalDate())
                .build();
    }
}
