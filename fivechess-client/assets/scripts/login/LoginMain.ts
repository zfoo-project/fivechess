import {NetManager} from "../common/NetManager";
import {UiManager, UiPanelEnum} from "../common/UiManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class LoginMain extends cc.Component {
    public static instance: LoginMain = null;

    @property({type: cc.String})
    public url: string = "ws://192.168.3.2:18000/websocket";

    start() {
        LoginMain.instance = this;

        // 登录UI弹出来
        UiManager.showPanel(UiPanelEnum.loginPanel);
    }

    update(dt: number) {
        NetManager.update();
    }
}
