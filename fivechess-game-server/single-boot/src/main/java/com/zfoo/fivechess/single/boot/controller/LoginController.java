package com.zfoo.fivechess.single.boot.controller;

import com.zfoo.fivechess.common.protocol.login.LoginRequest;
import com.zfoo.fivechess.common.protocol.login.LoginResponse;
import com.zfoo.net.NetContext;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.Session;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @PacketReceiver
    public void atLoginRequest(Session session, LoginRequest req) {
        NetContext.getRouter().send(session, LoginResponse.valueOf(1L, "jianan"));
    }
}
