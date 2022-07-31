package com.zfoo.fivechess.logic;

import com.google.common.collect.Maps;

import java.util.Map;

public class PlayerManager {
    private static final Map<Long, Player> uidPlayerMap = Maps.newConcurrentMap();

    public static Player addPlayer(long uid) {
        if (uidPlayerMap.containsKey(uid)) {
            return null;
        }

        Player player = new Player(uid);
        uidPlayerMap.put(uid, player);
        return player;
    }

    public static void removePlayerByUid(long uid) {
        uidPlayerMap.remove(uid);
    }

    public static Player getPlayerByUid(long uid) {
        return uidPlayerMap.get(uid);
    }
}
