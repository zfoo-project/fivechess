package com.zfoo.fivechess.protocol;

import com.zfoo.fivechess.protocol.common.RoleInfoVo;
import com.zfoo.protocol.IPacket;

public class LoginResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 212;

    private long uid;

    private String account;
    private RoleInfoVo roleInfoVo;

    public static LoginResponse valueOf(long uid, String account, RoleInfoVo roleInfoVo) {
        var response = new LoginResponse();
        response.uid = uid;
        response.account = account;
        response.roleInfoVo = roleInfoVo;
        return response;
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

    public RoleInfoVo getRoleInfoVo() {
        return roleInfoVo;
    }

    public void setRoleInfoVo(RoleInfoVo roleInfoVo) {
        this.roleInfoVo = roleInfoVo;
    }
}
