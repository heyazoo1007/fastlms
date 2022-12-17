package com.zerobase.common.model;

public class ResponseResultHeader {
    boolean result;
    String message;

    public ResponseResultHeader(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseResultHeader(boolean result) {
        this.result = result;
    }
}
