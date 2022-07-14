import NetManager from "../../net/NetManager";
import LoginRequest from "../../tsProtocol/login/LoginRequest";
import LoginResponse from "../../tsProtocol/login/LoginResponse";
import SceneManager from "../../scene/SceneManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class LoginScene extends cc.Component {
    @property({type: cc.EditBox})
    private editAccount: cc.EditBox = null;
    @property({type: cc.EditBox})
    private editPassword: cc.EditBox = null;

    onLoad() {
        NetManager.inst().registerNet(this);
    }

    onDestroy() {
        NetManager.inst().unregisterNet(this);
    }

    btnLoginClick() {
        let req = new LoginRequest();
        req.account = this.editAccount.string;
        req.password = this.editPassword.string;
        NetManager.inst().sendMessage(req);
    }

    processResponse(protocolId, packet) {
        if (protocolId == LoginResponse.prototype.protocolId()) {
            SceneManager.inst().loadScene("Hall");
        }
    }
}
