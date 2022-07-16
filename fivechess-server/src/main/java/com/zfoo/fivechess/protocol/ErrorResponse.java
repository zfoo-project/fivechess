package com.zfoo.fivechess.protocol;

import com.zfoo.fivechess.enums.ErrorCodeEnum;
import com.zfoo.protocol.IPacket;

public class ErrorResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 201;

    private int errorCode;
    private String message;

    public static ErrorResponse valueOf(ErrorCodeEnum errorCodeEnum) {
        var packet = new ErrorResponse();
        packet.errorCode = errorCodeEnum.getErrorCode();
        packet.message = errorCodeEnum.getMessage();
        return packet;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
