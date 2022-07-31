import {UiManager, UiPanelEnum} from "../common/UiManager";
import {NetManager} from "../common/NetManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallMain extends cc.Component {
    onLoad() {
        UiManager.showPanel(UiPanelEnum.hallPanel);
    }

    update(dt: number) {
        NetManager.update();
    }
}
