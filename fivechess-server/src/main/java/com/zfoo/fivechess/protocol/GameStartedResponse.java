package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class GameStartedResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 218;

    private int buttonId;

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int buttonId) {
        this.buttonId = buttonId;
    }
}
