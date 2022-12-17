package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/member/register")
    public String register(Model model,
                                 HttpServletRequest request,
                                 MemberInput parameter) {
        boolean result = memberService.register(parameter);
        model.addAttribute("result", result);

        return "member/register_complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(HttpServletRequest request, Model model) {
        String uuid = request.getParameter("id");

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return"member/email_auth";
    }

    @RequestMapping("/member/login")
    public String login() {
        System.out.println("request get");

        return "member/login";
    }

    @GetMapping("/member/info")
    public String memberInfo(Model model, Principal principal) {
        String userId = principal.getName();

        MemberDto memberDto = memberService.memberInfo(userId);

        model.addAttribute("memberDto", memberDto);

        return "member/info";
    }
    @PostMapping("/member/info")
    public String modifyMemberInfo(Model model,
                                   MemberInput parameter,
                                   Principal principal) {
        parameter.setUserId(principal.getName());

       ServiceResult serviceResult = memberService.modifyMemberInfo(parameter);
       if (!serviceResult.isResult()) {
           model.addAttribute("message", serviceResult.getMessage());
           return "common/error";
       }
       return "redirect:/member/info";
    }

    @GetMapping("/member/password")
    public String getMemberPassword(Model model, Principal principal) {
        String userId = principal.getName();

        MemberDto memberDto = memberService.memberInfo(userId);

        model.addAttribute("memberDto", memberDto);

        return "member/password";
    }

    @PostMapping("/member/password")
    public String updateMemberPassword(Model model,
                                 Principal principal,
                                 MemberInput parameter) {
        parameter.setUserId(principal.getName());

        ServiceResult serviceResult = memberService.updateMemberPassword(parameter);
        if (!serviceResult.isResult()) {
            model.addAttribute("message", serviceResult.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }

    @GetMapping("/member/takecourse")
    public String memberTakeCourse(Model model, Principal principal) {
        String userId = principal.getName();

        MemberDto memberDto = memberService.memberInfo(userId);

        model.addAttribute("memberDto", memberDto);

        return "member/takecourse";
    }

    @GetMapping("/member/find/password")
    public String findPassword() {
        return "member/find_password";
    }

    @PostMapping("/member/find/password")
    public String sendResetPassword(Model model, ResetPasswordInput parameter) {

        boolean result = false;
        try {
            result = memberService.sendResetPassword(parameter);
        } catch (Exception e) {
        }

        model.addAttribute("result", result);

        return "member/find_password_result";
    }

    @PostMapping("/member/reset/password")
    public String resetPassword(Model model, ResetPasswordInput parameter) {
        boolean result = false;
        try {
            memberService.resetPassword(parameter.getId(), parameter.getPassword());
        } catch (Exception e) {
        }

        model.addAttribute("result", result);

        return "member/reset_password_result";
    }

    @GetMapping("/member/reset/password")
    public String checkResetPassword(Model model,
                                HttpServletRequest request) {
        String uuid = request.getParameter("id");

        boolean result = memberService.checkResetPassword(uuid);
        model.addAttribute("result", result);

        return "member/reset_password";
    }
}
