package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerParameter;
import com.zerobase.fastlms.admin.service.AdminBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminBannerController extends BaseController {
    private final AdminBannerService adminBannerService;

    @GetMapping("/admin/banner/list.do")
    public String getBannerList(Model model, BannerParameter parameter) {
        List<BannerDto> list = adminBannerService.getBannerList();
        model.addAttribute("list", list);

        long totalCount = list.size();
        model.addAttribute("totalCount", totalCount);

        String pagerHtml = super.getPagerHtml(totalCount, parameter.getPageSize(),
                parameter.getPageIndex(), parameter.getQueryString());
        model.addAttribute("pagerHtml", pagerHtml);

        return "admin/banner/list";
    }
}
