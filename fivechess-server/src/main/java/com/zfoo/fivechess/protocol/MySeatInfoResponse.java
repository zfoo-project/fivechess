package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class MySeatInfoResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 226;

    private int tableId;
    private int seatId;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}
