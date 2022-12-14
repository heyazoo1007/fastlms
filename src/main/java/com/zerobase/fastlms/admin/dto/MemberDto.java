package com.zerobase.fastlms.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {

    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime createdAt;

    boolean email_authYn;
    LocalDateTime emailAuthTime;
    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitTime;

    boolean admin_yn;

    long totalCount;
    long sequence;


}
