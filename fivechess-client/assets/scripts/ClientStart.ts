import NetManager from "./manager/NetManager";
import Pong from "./tsProtocol/common/Pong";

const {ccclass, property} = cc._decorator;

@ccclass
export default class ClientStart extends cc.Component {
    @property(cc.String)
    private url: string = "ws://192.168.3.2:18000/websocket";

    onLoad() {
        NetManager.inst().registerNet(this);

        NetManager.inst().connect(this.url);
    }

    onDestroy() {
        NetManager.inst().unregisterNet(this);
    }

    processResponse(packet) {
        switch (packet.protocolId()) {
            case Pong.prototype.protocolId():
                
                break;
            default:

                break;
        }
    }
}
