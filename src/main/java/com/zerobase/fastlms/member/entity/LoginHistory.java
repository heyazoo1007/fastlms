package com.zerobase.fastlms.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private LocalDateTime loginAt;
    private String clientIp;
    private String userAgent;

    public static LoginHistory of(String userId,
                                  String clientIp,
                                  String userAgent) {
        return LoginHistory.builder()
                .userId(userId)
                .loginAt(LocalDateTime.now())
                .clientIp(clientIp)
                .userAgent(userAgent)
                .build();
    }
}

