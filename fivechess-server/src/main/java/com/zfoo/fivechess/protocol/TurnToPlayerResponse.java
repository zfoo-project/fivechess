package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class TurnToPlayerResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 221;

    private int seatId;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}
