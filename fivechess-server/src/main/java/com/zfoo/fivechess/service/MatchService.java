package com.zfoo.fivechess.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zfoo.fivechess.logic.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MatchService {

    @Autowired
    RoomService roomService;

    public static final String MATCH_HASH = "match_hash";
    private Set<Long> uidSet = Sets.newConcurrentHashSet();

    public boolean addToMatch(long uid) {
        return uidSet.add(uid);
    }

    public boolean deleteFromMatchQueue(long uid) {
        return uidSet.remove(uid);
    }

    public void checkMatchOk() {
        if (uidSet.size() >= 2) {
            List<Long> matchUidList = Lists.newArrayList(uidSet).subList(0, 2);
            uidSet.removeAll(matchUidList);

            int nextRoomId = roomService.getNextRoomId();
            Room room = new Room();
            room.setRoomId(nextRoomId);
            roomService.addRoom(room);
        }
    }
}
