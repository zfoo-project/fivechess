package com.zfoo.fivechess.protocol;

import com.zfoo.fivechess.protocol.common.GameInfoVo;
import com.zfoo.protocol.IPacket;

public class LoginResponse implements IPacket {
    public static final transient short PROTOCOL_ID = 212;

    private long uid;

    private String account;
    private GameInfoVo gameInfoVo;

    public static LoginResponse valueOf(long uid, String account, GameInfoVo gameInfo) {
        var response = new LoginResponse();
        response.uid = uid;
        response.account = account;
        response.gameInfoVo = gameInfo;
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

    public GameInfoVo getGameInfoVo() {
        return gameInfoVo;
    }

    public void setGameInfoVo(GameInfoVo gameInfoVo) {
        this.gameInfoVo = gameInfoVo;
    }
}
