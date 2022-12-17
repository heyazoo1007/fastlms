package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParameter;

import java.util.List;

public interface AdminMemberService {
    List<MemberDto> list(MemberParameter parameter);

    // 회원 상태 변경
    boolean updateStatus(String userId, String userStatus);

    boolean updatePassword(String userId, String password);
}
