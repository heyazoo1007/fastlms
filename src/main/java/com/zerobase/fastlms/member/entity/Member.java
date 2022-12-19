package com.zerobase.fastlms.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Member implements MemberCode {
    @Id
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthTime;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitTime;

    private boolean adminYn;

    private String userStatus; // 이용 가능,불가능 상태
}
