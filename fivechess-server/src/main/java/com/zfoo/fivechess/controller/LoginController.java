package com.zfoo.fivechess.controller;

import com.zfoo.fivechess.enums.ErrorCodeEnum;
import com.zfoo.fivechess.protocol.ErrorResponse;
import com.zfoo.fivechess.protocol.LoginRequest;
import com.zfoo.fivechess.protocol.LoginResponse;
import com.zfoo.fivechess.service.LoginService;
import com.zfoo.net.NetContext;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @PacketReceiver
    public void atLoginRequest(Session session, LoginRequest req) {
        var uinfoEntity = loginService.getEntity(req.getAccount());
        if (uinfoEntity.isNull()) {
            uinfoEntity = loginService.insertAndGetEntity(req.getAccount(), req.getPassword());
        } else {
            if (!loginService.checkPassword(uinfoEntity.getPassword(), req.getPassword())) {
                NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.PASSWORD_ERROR));
                return;
            }
        }

        NetContext.getRouter().send(session, LoginResponse.valueOf(uinfoEntity.getAccount(), uinfoEntity.getUid(), uinfoEntity.getCoin()));
    }
}
