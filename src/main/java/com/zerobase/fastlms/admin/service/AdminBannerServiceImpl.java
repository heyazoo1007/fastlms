package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.repository.AdminBannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminBannerServiceImpl implements AdminBannerService{
    private final AdminBannerRepository adminBannerRepository;

    @Override
    public void saveBanner(BannerInput parameter) {
        adminBannerRepository.save(Banner.builder()
                .bannerName(parameter.getBannerName())
                .linkAddress(parameter.getLinkAddress())
                .sortSequence(parameter.getSortSequence())
                .saveFilename(parameter.getSaveFilename())
                .urlFilename(parameter.getUrlFilename())
                .publicOrNot(parameter.isPublicOrNot())
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    public void modifyBanner(BannerInput parameter) {
        Optional<Banner> optionalBanner = adminBannerRepository.findById(parameter.getId());
        if (optionalBanner.isPresent()) {
            Banner banner = optionalBanner.get();
            banner.setBannerName(parameter.getBannerName());
            banner.setSaveFilename(parameter.getSaveFilename());
            banner.setUrlFilename(parameter.getUrlFilename());
            banner.setLinkAddress(parameter.getLinkAddress());
            banner.setSortSequence(parameter.getSortSequence());
            banner.setUpdatedAt(LocalDateTime.now());
        }
    }

    @Override
    public List<BannerDto> getBannerList() {
        List<Banner> bannerList = adminBannerRepository.findAll();

        List<BannerDto> list = new ArrayList<>();
        for (Banner banner : bannerList) {
            list.add(BannerDto.of(banner));
        }
        return list;
    }

    @Override
    public BannerDto getById(long id) {
        return adminBannerRepository.findById(id).map(BannerDto::of).orElse(null);
    }

    @Override
    public String getPublicUrlFilename() {
        List<Banner> bannerList =
                adminBannerRepository.findAll();

        if (bannerList.size() > 0) {
            return "";
        }

        String publicUrlFilename = "";
        for (Banner banner : bannerList) {
            if (banner.isPublicOrNot()) {
                publicUrlFilename = banner.getUrlFilename();
            }
        }
        return publicUrlFilename;
    }

    @Override
    public void delete(String idList) {
        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }

                if (id > 0) {
                    adminBannerRepository.deleteById(id);
                }
            }
        }
    }
}
