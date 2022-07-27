package com.zfoo.fivechess.enums;

import com.google.common.collect.Sets;
import com.zfoo.fivechess.protocol.common.ErrorResponse;
import lombok.Getter;

import java.util.Set;

@Getter
public enum ErrorCodeEnum {
    PASSWORD_ERROR(-1, "密码错误"),
    ALREADY_MATCHING_ERROR(-2, "正在匹配中"),

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

    public ErrorResponse newErrorResponse() {
        return ErrorResponse.valueOf(this);
    }
}
