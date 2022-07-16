package com.zfoo.fivechess.controller;

import com.zfoo.fivechess.protocol.MatchRequest;
import com.zfoo.fivechess.protocol.MatchResponse;
import com.zfoo.net.NetContext;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.Session;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @PacketReceiver
    public void atMatchRequest(Session session, MatchRequest request) {
        NetContext.getRouter().send(session, MatchResponse.valueOf());
    }
}
