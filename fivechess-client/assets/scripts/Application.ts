import EventManager from "./manager/EventManager";
import NetManager from "./manager/NetManager";
import {EventConfig} from "./config/EventConfig";
import SceneManager from "./manager/SceneManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class ClientStart extends cc.Component {
    @property(cc.String)
    private url: string = "ws://192.168.3.2:18000/websocket";

    @property({type: cc.Label})
    private lblStatus: cc.Label = null;

    onLoad() {
        this.init();

        EventManager.inst().registerEvent(this);
        NetManager.inst().connect(this.url);
    }

    onDestroy() {
        EventManager.inst().unregisterEvent(this);
    }

    init() {
        let action = cc.fadeIn(1.0);//渐显
        let action2 = cc.fadeOut(1.0);//渐隐效果

        let seq = cc.sequence(action2, action);
        this.lblStatus.node.runAction(cc.repeat(seq, 10));
    }

    processEvent(eventId, event) {
        if (eventId == EventConfig.CONNECTED_EVENT) {
            let waitTime: number = 1;
            this.lblStatus.string = "连接服务器成功! ${waitTime}后跳到登录";
            this.scheduleOnce(() => {
                SceneManager.inst().loadScene("Login");
            }, waitTime);
        }
    }
}
