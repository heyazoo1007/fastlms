package com.zerobase.fastlms.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MemberInput {
    private String userId;
    private String userName;
    private String password;
    private String phone;
}
