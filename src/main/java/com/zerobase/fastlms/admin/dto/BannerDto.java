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
    private String linkAddress;
    private String howToOpen;
    private String sortSequence;
    private boolean publicOrNot;
    private LocalDate createdAt;

    private long sequence;

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .saveFilename(banner.getSaveFilename())
                .urlFilename(banner.getUrlFilename())
                .linkAddress(banner.getLinkAddress())
                .sortSequence(banner.getSortSequence())
                .publicOrNot(banner.isPublicOrNot())
                .createdAt(LocalDateTime.now().toLocalDate())
                .build();
    }
    public static BannerDto of(Banner banner, long sequence) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .saveFilename(banner.getSaveFilename())
                .urlFilename(banner.getUrlFilename())
                .linkAddress(banner.getLinkAddress())
                .sortSequence(banner.getSortSequence())
                .publicOrNot(banner.isPublicOrNot())
                .createdAt(LocalDateTime.now().toLocalDate())
                .sequence(sequence)
                .build();
    }
}
