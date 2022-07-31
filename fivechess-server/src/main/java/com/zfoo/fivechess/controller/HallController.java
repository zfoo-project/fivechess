package com.zfoo.fivechess.controller;

import com.zfoo.fivechess.common.OnlineRoleManager;
import com.zfoo.fivechess.logic.*;
import com.zfoo.fivechess.protocol.CreateRoomRequest;
import com.zfoo.fivechess.protocol.CreateRoomResponse;
import com.zfoo.fivechess.protocol.MySeatInfoResponse;
import com.zfoo.fivechess.utils.LogUtils;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.AttributeType;
import com.zfoo.net.session.model.Session;
import org.springframework.stereotype.Controller;

@Controller
public class HallController {
    @PacketReceiver
    public void atCreateRoomRequest(Session session, CreateRoomRequest request) {
        long uid = session.getAttribute(AttributeType.UID);

        if (PlayerManager.getPlayerByUid(uid) != null) {
            LogUtils.game.error("玩家已经在桌子上了");
            return;
        }

        int tableId = TableManager.getNextTableId();
        Table table = TableManager.addTable(tableId);

        Player player = PlayerManager.addPlayer(uid);
        int seatId = table.getEmptySeatId();
        table.bindSeatIdWithPlayer(seatId, player);

        CreateRoomResponse createRoomResponse = new CreateRoomResponse();
        createRoomResponse.setStatus(Constant.eResponse_Ok);
        OnlineRoleManager.sendMessage(uid, createRoomResponse);

        MySeatInfoResponse mySeatInfoResponse = new MySeatInfoResponse();
        mySeatInfoResponse.setTableId(tableId);
        mySeatInfoResponse.setSeatId(seatId);
        OnlineRoleManager.sendMessage(uid, mySeatInfoResponse);
    }
}















































