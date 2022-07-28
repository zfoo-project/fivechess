package com.zfoo.fivechess.controller;

import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.fivechess.common.ErrorCodeEnum;
import com.zfoo.fivechess.common.OnlineRoleManager;
import com.zfoo.fivechess.entity.AccountEntity;
import com.zfoo.fivechess.protocol.LoginRequest;
import com.zfoo.fivechess.protocol.LoginResponse;
import com.zfoo.fivechess.protocol.common.ErrorResponse;
import com.zfoo.fivechess.utils.LogUtils;
import com.zfoo.net.NetContext;
import com.zfoo.net.core.tcp.model.ServerSessionInactiveEvent;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.AttributeType;
import com.zfoo.net.session.model.Session;
import com.zfoo.net.task.TaskBus;
import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.anno.EntityCachesInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.orm.util.MongoIdUtils;
import com.zfoo.protocol.util.StringUtils;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @EntityCachesInjection
    private IEntityCaches<String, AccountEntity> accountEntityCaches;

    @EventReceiver
    public void onServerSessionInactiveEvent(ServerSessionInactiveEvent event) {
        Session session = event.getSession();
        Long uid = session.getAttribute(AttributeType.UID);
        if (uid == null) {
            return;
        }

        OnlineRoleManager.removeUid(uid);
    }

    @PacketReceiver
    public void atLoginRequest(Session session, LoginRequest req) {
        String account = req.getAccount();
        String password = req.getPassword();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.ACCOUNT_OR_PASSWORD_EMPTY_ERROR));
            return;
        }

        TaskBus.executor(account).execute(() -> {
            LogUtils.game.info("login");

            AccountEntity accountEntity = accountEntityCaches.load(account);
            if (accountEntity.checkNull()) {
                var uid = MongoIdUtils.getIncrementIdFromMongoDefault(AccountEntity.class);

                OrmContext.getAccessor().insert(AccountEntity.valueOf(uid, account, password));
                accountEntityCaches.invalidate(account);

                accountEntity = accountEntityCaches.load(account);
            } else {
                if (!accountEntity.getPassword().equals(password)) {
                    NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.PASSWORD_ERROR));
                    return;
                }
            }

            // 绑定下uid
            session.putAttribute(AttributeType.UID, accountEntity.getUid());

            OnlineRoleManager.bindUidSession(accountEntity.getUid(), session);

            var response = LoginResponse.valueOf(accountEntity.getUid(), accountEntity.getId(), accountEntity.getRoleInfoVo());
            NetContext.getRouter().send(session, response);
        });
    }

}
