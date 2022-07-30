package com.zfoo.fivechess.protocol.common;

import com.zfoo.protocol.IPacket;

public class RoleInfoVo implements IPacket {
    public static final transient short PROTOCOL_ID = 202;

    private int gold;

    public RoleInfoVo() {
        this.gold = 1000;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
