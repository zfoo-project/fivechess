package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class UserArrivedResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 217;

    private int seatId;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}
