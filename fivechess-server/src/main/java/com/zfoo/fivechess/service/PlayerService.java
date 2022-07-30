package com.zfoo.fivechess.service;

import com.google.common.collect.Maps;
import com.zfoo.fivechess.logic.Player;
import com.zfoo.fivechess.utils.LogUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayerService {
    private final Map<Long, Player> uidPlayerMap = Maps.newConcurrentMap();

    public Player addPlayer(long uid) {
        if (uidPlayerMap.containsKey(uid)) {
            LogUtils.game.error("已存在{}", uid);
            return null;
        }

        Player player = new Player(uid);
        this.uidPlayerMap.put(uid, player);

        return player;
    }

    public void removePlayerByUid(long uid) {
        this.uidPlayerMap.remove(uid);
    }

    public Player getPlayerByUid(long uid) {
        return this.uidPlayerMap.get(uid);
    }
}
