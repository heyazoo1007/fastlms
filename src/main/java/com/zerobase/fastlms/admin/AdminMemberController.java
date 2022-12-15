package com.zerobase.fastlms.admin;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParameter;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParameter parameter) {

        parameter.init();

        List<MemberDto> list = memberService.list(parameter);

        long totalCount = 0;
        if (list != null && list.size() > 0) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageUtil.pager());

        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParameter parameter) {

        parameter.init();

        MemberDto memberDto = memberService.detail(parameter.getUserId());
        model.addAttribute("memberDto", memberDto);

        return "admin/member/detail";
    }
}
