package com.zfoo.fivechess.controller;

import com.zfoo.fivechess.enums.ErrorCodeEnum;
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
        String account = req.getAccount();
        String password = req.getPassword();

        var entity = loginService.selectByAccount(account);
        if (entity.checkNull()) {
            entity = loginService.addUser(account, password);
        } else {
            if (!entity.getPassword().equals(password)) {
                NetContext.getRouter().send(session, ErrorCodeEnum.PASSWORD_ERROR.newErrorResponse());
                return;
            }
        }

        NetContext.getRouter().send(session, LoginResponse.valueOf(entity.getAccount(), entity.getUid(), entity.getCoin()));
    }
}
