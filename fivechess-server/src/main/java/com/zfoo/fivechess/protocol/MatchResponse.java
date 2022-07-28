package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class MatchResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 312;

    private boolean status = true;

    public static MatchResponse valueOf(boolean status) {
        var response = new MatchResponse();
        response.status = status;
        return response;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
