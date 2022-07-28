package com.zfoo.fivechess.controller;

import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.fivechess.protocol.MatchRequest;
import com.zfoo.fivechess.protocol.MatchResponse;
import com.zfoo.fivechess.service.MatchService;
import com.zfoo.fivechess.utils.LogUtils;
import com.zfoo.net.NetContext;
import com.zfoo.net.core.tcp.model.ServerSessionInactiveEvent;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.AttributeType;
import com.zfoo.net.session.model.Session;
import com.zfoo.net.task.TaskBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HallController {
    @Autowired
    MatchService matchService;

    @PacketReceiver
    public void atMatchRequest(Session session, MatchRequest request) {
        LogUtils.game.info("match");

        Long uid = session.getAttribute(AttributeType.UID);
        TaskBus.executor(MatchService.MATCH_HASH).execute(() -> {
            boolean flag = matchService.addToMatch(uid);
            NetContext.getRouter().send(session, MatchResponse.valueOf(flag));

            if (flag) {
                matchService.checkMatchOk();
            }
        });
    }

    @EventReceiver
    public void onServerSessionInactiveEvent(ServerSessionInactiveEvent event) {
        Session session = event.getSession();
        Long uid = session.getAttribute(AttributeType.UID);
        if (uid == null) {
            return;
        }

        TaskBus.executor(MatchService.MATCH_HASH).execute(() -> {
            matchService.deleteFromMatchQueue(uid);
        });
    }
}
