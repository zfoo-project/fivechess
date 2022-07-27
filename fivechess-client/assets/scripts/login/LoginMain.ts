import LoginRequest from "../tsProtocol/protocol/LoginRequest";
import LoginResponse from "../tsProtocol/protocol/LoginResponse";
import {NetManager} from "../common/NetManager";
import {EventEnum} from "../common/EventEnum";
import {EventManager} from "../common/EventManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class LoginMain extends cc.Component {
    private url: string = "ws://192.168.3.2:18000/websocket";
    private isConnecting = false;

    @property({type: cc.EditBox})
    private editAccount: cc.EditBox = null;
    @property({type: cc.EditBox})
    private editPassword: cc.EditBox = null;

    @property({type: cc.Label})
    private lblConnect: cc.Label = null;

    onLoad() {
        EventManager.registerEventHandler(this);
        NetManager.registerNetHandler(this);
    }

    start() {
        this.isConnecting = true;
        this.lblConnect.string = "正在连接服务器...";
        NetManager.connect(this.url);
    }

    update(dt: number) {
        NetManager.update();
    }

    onDestroy() {
        EventManager.unregisterEventHandler(this);
        NetManager.unregisterNetHandler(this);
    }

    btnLoginClick() {
        if (this.isConnecting) {
            return;
        }

        let request = new LoginRequest();
        request.account = this.editAccount.string;
        request.password = this.editPassword.string;
        NetManager.sendMessage(request);
    }

    processResponse(protocolId, packet) {
        if (protocolId == LoginResponse.prototype.protocolId()) {
            cc.director.loadScene("main");
        }
    }

    processEvent(eventId, event) {
        if (eventId == EventEnum.CONNECTED_EVENT) {
            this.isConnecting = false;
            this.lblConnect.string = "连接服务器成功";
        } else if (eventId == EventEnum.DISCONNECT_EVENT) {
            this.scheduleOnce(() => {
                this.isConnecting = true;
                this.lblConnect.string = "正在连接服务器...";
                NetManager.connect(this.url);
            }, 2);
        }
    }
}
