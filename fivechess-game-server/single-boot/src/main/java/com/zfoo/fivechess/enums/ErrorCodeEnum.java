package com.zfoo.fivechess.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    PASSWORD_ERROR(-1, "密码错误"),

    ;
    private int errorCode;
    private String message;

    ErrorCodeEnum(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
