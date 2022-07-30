import {GlobalEventProcessManager} from "./GlobalEventProcessManager";

/**
 * 负责事件订阅与发布
 */
export class EventManager {
    private static eventHandlerSet: any = new Set();

    public static registerEventHandler(handler: any) {
        if (EventManager.eventHandlerSet.has(handler)) {
            return;
        }

        EventManager.eventHandlerSet.add(handler);
    }

    public static unregisterEventHandler(handler) {
        if (!EventManager.eventHandlerSet.has(handler)) {
            return;
        }
        EventManager.eventHandlerSet.delete(handler);
    }

    public static sendEvent(eventId, event) {
        GlobalEventProcessManager.processEvent(eventId, event);

        EventManager.eventHandlerSet.forEach(handler => {
            if(handler.processEvent != null){
                handler.processEvent(eventId, event);
            }
        });
    }
}


