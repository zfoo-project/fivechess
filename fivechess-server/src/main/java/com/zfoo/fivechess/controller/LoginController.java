package com.zfoo.fivechess.controller;

import com.zfoo.event.model.anno.EventReceiver;
import com.zfoo.fivechess.common.ErrorCodeEnum;
import com.zfoo.fivechess.common.OnlineRoleManager;
import com.zfoo.fivechess.entity.UserEntity;
import com.zfoo.fivechess.protocol.UnameLoginRequest;
import com.zfoo.fivechess.protocol.UnameLoginResponse;
import com.zfoo.fivechess.protocol.common.ErrorResponse;
import com.zfoo.net.NetContext;
import com.zfoo.net.core.tcp.model.ServerSessionInactiveEvent;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.AttributeType;
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
    private IEntityCaches<String, UserEntity> userEntityCaches;


    @EventReceiver
    public void onServerSessionInactiveEvent(ServerSessionInactiveEvent event) {
        Session session = event.getSession();
        Long uid = session.getAttribute(AttributeType.UID);
        if (uid == null) {
            return;
        }

        OnlineRoleManager.removeSessionByUid(uid);
    }

    @PacketReceiver
    public void atUnameLoginRequest(Session session, UnameLoginRequest request) {
        String uname = request.getUname();
        String upwd = request.getUpwd();
        if (StringUtils.isBlank(uname) || StringUtils.isBlank(upwd)) {
            return;
        }

        UserEntity userEntity = userEntityCaches.load(uname);

        // 生成下账号
        if (userEntity.checkNull()) {
            long uid = MongoIdUtils.getIncrementIdFromMongoDefault(UserEntity.class);

            userEntity = new UserEntity();
            userEntity.setId(uname);
            userEntity.setPwd(upwd);
            userEntity.setUid(uid);

            OrmContext.getAccessor().insert(userEntity);
            userEntityCaches.invalidate(uname);

            userEntity = userEntityCaches.load(uname);
        }

        if (!userEntity.getPwd().equals(upwd)) {
            NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.PASSWORD_ERROR));
            return;
        }

        // 绑定下uid
        long uid = userEntity.getUid();
        OnlineRoleManager.bindUidSession(uid, session);

        // 查询下
        UnameLoginResponse response = new UnameLoginResponse();
        response.setStatus(1);
        response.setInRoom(true);

        NetContext.getRouter().send(session, response);
    }
}





















