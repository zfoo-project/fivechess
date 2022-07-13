package com.zfoo.fivechess.common.entity;

import com.zfoo.orm.model.anno.EntityCache;
import com.zfoo.orm.model.anno.Id;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.entity.IEntity;

@EntityCache(cacheStrategy = "tenThousand", persister = @Persister("time30s"))
public class UinfoEntity implements IEntity<String> {
    @Id
    private String id;

    private String password;

    private int coin;

    private long uid;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
