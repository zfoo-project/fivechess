package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class GiveChessRequest implements IPacket {
    public static final transient short PROTOCOL_ID = 219;

    private int xBlock;
    private int yBlock;

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
