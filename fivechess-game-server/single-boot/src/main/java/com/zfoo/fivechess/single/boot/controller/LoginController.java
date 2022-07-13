package com.zfoo.fivechess.single.boot.controller;

import com.zfoo.fivechess.common.entity.UinfoEntity;
import com.zfoo.fivechess.common.protocol.login.LoginRequest;
import com.zfoo.fivechess.common.protocol.login.LoginResponse;
import com.zfoo.net.NetContext;
import com.zfoo.net.router.receiver.PacketReceiver;
import com.zfoo.net.session.model.Session;
import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.anno.EntityCachesInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.orm.util.MongoIdUtils;
import com.zfoo.protocol.util.AssertionUtils;
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

        if (StringUtils.isBlank(entity.getId())) {
            // 自增的id
            long uid = MongoIdUtils.getIncrementIdFromMongoDefault(UinfoEntity.class);
            // 插入账号
            var newEntity = UinfoEntity.valueOf(req.getAccount(), req.getPassword(), 100, uid);
            OrmContext.getAccessor().insert(newEntity);
            // 先失效，下次还从数据库中查询就可以查到
            uinfoEntityCaches.invalidate(newEntity.getId());
            // 再次查询一下
            entity = uinfoEntityCaches.load(req.getAccount());

            AssertionUtils.isTrue(!StringUtils.isBlank(entity.getId()), "账号没找到");
        } else {
            if (!entity.getPassword().equals(req.getPassword())) {
                logger.info("密码错误");
                return;
            }
        }

        NetContext.getRouter().send(session, LoginResponse.valueOf(entity.getUid(), entity.getId(), entity.getCoin()));
    }
}
