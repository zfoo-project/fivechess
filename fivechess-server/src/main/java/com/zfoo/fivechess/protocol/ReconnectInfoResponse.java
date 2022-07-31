package com.zfoo.fivechess.protocol;

import com.google.common.collect.Lists;
import com.zfoo.fivechess.protocol.common.ChessItem;
import com.zfoo.protocol.IPacket;

import java.util.List;

public class ReconnectInfoResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 224;

    private int buttonId;
    private int seatId;
    private List<ChessItem> chessItems = Lists.newArrayList();

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int buttonId) {
        this.buttonId = buttonId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public List<ChessItem> getChessItems() {
        return chessItems;
    }

    public void setChessItems(List<ChessItem> chessItems) {
        this.chessItems = chessItems;
    }
}
