package com.zfoo.fivechess.controller;

import com.zfoo.fivechess.entity.UinfoEntity;
import com.zfoo.fivechess.enums.ErrorCodeEnum;
import com.zfoo.fivechess.protocol.login.ErrorResponse;
import com.zfoo.fivechess.protocol.login.LoginRequest;
import com.zfoo.fivechess.protocol.login.LoginResponse;
import com.zfoo.net.NetContext;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.Session;
import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.anno.EntityCachesInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.orm.util.MongoIdUtils;
import com.zfoo.protocol.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @EntityCachesInjection
    private IEntityCaches<String, UinfoEntity> uinfoEntityCaches;

    @PacketReceiver
    public void atLoginRequest(Session session, LoginRequest req) {
        var entity = uinfoEntityCaches.load(req.getAccount());

        if (StringUtils.isBlank(entity.getAccount())) {
            long uid = MongoIdUtils.getIncrementIdFromMongoDefault(UinfoEntity.class);
            var newEntity = UinfoEntity.valueOf(req.getAccount(), req.getPassword(), 100, uid);
            OrmContext.getAccessor().insert(newEntity);

            uinfoEntityCaches.invalidate(newEntity.getAccount());

            entity = uinfoEntityCaches.load(req.getAccount());
        } else {
            if (!entity.getPassword().equals(req.getPassword())) {
                NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.PASSWORD_ERROR));
                return;
            }
        }

        NetContext.getRouter().send(session, LoginResponse.valueOf(entity.getAccount(), entity.getUid(), entity.getCoin()));
    }
}
