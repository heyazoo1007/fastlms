package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {
    private Long id;
    private String bannerName;
    private String image;
    private LocalDate createdAt;

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .image(banner.getImage())
                .createdAt(banner.getCreatedAt().toLocalDate())
                .build();
    }
}
