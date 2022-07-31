import {NetManager} from "../common/NetManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class LoginMain extends cc.Component {
    public static instance: LoginMain = null;

    @property({type: cc.Prefab})
    private loginPanel: cc.Prefab = null;

    @property
    public url: string = "ws://192.168.3.2:18000/websocket";

    onLoad() {
        LoginMain.instance = this;

        let loginPanel = cc.instantiate(this.loginPanel);
        this.node.addChild(loginPanel);
    }

    start() {
        NetManager.connect(LoginMain.instance.url);
    }

    update(dt: number) {
        NetManager.update();
    }
}
