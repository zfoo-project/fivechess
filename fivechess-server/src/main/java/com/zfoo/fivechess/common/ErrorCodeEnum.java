package com.zfoo.fivechess.common;

import com.zfoo.protocol.collection.CollectionUtils;
import lombok.Getter;

import java.util.Set;

/**
 * 错误码大全
 */
@Getter
public enum ErrorCodeEnum {
    PASSWORD_ERROR(-1, "密码错误"),

    ;
    private final int errorCode;
    private final String message;

    static {
        // 检查重复errorCode
        Set<Integer> set = CollectionUtils.newSet(ErrorCodeEnum.values().length);
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
