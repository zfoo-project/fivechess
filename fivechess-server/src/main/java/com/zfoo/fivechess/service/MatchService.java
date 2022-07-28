package com.zfoo.fivechess.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zfoo.fivechess.logic.Player;
import com.zfoo.fivechess.logic.Room;
import com.zfoo.fivechess.logic.RoomConst;
import com.zfoo.fivechess.protocol.GameStartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MatchService {

    @Autowired
    RoomService roomService;

    @Autowired
    PlayerService playerService;

    @Autowired
    OnlineService onlineService;

    public static final String MATCH_HASH = "match_hash";
    private Set<Long> uidSet = Sets.newConcurrentHashSet();

    public boolean addToMatch(long uid) {
        return uidSet.add(uid);
    }

    public void deleteFromMatchQueue(long uid) {
        uidSet.remove(uid);
    }

    public void checkMatchOk() {
        if (uidSet.size() >= RoomConst.PLAYER_NUM) {
            // 匹配到的人
            List<Long> matchUidList = Lists.newArrayList(uidSet).subList(0, 2);
            uidSet.removeAll(matchUidList);

            // 新房间号
            int roomId = roomService.getNextRoomId();

            // 分配房间
            Room room = roomService.addAndGetRoom(roomId);

            // 把匹配到的人加入进来
            for (int i = 0; i < RoomConst.PLAYER_NUM; i++) {
                int seatId = i;
                long uid = matchUidList.get(seatId);

                Player player = playerService.addAndGetPlayer(uid, roomId, seatId);
                room.bindSeatIdWithPlayer(seatId, player);

                // 通知游戏开始
                onlineService.sendMessage(uid, GameStartResponse.valueOf());
            }
        }
    }
}
