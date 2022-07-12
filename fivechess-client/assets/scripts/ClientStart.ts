import NetManager from "./manager/NetManager";
import EventManager from "./manager/EventManager";
import {EventConfig} from "./config/EventConfig";
import SceneManager from "./manager/SceneManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class ClientStart extends cc.Component {
    @property(cc.String)
    private url: string = "ws://192.168.3.2:18000/websocket";

    onLoad() {
        EventManager.inst().registerEvent(this);
        NetManager.inst().connect(this.url);
    }

    onDestroy() {
        EventManager.inst().unregisterEvent(this);
    }

    processEvent(eventId, event) {
        if (eventId == EventConfig.CONNECTED_EVENT) {
            SceneManager.loadScene("Login");
        }
    }
}
