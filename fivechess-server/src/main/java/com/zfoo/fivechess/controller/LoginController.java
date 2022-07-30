package com.zfoo.fivechess.controller;

import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.fivechess.entity.AccountEntity;
import com.zfoo.fivechess.protocol.LoginRequest;
import com.zfoo.fivechess.protocol.LoginResponse;
import com.zfoo.fivechess.protocol.common.RoleInfoVo;
import com.zfoo.fivechess.service.LoginService;
import com.zfoo.fivechess.service.OnlineRoleService;
import com.zfoo.net.NetContext;
import com.zfoo.net.core.tcp.model.ServerSessionInactiveEvent;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.AttributeType;
import com.zfoo.net.session.model.Session;
import com.zfoo.net.task.TaskBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    OnlineRoleService onlineRoleService;

    @EventReceiver
    public void onServerSessionInactiveEvent(ServerSessionInactiveEvent event) {
        Session session = event.getSession();
        Long uid = session.getAttribute(AttributeType.UID);
        if (uid == null) {
            return;
        }

        onlineRoleService.removeSessionByUid(uid);
    }

    @PacketReceiver
    public void atLoginRequest(Session session, LoginRequest req) {
        String account = req.getAccount();
        String password = req.getPassword();

        // 检查登录参数
        if (!loginService.checkLoginParam(session, account, password)) {
            return;
        }

        TaskBus.executor(account).execute(() -> {
            AccountEntity accountEntity = loginService.selectAndRegisterAccount(account, password);

            // 检查密码
            String dbPassword = accountEntity.getPassword();
            if (!loginService.checkAccountAndPassword(session, dbPassword, password)) {
                return;
            }

            long uid = accountEntity.getUid();

            TaskBus.executor(uid).execute(() -> {
                loginService.bindUidWithSession(session, uid);

                RoleInfoVo roleInfoVo = accountEntity.getRoleInfoVo();
                var response = LoginResponse.valueOf(uid, account, roleInfoVo);
                NetContext.getRouter().send(session, response);
            });
        });
    }
}
