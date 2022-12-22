package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.controller.MemberController;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication
    ) throws IOException, ServletException {

        memberService.saveLoginHistory(authentication.getName(), getClientIp(request), getUserAgent(request));
    }

    private static String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    private static String getUserAgent(HttpServletRequest request) {

        return request.getHeader("User-Agent");
    }
}
