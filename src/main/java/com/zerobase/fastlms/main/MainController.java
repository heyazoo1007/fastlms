package com.zerobase.fastlms.main;

import com.zerobase.fastlms.admin.repository.AdminBannerRepository;
import com.zerobase.fastlms.admin.service.AdminBannerService;
import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


// MainPage 클래스를 만든 목적은 매핑 하기 위해서
// 논리적인 인터넷 주소와 물리적인 파일(프로그래밍을 해야 하므로)을 매핑
// 하나의 주소에 대해서 메소드가 매핑 해준다

@Controller
@RequiredArgsConstructor
public class MainController {
    private final AdminBannerService adminBannerService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("urlFilename",
                adminBannerService.getPublicUrlFilename());
        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {
        return "error/denied";
    }
}
