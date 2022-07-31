package com.zfoo.fivechess.logic;

import com.zfoo.fivechess.common.OnlineRoleManager;
import com.zfoo.protocol.IPacket;
import lombok.Data;

@Data
public class Player {
    private long uid;
    private int tableId;
    private int seatId;

    public Player(long uid) {
        this.uid = uid;
        this.tableId = -1;
        this.seatId = -1;
    }

    public void sendMsg(IPacket packet) {
        OnlineRoleManager.sendMessage(uid, packet);
    }
}
