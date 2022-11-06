package com.zfoo.fivechess.logic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    private static final Map<Long, Player> uidPlayerMap = new ConcurrentHashMap<>();

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
