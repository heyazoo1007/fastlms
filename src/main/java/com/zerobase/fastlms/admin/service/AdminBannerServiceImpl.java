package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.repository.AdminBannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBannerServiceImpl implements AdminBannerService{
    private final AdminBannerRepository adminBannerRepository;

    @Override
    public List<BannerDto> getBannerList() {
        List<Banner> bannerList = adminBannerRepository.findAll();

        List<BannerDto> list = new ArrayList<>();
        for (Banner banner : bannerList) {
            list.add(BannerDto.of(banner));
        }

        return list;
    }
}
