package com.zfoo.fivechess.protocol.common;

import com.zfoo.protocol.IPacket;

public class ChessItem implements IPacket {
    public static final transient short PROTOCOL_ID = 203;

    private int xBlock;
    private int yBlock;
    private int color;

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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
