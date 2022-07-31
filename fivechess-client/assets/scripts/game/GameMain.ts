import {NetManager} from "../common/NetManager";
import {UiManager, UiPanelEnum} from "../common/UiManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class GameMain extends cc.Component {
    onLoad() {
        UiManager.showPanel(UiPanelEnum.gamePanel);
    }

    update(dt) {
        NetManager.update();
    }
}
