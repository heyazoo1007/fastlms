package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParameter;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    // uuid에 해당하는 계정을 활성화
    boolean emailAuth(String uuid);

    // 입력한 이메일로 비밀번호 초기화 정보를 전송
    boolean sendResetPassword(ResetPasswordInput parameter);

    // 입력받은 uuid에 대해서 password로 초기화 함
    boolean resetPassword(String id, String password);

    // 입력받은 uuid 값이 유효한지 확인
    boolean checkResetPassword(String uuid);

    List<MemberDto> list(MemberParameter parameter);

    MemberDto detail(String userId);

    // 회원 상태 변경
    boolean updateStatus(String userId, String userStatus);

    boolean updatePassword(String userId, String password);
}
