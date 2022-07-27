/**
 * 负责事件订阅与发布
 */
export class EventManager {
    private static eventHandlerSet = new Set();

    public static registerEventHandler(handler) {
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
        EventManager.eventHandlerSet.forEach(handler => {
            // @ts-ignore
            handler.processEvent(eventId, event);
        });
    }
}

