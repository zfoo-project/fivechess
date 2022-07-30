package com.zfoo.fivechess.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zfoo.fivechess.logic.Player;
import com.zfoo.fivechess.logic.Room;
import com.zfoo.fivechess.logic.RoomConst;
import com.zfoo.fivechess.protocol.GameStartResponse;
import com.zfoo.net.task.TaskBus;
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
    OnlineRoleService onlineRoleService;

    private Set<Long> matchingUidSet = Sets.newConcurrentHashSet();

    public boolean addToMatchQueue(long uid) {
        return matchingUidSet.add(uid);
    }

    public void deleteFromMatchQueue(long uid) {
        matchingUidSet.remove(uid);
    }

    public void checkMatch() {
        if (matchingUidSet.size() >= RoomConst.PLAYER_NUM) {
            // 匹配到的人
            List<Long> matchedUidList = Lists.newArrayList(matchingUidSet).subList(0, 2);
            // 删除匹配到的人
            matchingUidSet.removeAll(matchedUidList);
            // 新房间号
            int roomId = roomService.getNextRoomId();

            // 有了房间号后，所有的业务逻辑，都在房间线程执行(从无房间号到有房间号，是一个临界的过程)
            TaskBus.executor(roomId).execute(() -> {
                // 分配房间
                Room room = roomService.addAndGetRoom(roomId);
                // 把匹配到的人加入进来
                for (int seatId = 0; seatId < RoomConst.PLAYER_NUM; seatId++) {
                    // 匹配到的这个人的uid
                    long uid = matchedUidList.get(seatId);
                    // 绑定好座位
                    Player player = playerService.addAndGetPlayer(uid, roomId, seatId);
                    room.bindSeatIdWithPlayer(seatId, player);
                }

                // 座位全部安排好后，通知游戏开始
                matchedUidList.forEach(uid -> onlineRoleService.sendMessage(uid, GameStartResponse.valueOf()));
            });
        }
    }
}
