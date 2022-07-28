package com.zfoo.fivechess.service;

import com.google.common.collect.Maps;
import com.zfoo.fivechess.logic.Player;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayerService {
    private final Map<Long, Player> uidPlayerMap = Maps.newConcurrentMap();

    public Player addAndGetPlayer(long uid, int roomId, int seatId) {
        return uidPlayerMap.computeIfAbsent(uid, k -> {
            Player player = new Player();
            player.setUid(uid);
            player.setRoomId(roomId);
            player.setSeatId(seatId);

            return player;
        });
    }

    public void deletePlayerByUid(long uid) {
        uidPlayerMap.remove(uid);
    }

    public Player getPlayerByUid(long uid) {
        return uidPlayerMap.get(uid);
    }
}
