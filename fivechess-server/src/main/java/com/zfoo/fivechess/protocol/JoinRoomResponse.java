package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class JoinRoomResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 216;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
