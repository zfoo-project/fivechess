package com.zfoo.fivechess.service;

import com.google.common.collect.Maps;
import com.zfoo.net.NetContext;
import com.zfoo.net.session.model.Session;
import com.zfoo.protocol.IPacket;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OnlineService {
    private Map<Long, Session> uidSessionMap = Maps.newConcurrentMap();

    public void bindUidSession(long uid, Session session) {
        uidSessionMap.computeIfAbsent(uid, k -> session);
    }

    public void removeUid(long uid) {
        uidSessionMap.remove(uid);
    }

    public void sendMessage(long uid, IPacket packet) {
        var session = uidSessionMap.get(uid);
        if (session == null) {
            return;
        }

        NetContext.getRouter().send(session, packet);
    }
}
