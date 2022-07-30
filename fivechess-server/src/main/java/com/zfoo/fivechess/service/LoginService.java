package com.zfoo.fivechess.service;

import com.zfoo.fivechess.common.ErrorCodeEnum;
import com.zfoo.fivechess.entity.AccountEntity;
import com.zfoo.fivechess.protocol.common.ErrorResponse;
import com.zfoo.net.NetContext;
import com.zfoo.net.session.model.AttributeType;
import com.zfoo.net.session.model.Session;
import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.anno.EntityCachesInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.orm.util.MongoIdUtils;
import com.zfoo.protocol.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @EntityCachesInjection
    private IEntityCaches<String, AccountEntity> accountEntityCaches;

    @Autowired
    OnlineRoleService onlineRoleService;

    public boolean checkLoginParam(Session session, String account, String password) {
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.ACCOUNT_OR_PASSWORD_EMPTY_ERROR));
            return false;
        }

        return true;
    }

    public boolean checkAccountAndPassword(Session session, String dbPassword, String password) {
        if (!dbPassword.equals(password)) {
            NetContext.getRouter().send(session, ErrorResponse.valueOf(ErrorCodeEnum.PASSWORD_ERROR));
            return false;
        }
        return true;
    }

    public void bindUidWithSession(Session session, long uid) {
        // 绑定下uid
        session.putAttribute(AttributeType.UID, uid);
        // 玩家上线，记录下session
        onlineRoleService.bindUidSession(uid, session);
    }

    /**
     * 查询账号和密码，查询不到则注册
     * 在account线程,避免反复注册
     *
     * @param account
     * @param password
     * @return
     */
    public AccountEntity selectAndRegisterAccount(String account, String password) {
        // 如果账号不存在，则创建账号
        AccountEntity accountEntity = accountEntityCaches.load(account);
        if (accountEntity.checkNull()) {
            var uid = MongoIdUtils.getIncrementIdFromMongoDefault(AccountEntity.class);
            OrmContext.getAccessor().insert(AccountEntity.valueOf(uid, account, password));
            accountEntityCaches.invalidate(account);
        }

        return accountEntity;
    }
}
