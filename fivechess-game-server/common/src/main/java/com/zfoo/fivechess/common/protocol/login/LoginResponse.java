package com.zfoo.fivechess.common.protocol.login;

import com.zfoo.protocol.IPacket;

public class LoginResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 202;

    private long uid;
    private String unick;

    public static LoginResponse valueOf(long uid, String unick) {
        var packet = new LoginResponse();
        packet.uid = uid;
        packet.unick = unick;
        return packet;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUnick() {
        return unick;
    }

    public void setUnick(String unick) {
        this.unick = unick;
    }
}
