package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;

import java.util.List;

public interface AdminBannerService {

    void saveBanner(BannerInput parameter);
    void modifyBanner(BannerInput parameter);
    List<BannerDto> getBannerList();

    BannerDto getById(long id);

    String getPublicUrlFilename();
}
