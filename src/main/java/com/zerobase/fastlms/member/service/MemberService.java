package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface MemberService extends UserDetailsService {

    public boolean register(MemberInput parameter);

    // uuid에 해당하는 계정을 활성화 함
    boolean emailAuth(String uuid);
}
