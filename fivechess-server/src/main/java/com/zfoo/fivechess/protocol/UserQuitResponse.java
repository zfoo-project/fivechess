package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class UserQuitResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 223;

    private int seatId;
    private int reason;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }
}
