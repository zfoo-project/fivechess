package com.zfoo.fivechess.single.boot.controller;

import com.zfoo.net.NetContext;
import com.zfoo.net.packet.common.Ping;
import com.zfoo.net.packet.common.Pong;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.Session;
import com.zfoo.scheduler.util.TimeUtils;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @PacketReceiver
    public void atPing(Session session, Ping ping){
        NetContext.getRouter().send(session, Pong.valueOf(TimeUtils.now()));
    }
}
