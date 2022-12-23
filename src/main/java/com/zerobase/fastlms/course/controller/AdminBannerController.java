package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.service.AdminBannerService;
import com.zerobase.fastlms.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminBannerController extends BaseController {
    private final AdminBannerService adminBannerService;

    @GetMapping("/admin/banner/add.do")
    public String getSaveBannerPage() {
        return "admin/banner/add";
    }

    @PostMapping("/admin/banner/add.do")
    public String saveBanner(Model model,
                             HttpServletRequest request,
                             MultipartFile file,
                             BannerInput parameter) {

        String saveFilename = "";
        String urlFilename = "";

        if (file != null) {
            String originalFileName = file.getOriginalFilename();

            String baseLocalPath = "/Users/yejinkim/Desktop/heyazoo1007/fastlms/files/";
            String baseUrlPath = "/files";

            String[] arrFilename = getNewFileName(baseLocalPath, baseUrlPath, originalFileName);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];;

            try {
                File newFile = new File(baseLocalPath);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("#######################");
                log.info(e.getMessage());
            }
        }

        parameter.setSaveFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);
        adminBannerService.saveBanner(parameter);

        return "redirect:/admin/banner/list.do";
    }

    @GetMapping("/admin/banner/list.do")
    public String getBannerList(Model model, BannerInput parameter) {
        List<BannerDto> list = adminBannerService.getBannerList();
        model.addAttribute("list", list);

        long totalCount = list.size();
        model.addAttribute("totalCount", totalCount);

        String pagerHtml = super.getPagerHtml(totalCount, parameter.getPageSize(),
                parameter.getPageIndex(), parameter.getQueryString());
        model.addAttribute("pagerHtml", pagerHtml);

        return "admin/banner/list";
    }

    private String[] getNewFileName(String baseLocalPath, String baseUrlPath, String originalFilename) {
        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = String.format("%s%s", dirs[2], uuid);
        String newUrlFileName = String.format("%s%s", urlDir, uuid);

        if (fileExtension.length() > 0) {
            newFileName += "." + fileExtension;
            newUrlFileName += "."+ fileExtension;
        }

        return new String[]{newFileName, newUrlFileName};
    }
}
