package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    LocalDateTime updatedAt;

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
                .updatedAt(member.getUpdatedAt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthTime(member.getEmailAuthTime())
                .adminYn(member.isAdminYn())
                .build();
    }

    public String getCreatedText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return createdAt != null ? createdAt.format(formatter) : "";
    }

    public String getUpdatedText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return createdAt != null ? createdAt.format(formatter) : "";
    }
}
