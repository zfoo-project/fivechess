package com.zfoo.fivechess.entity;

import com.zfoo.orm.model.anno.EntityCache;
import com.zfoo.orm.model.anno.Id;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.entity.IEntity;
import lombok.Data;

@Data
@EntityCache(cacheStrategy = "tenThousand", persister = @Persister("time30s"))
public class UinfoEntity implements IEntity<String> {
    /**
     * 账号
     */
    @Id
    private String id;

    /**
     * 玩家id，游戏中使用
     */
    private long uid;

    /**
     * 密码
     */
    private String password;

    /**
     * 金币数
     */
    private int coin;

    public static UinfoEntity valueOf(String id, String password, int coin, long uid) {
        var entity = new UinfoEntity();
        entity.id = id;
        entity.password = password;
        entity.coin = coin;
        entity.uid = uid;
        return entity;
    }

    @Override
    public String id() {
        return id;
    }

    public String getAccount() {
        return id;
    }
}
