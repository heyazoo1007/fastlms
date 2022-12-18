package com.zerobase.fastlms.member.entity;

public interface MemberCode {

    // 현재 가입 요청 중
    String MEMBER_STATUS_REQUEST = "REQUEST";

    // 현재 이용중인 상태
    String MEMBER_STATUS_ING = "ING";
    // 현재 이용정지인 상태
    String MEMBER_STATUS_STOP = "STOP";

    // 현재 탈퇴한 회원 상태
    String MEMBER_STATUS_WITHDRAW = "WITHDRAW";
}
