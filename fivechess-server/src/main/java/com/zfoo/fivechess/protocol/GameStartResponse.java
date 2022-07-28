package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class GameStartResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 313;

    public static GameStartResponse valueOf() {
        var notify = new GameStartResponse();
        return notify;
    }
}
