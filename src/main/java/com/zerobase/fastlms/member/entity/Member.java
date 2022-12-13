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
public class Member {
    @Id
    private String userEmail;

    private String userName;
    private String phone;
    private String password;
    private LocalDateTime createdAt;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthTime;
    private String emailAuthKey;
}
