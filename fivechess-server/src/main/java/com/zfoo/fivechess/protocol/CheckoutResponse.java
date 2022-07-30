package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class CheckoutResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 222;

    private int winner;

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
