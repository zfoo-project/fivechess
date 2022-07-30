package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class JoinRoomRequest implements IPacket {
    public static final transient short PROTOCOL_ID = 215;

    private int tableId;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
