import Pong from "../tsProtocol/common/Pong";
import NetManager from "../manager/NetManager";
import Ping from "../tsProtocol/common/Ping";

const {ccclass, property} = cc._decorator;

@ccclass
export default class LoginScene extends cc.Component {
    onLoad() {
        NetManager.inst().registerNet(this);
    }

    start() {
        // 用于测试
        NetManager.inst().sendMessage(new Ping());
    }

    onDestroy() {
        NetManager.inst().unregisterNet(this);
    }

    onPong(msg: Pong) {
        cc.log("inner pong", msg.time);
    }

    processResponse(protocolId, packet) {
        if (protocolId == Pong.prototype.protocolId()) {
            this.onPong(packet);
        }
    }
}
