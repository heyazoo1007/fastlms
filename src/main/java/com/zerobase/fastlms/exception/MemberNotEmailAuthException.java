package com.zerobase.fastlms.exception;

public class MemberNotEmailAuthException extends RuntimeException {

    public MemberNotEmailAuthException(String error) {
        super(error);
    }
}
