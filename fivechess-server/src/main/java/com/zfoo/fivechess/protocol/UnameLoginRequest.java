package com.zfoo.fivechess.protocol;

import com.zfoo.protocol.IPacket;

public class UnameLoginRequest implements IPacket {
    public static final transient short PROTOCOL_ID = 211;

    private String uname;
    private String upwd;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
}
