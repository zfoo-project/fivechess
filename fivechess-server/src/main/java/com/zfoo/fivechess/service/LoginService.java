package com.zfoo.fivechess.service;

import com.zfoo.fivechess.entity.UinfoEntity;
import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.anno.EntityCachesInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.orm.util.MongoIdUtils;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @EntityCachesInjection
    private IEntityCaches<String, UinfoEntity> uinfoEntityCaches;

    public UinfoEntity selectByAccount(String account) {
        return uinfoEntityCaches.load(account);
    }

    public UinfoEntity addUser(String account, String password) {
        long uid = MongoIdUtils.getIncrementIdFromMongoDefault(UinfoEntity.class);

        var newEntity = UinfoEntity.valueOf(account, password, 100, uid);
        OrmContext.getAccessor().insert(newEntity);
        uinfoEntityCaches.invalidate(newEntity.getAccount());

        return uinfoEntityCaches.load(account);
    }
}
