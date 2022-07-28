import TipPanel from "./TipPanel";

const {ccclass, property} = cc._decorator;

@ccclass
export class UiManager extends cc.Component {
    private static node: cc.Node = null;

    onLoad() {
        UiManager.node = this.node;
    }

    public static showPanel(url: string, cb ?: (node: cc.Node) => void) {
        cc.resources.load("uiPanel/" + url, (err, res: cc.Prefab) => {
            if (err) {
                cc.error(err);
                return cb && cb(null);
            }

            let node = cc.instantiate(res);
            node.parent = this.node;
            cb && cb(node);
        });
    }

    public static showTip(msg: string, show_close_btn: boolean, yes_cb ?: () => void) {
        this.showPanel(UiPanelEnum.tipPanel, (node) => {
            node.getComponent(TipPanel).init(msg, show_close_btn, yes_cb);
        });
    }
}

export const enum UiPanelEnum {
    tipPanel = "tipPanel",         // 提示面板
    loginPanel = "loginPanel",     // 登录面板
    hallPanel = "hallPanel",       // 大厅面板
}
