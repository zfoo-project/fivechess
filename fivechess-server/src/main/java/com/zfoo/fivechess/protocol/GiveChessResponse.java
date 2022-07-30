package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class GiveChessResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 220;

    private int status;
    private int seatId;
    private int xBlock;
    private int yBlock;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getxBlock() {
        return xBlock;
    }

    public void setxBlock(int xBlock) {
        this.xBlock = xBlock;
    }

    public int getyBlock() {
        return yBlock;
    }

    public void setyBlock(int yBlock) {
        this.yBlock = yBlock;
    }
}
