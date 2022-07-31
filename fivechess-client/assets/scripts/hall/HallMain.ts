import {NetManager} from "../common/NetManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallMain extends cc.Component {
    @property({type: cc.Prefab})
    private hallPanel: cc.Prefab = null;

    onLoad() {
        let hallPanel = cc.instantiate(this.hallPanel);
        this.node.addChild(hallPanel);
    }

    update(dt: number) {
        NetManager.update();
    }
}
