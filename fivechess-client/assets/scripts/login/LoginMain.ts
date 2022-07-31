import {NetManager} from "../common/NetManager";
import {UiManager, UiPanelEnum} from "../common/UiManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class LoginMain extends cc.Component {
    public static instance: LoginMain = null;

    @property
    public url: string = "ws://192.168.3.2:18000/websocket";

    onLoad() {
        LoginMain.instance = this;
        UiManager.showPanel(UiPanelEnum.loginPanel);
    }

    update(dt: number) {
        NetManager.update();
    }
}
