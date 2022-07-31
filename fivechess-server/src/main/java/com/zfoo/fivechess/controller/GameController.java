package com.zfoo.fivechess.controller;

import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.fivechess.common.OnlineRoleManager;
import com.zfoo.fivechess.logic.*;
import com.zfoo.fivechess.protocol.*;
import com.zfoo.fivechess.utils.LogUtils;
import com.zfoo.net.core.tcp.model.ServerSessionInactiveEvent;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.AttributeType;
import com.zfoo.net.session.model.Session;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    @EventReceiver
    public void onServerSessionInactiveEvent(ServerSessionInactiveEvent event) {
        Session session = event.getSession();
        Long uid = session.getAttribute(AttributeType.UID);
        if (uid == null) {
            return;
        }

        Player player = PlayerManager.getPlayerByUid(uid);

        Table table = TableManager.getTableByTableId(player.getTableId());

        PlayerLostConnectResponse playerLostConnectResponse = new PlayerLostConnectResponse();
        player.setSeatId(player.getSeatId());

        table.broadcastMsgInTable(playerLostConnectResponse, player.getSeatId());
    }


    @PacketReceiver
    public void atJoinRoomRequest(Session session, JoinRoomRequest request) {
        long uid = session.getAttribute(AttributeType.UID);
        if (PlayerManager.getPlayerByUid(uid) != null) {
            LogUtils.game.error("玩家已经在桌子上了");
            return;
        }

        int tableId = request.getTableId();
        Table table = TableManager.getTableByTableId(tableId);
        if (table == null) {
            LogUtils.game.error("加入的桌子不存在");
            return;
        }

        int seatId = table.getEmptySeatId();
        if (seatId < 0) {
            LogUtils.game.error("房间已满");
            return;
        }

        Player player = PlayerManager.addPlayer(uid);
        table.bindSeatIdWithPlayer(seatId, player);

        JoinRoomResponse joinRoomResponse = new JoinRoomResponse();
        joinRoomResponse.setStatus(Constant.eResponse_Ok);
        OnlineRoleManager.sendMessage(uid, joinRoomResponse);

        MySeatInfoResponse mySeatInfoResponse = new MySeatInfoResponse();
        mySeatInfoResponse.setTableId(tableId);
        mySeatInfoResponse.setSeatId(seatId);
        OnlineRoleManager.sendMessage(uid, mySeatInfoResponse);

        UserArrivedResponse userArrivedResponse = new UserArrivedResponse();
        userArrivedResponse.setSeatId(seatId);
        table.broadcastMsgInTable(userArrivedResponse, seatId);

        table.otherUsersArrivedToMyself(player, seatId);

        if (table.checkGameStarted()) {
            table.startGame();
        }
    }

    @PacketReceiver
    public void atGiveChessRequest(Session session, GiveChessRequest request) {
        long uid = session.getAttribute(AttributeType.UID);

        Player player = PlayerManager.getPlayerByUid(uid);
        if (player == null) {
            LogUtils.game.error("没玩家对象");
            return;
        }

        int tableId = player.getTableId();

        Table table = TableManager.getTableByTableId(tableId);
        if (table.getStatus() != Constant.eTableState_Playing) {
            LogUtils.game.error("游戏状态不是游戏中");
            return;
        }

        if (!table.isInTable(player)) {
            LogUtils.game.error("玩家不在当前桌子中");
            return;
        }

        int seatId = player.getSeatId();
        if (seatId != table.getCur_turn()) {
            LogUtils.game.error("不轮到你下棋o");
            return;
        }

        int x_block = request.getxBlock();
        int y_block = request.getyBlock();

        if (x_block < 0 || x_block >= Constant.eDisk_Size || y_block < 0 || y_block >= Constant.eDisk_Size) {
            LogUtils.game.error("下棋位置错误");
            return;
        }

        if (table.getChess_disk()[y_block][x_block] != 0) {
            LogUtils.game.error("该地方有棋子");
            return;
        }

        if (seatId == table.getButton_id()) {
            table.getChess_disk()[y_block][x_block] = Constant.eChessColor_Black;
        } else {
            table.getChess_disk()[y_block][x_block] = Constant.eChessColor_White;
        }

        GiveChessResponse giveChessResponse = new GiveChessResponse();
        giveChessResponse.setStatus(Constant.eResponse_Ok);
        giveChessResponse.setSeatId(seatId);
        giveChessResponse.setxBlock(x_block);
        giveChessResponse.setyBlock(y_block);
        table.broadcastMsgInTable(giveChessResponse, -1);

        if (table.checkGameOver(seatId) == 1) {
            table.sendCheckout(seatId);
            return;
        }

        int next = table.getCur_turn() + 1;
        if (next >= Constant.eDesk_PlayerNum) {
            next = 0;
        }

        table.turnToPlayer(next);
    }
}





































