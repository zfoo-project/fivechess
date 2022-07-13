package com.zfoo.fivechess.common.protocol.login;

import com.zfoo.protocol.IPacket;

public class LoginRequest implements IPacket {
    public static final transient short PROTOCOL_ID = 201;

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
