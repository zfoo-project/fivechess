package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class UnameLoginResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 212;

    private int status;
    private boolean inRoom;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isInRoom() {
        return inRoom;
    }

    public void setInRoom(boolean inRoom) {
        this.inRoom = inRoom;
    }
}
