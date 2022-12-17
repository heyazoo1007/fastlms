package com.zerobase.fastlms.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResult {
    boolean result;
    String message;

    public ServiceResult(boolean result) {
        this.result = result;
    }
}
