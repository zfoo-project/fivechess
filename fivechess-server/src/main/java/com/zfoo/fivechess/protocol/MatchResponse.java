package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class MatchResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 312;

    public static MatchResponse valueOf() {
        var response = new MatchResponse();
        return response;
    }
}
