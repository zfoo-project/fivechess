import {EventEnum} from "./EventEnum";

export class GlobalEventProcessManager {
    public static eventFuncMap = new Map<number, any>();

    public static processEvent(eventId, event): void {
        if (GlobalEventProcessManager.eventFuncMap[eventId] == null) {
            return;
        }
        GlobalEventProcessManager.eventFuncMap[eventId](event);
    }
}

GlobalEventProcessManager.eventFuncMap[EventEnum.DISCONNECT_EVENT] = function (event) {

}
