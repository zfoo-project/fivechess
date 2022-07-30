package com.zfoo.fivechess.common;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

/**
 * 错误码大全
 */
@Getter
public enum ErrorCodeEnum {
    PASSWORD_ERROR(-1, "密码错误"),
    ;
    private int errorCode;
    private String message;

    static {
        Set<Integer> set = Sets.newHashSet();
        for (ErrorCodeEnum errorCodeEnum : values()) {
            if (!set.add(errorCodeEnum.errorCode)) {
                throw new RuntimeException(errorCodeEnum.errorCode + "重复");
            }
        }
    }

    ErrorCodeEnum(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
