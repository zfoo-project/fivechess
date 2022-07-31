import {NetManager} from "../common/NetManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class GameMain extends cc.Component {

    @property({type: cc.Prefab})
    private gamePanel: cc.Prefab = null;

    onLoad() {
        let gamePanel = cc.instantiate(this.gamePanel);
        this.node.addChild(gamePanel);
    }

    update(dt) {
        NetManager.update();
    }
}
