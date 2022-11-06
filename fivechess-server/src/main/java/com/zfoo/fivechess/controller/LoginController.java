package com.zfoo.fivechess.controller;

import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.fivechess.common.ErrorCodeEnum;
import com.zfoo.fivechess.common.OnlineRoleManager;
import com.zfoo.fivechess.entity.UserEntity;
import com.zfoo.fivechess.logic.*;
import com.zfoo.fivechess.protocol.*;
import com.zfoo.fivechess.protocol.common.ChessItem;
import com.zfoo.fivechess.protocol.common.ErrorResponse;
import com.zfoo.net.NetContext;
import com.zfoo.net.core.tcp.model.ServerSessionInactiveEvent;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.AttributeType;
import com.zfoo.net.session.model.Session;
import com.zfoo.orm.OrmContext;
import com.zfoo.orm.cache.IEntityCaches;
import com.zfoo.orm.model.anno.EntityCachesInjection;
import com.zfoo.orm.util.MongoIdUtils;
import com.zfoo.protocol.util.StringUtils;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    @EntityCachesInjection
    private IEntityCaches<String, UserEntity> userEntityCaches;

    /**
     * 玩家登录
     *
     * @param session
     * @param request
     */
    @PacketReceiver
    public void atUnameLoginRequest(Session session, UnameLoginRequest request) {
        String uname = request.getUname();
        String upwd = request.getUpwd();
        if (StringUtils.isBlank(uname) || StringUtils.isBlank(upwd)) {
            return;
        }

        UserEntity userEntity = userEntityCaches.load(uname);

        // 生成下账号
        if (userEntity.empty()) {
            long uid = MongoIdUtils.getIncrementIdFromMongoDefault(UserEntity.class);

            userEntity = new UserEntity();
            userEntity.setId(uname);
            userEntity.setPwd(upwd);
            userEntity.setUid(uid);

            OrmContext.getAccessor().insert(userEntity);
            userEntityCaches.invalidate(uname);

            userEntity = userEntityCaches.load(uname);
        }

        if (!userEntity.getPwd().equals(upwd)) {
            NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.PASSWORD_ERROR));
            return;
        }

        // 绑定下uid
        long uid = userEntity.getUid();
        OnlineRoleManager.bindUidSession(uid, session);

        Player player = PlayerManager.getPlayerByUid(session.getAttribute(AttributeType.UID));

        boolean isInRoom = player != null;

        UnameLoginResponse unameLoginResponse = new UnameLoginResponse();
        unameLoginResponse.setStatus(Constant.eResponse_Ok);
        unameLoginResponse.setInRoom(isInRoom);
        OnlineRoleManager.sendMessage(uid, unameLoginResponse);

        if (isInRoom) {
            MySeatInfoResponse mySeatInfoResponse = new MySeatInfoResponse();
            mySeatInfoResponse.setTableId(player.getTableId());
            mySeatInfoResponse.setSeatId(player.getSeatId());
            OnlineRoleManager.sendMessage(uid, mySeatInfoResponse);

            Table table = TableManager.getTableByTableId(player.getTableId());
            table.otherUsersArrivedToMyself(player, player.getSeatId());

            UserArrivedResponse userArrivedResponse = new UserArrivedResponse();
            userArrivedResponse.setSeatId(player.getSeatId());
            table.broadcastMsgInTable(userArrivedResponse, player.getSeatId());

            if (table.getStatus() == Constant.eTableState_Playing) {
                ReconnectInfoResponse reconnectInfoResponse = new ReconnectInfoResponse();
                reconnectInfoResponse.setButtonId(table.getButton_id());
                reconnectInfoResponse.setSeatId(table.getCur_turn());

                // 棋子信息
                for (int y_block = 0; y_block < table.getChess_disk().length; y_block++) {
                    for (int x_block = 0; x_block < table.getChess_disk()[y_block].length; x_block++) {
                        int color = table.getChess_disk()[y_block][x_block];

                        if (color > 0) {
                            ChessItem chessItem = new ChessItem();
                            chessItem.setxBlock(x_block);
                            chessItem.setyBlock(y_block);
                            chessItem.setColor(color);

                            reconnectInfoResponse.getChessItems().add(chessItem);
                        }
                    }
                }

                OnlineRoleManager.sendMessage(uid, reconnectInfoResponse);
            }
        }
    }

    /**
     * 玩家下线
     *
     * @param event
     */
    @EventReceiver
    public void onServerSessionInactiveEvent(ServerSessionInactiveEvent event) {
        Session session = event.getSession();
        Long uid = session.getAttribute(AttributeType.UID);
        if (uid == null) {
            return;
        }

        OnlineRoleManager.removeSessionByUid(uid);
    }
}





















