package com.zfoo.fivechess.common;

import com.zfoo.net.NetContext;
import com.zfoo.net.session.model.AttributeType;
import com.zfoo.net.session.model.Session;
import com.zfoo.protocol.IPacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 管理在线的玩家
 */
public class OnlineRoleManager {
    private static final Map<Long, Session> uidSessionMap = new ConcurrentHashMap<>();

    public static void bindUidSession(long uid, Session session) {
        if (uidSessionMap.containsKey(uid)) {
            return;
        }

        session.putAttribute(AttributeType.UID, uid);

        uidSessionMap.put(uid, session);
    }

    public static void removeSessionByUid(long uid) {
        uidSessionMap.remove(uid);
    }

    public static void sendMessage(long uid, IPacket packet) {
        var session = uidSessionMap.get(uid);
        if (session == null) {
            return;
        }

        NetContext.getRouter().send(session, packet);
    }
}
