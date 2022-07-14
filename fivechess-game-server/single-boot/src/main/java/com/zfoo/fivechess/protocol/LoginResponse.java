package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class LoginResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 212;

    private long uid;
    private String account;
    private int coin;

    public static LoginResponse valueOf(String account, long uid, int coin) {
        var packet = new LoginResponse();
        packet.account = account;
        packet.uid = uid;
        packet.coin = coin;
        return packet;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
