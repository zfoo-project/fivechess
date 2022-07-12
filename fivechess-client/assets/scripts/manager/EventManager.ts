/**
 * 负责事件订阅与发布
 */
export default class EventManager {
    private static _inst: EventManager = null;

    private eventHandlerList = [];

    public static inst(): EventManager {
        if (this._inst == null) {
            this._inst = new EventManager();
        }
        return this._inst;
    }

    public registerEvent(handler) {
        for (let i = 0; i < this.eventHandlerList.length; i++) {
            if (this.eventHandlerList[i] == handler) {
                return;
            }
        }

        this.eventHandlerList.push(handler);
    }

    public unregisterEvent(handler) {
        for (let i = 0; i < this.eventHandlerList.length; i++) {
            if (this.eventHandlerList[i] == handler) {
                this.eventHandlerList.splice(i, 1);
                return;
            }
        }
    }

    public sendEvent(eventId, event) {
        for (let i = 0; i < this.eventHandlerList.length; i++) {
            if (this.eventHandlerList[i].processEvent == null) {
                cc.error("event handler must implement interface processEvent");
                continue;
            }
            this.eventHandlerList[i].processEvent(eventId, event);
        }
    }
}
