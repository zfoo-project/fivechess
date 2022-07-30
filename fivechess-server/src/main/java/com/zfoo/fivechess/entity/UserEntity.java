package com.zfoo.fivechess.entity;

import com.zfoo.fivechess.protocol.common.RoleInfoVo;
import com.zfoo.orm.model.anno.EntityCache;
import com.zfoo.orm.model.anno.Id;
import com.zfoo.orm.model.anno.Index;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.entity.IEntity;
import lombok.Data;

@Data
@EntityCache(cacheStrategy = "tenThousand", persister = @Persister("time30s"))
public class UserEntity implements IEntity<String> {

    /**
     * 账号uname,因为查询需要根据这个查询
     */
    @Id
    private String id;

    /**
     * 密码
     */
    private String pwd;

    // 自增
    /**
     * 自增的uid
     */
    @Index(ascending = true, unique = true)
    private long uid;

    /**
     * 游戏内玩家
     */
    private RoleInfoVo roleInfoVo = new RoleInfoVo();

    @Override
    public String id() {
        return id;
    }
}
