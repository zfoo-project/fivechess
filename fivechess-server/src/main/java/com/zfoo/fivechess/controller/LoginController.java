package com.zfoo.fivechess.controller;

import com.zfoo.fivechess.entity.AccountEntity;
import com.zfoo.fivechess.enums.ErrorCodeEnum;
import com.zfoo.fivechess.protocol.LoginRequest;
import com.zfoo.fivechess.protocol.LoginResponse;
import com.zfoo.fivechess.protocol.common.ErrorResponse;
import com.zfoo.net.NetContext;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.Session;
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

    @PacketReceiver
    public void atLoginRequest(Session session, LoginRequest req) {
        String account = req.getAccount();
        String password = req.getPassword();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.ACCOUNT_OR_PASSWORD_EMPTY_ERROR));
            return;
        }

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

        var response = LoginResponse.valueOf(accountEntity.getUid(), accountEntity.getId(), accountEntity.getRoleInfoVo());
        NetContext.getRouter().send(session, response);
    }
}
