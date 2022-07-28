package com.zfoo.fivechess.entity;

import com.zfoo.fivechess.protocol.common.RoleInfoVo;
import com.zfoo.orm.model.anno.EntityCache;
import com.zfoo.orm.model.anno.Id;
import com.zfoo.orm.model.anno.Index;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.scheduler.util.TimeUtils;
import lombok.Data;

@Data
@EntityCache(cacheStrategy = "tenThousand", persister = @Persister("time30s"))
public class AccountEntity implements IEntity<String> {
    /**
     * 自增的uid
     */
    @Index(ascending = true, unique = true)
    private long uid;

    /**
     * 账号account,因为查询需要根据这个查询
     */
    @Id
    private String id;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册时间
     */
    private long regTime;

    /**
     * 游戏内玩家
     */
    private RoleInfoVo roleInfoVo = new RoleInfoVo();

    public static AccountEntity valueOf(long uid, String account, String password) {
        var entity = new AccountEntity();
        entity.uid = uid;
        entity.id = account;
        entity.password = password;
        entity.regTime = TimeUtils.now();
        return entity;
    }

    @Override
    public String id() {
        return id;
    }
}
