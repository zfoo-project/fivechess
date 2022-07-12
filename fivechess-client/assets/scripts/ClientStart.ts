import NetManager from "./manager/NetManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class ClientStart extends cc.Component {
    @property(cc.String)
    private url: string = "ws://192.168.3.2:18000/websocket";

    onLoad() {
        NetManager.inst().connect(this.url);
    }
}
