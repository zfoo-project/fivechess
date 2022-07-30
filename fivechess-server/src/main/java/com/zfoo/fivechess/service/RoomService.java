package com.zfoo.fivechess.service;

import com.google.common.collect.Maps;
import com.zfoo.fivechess.logic.Room;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RoomService {
    private AtomicInteger roomId = new AtomicInteger(0);
    
    private Map<Integer, Room> roomMap = Maps.newConcurrentMap();

    public int getNextRoomId() {
        return roomId.incrementAndGet();
    }

    public Room getRoomById(int roomId) {
        return roomMap.get(roomId);
    }

    public Room addAndGetRoom(int roomId) {
        return roomMap.computeIfAbsent(roomId, k -> {
            Room room = new Room();
            room.setRoomId(roomId);
            return room;
        });
    }

    public void deleteRoomById(int roomId) {
        roomMap.remove(roomId);
    }
}
