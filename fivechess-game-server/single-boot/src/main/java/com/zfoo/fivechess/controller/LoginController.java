package com.zfoo.fivechess.controller;

import com.zfoo.fivechess.entity.UinfoEntity;
import com.zfoo.fivechess.enums.ErrorCodeEnum;
import com.zfoo.fivechess.protocol.ErrorResponse;
import com.zfoo.fivechess.protocol.LoginRequest;
import com.zfoo.fivechess.protocol.LoginResponse;
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
    private IEntityCaches<String, UinfoEntity> uinfoEntityCaches;

    @PacketReceiver
    public void atLoginRequest(Session session, LoginRequest request) {
        var uinfoEntity = uinfoEntityCaches.load(request.getAccount());

        if (StringUtils.isBlank(uinfoEntity.getAccount())) {
            long uid = MongoIdUtils.getIncrementIdFromMongoDefault(UinfoEntity.class);
            var newEntity = UinfoEntity.valueOf(request.getAccount(), request.getPassword(), 100, uid);
            OrmContext.getAccessor().insert(newEntity);

            uinfoEntityCaches.invalidate(newEntity.getAccount());

            uinfoEntity = uinfoEntityCaches.load(request.getAccount());
        } else {
            if (!uinfoEntity.getPassword().equals(request.getPassword())) {
                NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.PASSWORD_ERROR));
                return;
            }
        }

        NetContext.getRouter().send(session, LoginResponse.valueOf(uinfoEntity.getAccount(), uinfoEntity.getUid(), uinfoEntity.getCoin()));
    }
}
