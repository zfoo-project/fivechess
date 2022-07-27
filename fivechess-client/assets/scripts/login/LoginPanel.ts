import LoginRequest from "../tsProtocol/protocol/LoginRequest";
import LoginResponse from "../tsProtocol/protocol/LoginResponse";
import {NetManager} from "../common/NetManager";
import {EventEnum} from "../common/EventEnum";
import {EventManager} from "../common/EventManager";
import LoginMain from "./LoginMain";

const {ccclass, property} = cc._decorator;

@ccclass
export default class LoginPanel extends cc.Component {

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
        NetManager.connect(LoginMain.instance.url);
    }

    onDestroy() {
        EventManager.unregisterEventHandler(this);
        NetManager.unregisterNetHandler(this);
    }

    btn_login() {
        if (this.isConnecting) {
            return;
        }

        let request = new LoginRequest();
        request.account = this.editAccount.string;
        request.password = this.editPassword.string;
        NetManager.sendMessage(request);
    }

    processResponse(protocolId, response) {
        if (protocolId == LoginResponse.prototype.protocolId()) {
            cc.director.loadScene("main");
        }
    }

    processEvent(eventId, event) {
        if (eventId == EventEnum.CONNECTED_EVENT) {
            this.isConnecting = false;
            this.lblConnect.string = "连接服务器成功";
        } else if (eventId == EventEnum.DISCONNECT_EVENT) {
            this.lblConnect.string = "连接服务器失败...";
            this.scheduleOnce(() => {
                this.isConnecting = true;
                this.lblConnect.string = "正在重连...";
                NetManager.connect(LoginMain.instance.url);
            }, 2);
        }
    }
}
