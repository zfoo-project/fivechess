import {NetManager} from "../common/NetManager";
import UnameLoginResponse from "../tsProtocol/protocol/UnameLoginResponse";
import UnameLoginRequest from "../tsProtocol/protocol/UnameLoginRequest";
import SceneManager from "../common/SceneManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class LoginPanel extends cc.Component {
    @property({type: cc.EditBox})
    private edit_uname: cc.EditBox = null;

    @property({type: cc.EditBox})
    private edit_upwd: cc.EditBox = null;

    onLoad() {
        NetManager.registerNetHandler(this);
    }

    onDestroy() {
        NetManager.unregisterNetHandler(this);
    }

    btn_login() {
        let request = new UnameLoginRequest();
        request.uname = this.edit_uname.string;
        request.upwd = this.edit_upwd.string;
        NetManager.sendMessage(request);
    }

    processResponse(protocolId, packet) {
        if (protocolId == UnameLoginResponse.prototype.protocolId()) {
            let response: UnameLoginResponse = packet;

            if (response.status != 1) {
                cc.error("登录失败", response)
                return;
            }
            if (response.inRoom) {
                SceneManager.loadScene("game");
            } else {
                SceneManager.loadScene("main");
            }
        }
    }
}
