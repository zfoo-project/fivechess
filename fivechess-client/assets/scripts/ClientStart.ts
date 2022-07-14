import EventManager from "./event/EventManager";
import {EventConfig} from "./event/EventConfig";
import SceneManager from "./scene/SceneManager";
import NetManager from "./net/NetManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class ClientStart extends cc.Component {
    @property(cc.String)
    private url: string = "ws://192.168.3.2:18000/websocket";

    @property({type: cc.Label})
    private lblStatus: cc.Label = null;

    onLoad() {
        EventManager.inst().registerEvent(this);
        NetManager.inst().connect(this.url);
    }

    onDestroy() {
        EventManager.inst().unregisterEvent(this);
    }

    processEvent(eventId, event) {
        if (eventId == EventConfig.CONNECTED_EVENT) {
            this.lblStatus.string = "连接服务器成功! 2s后跳到登录";
            this.scheduleOnce(() => {
                SceneManager.inst().loadScene("Login");
            }, 2);
        }
    }
}
