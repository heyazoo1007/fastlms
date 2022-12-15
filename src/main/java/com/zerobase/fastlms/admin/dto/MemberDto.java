package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime createdAt;

    boolean emailAuthYn;
    LocalDateTime emailAuthTime;
    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitTime;

    boolean adminYn;

    long totalCount;
    long sequence;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .createdAt(member.getCreatedAt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthTime(member.getEmailAuthTime())
                .adminYn(member.isAdminYn())
                .build();
    }
}
