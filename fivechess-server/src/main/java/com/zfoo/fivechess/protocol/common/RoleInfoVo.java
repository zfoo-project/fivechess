package com.zfoo.fivechess.protocol.common;

import com.zfoo.protocol.IPacket;

public class RoleInfoVo implements IPacket {
    public static final transient short PROTOCOL_ID = 120;

    private int gold = 100;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
