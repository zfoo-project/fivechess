import TipPanel from "./TipPanel";

const {ccclass, property} = cc._decorator;

@ccclass
export class UiManager extends cc.Component {
    private static node: cc.Node = null;

    onLoad() {
        UiManager.node = this.node;
    }

    /**
     * @param url
     * @param cb 加载完毕执行的回调
     * @private
     */
    private static showPanel(url: string, cb ?: (node: cc.Node) => void) {
        cc.resources.load("uiPanel/" + url, (err, res: cc.Prefab) => {
            if (err) {
                cc.error(err);
                cb && cb(null);
                return
            }

            let node = cc.instantiate(res);
            node.parent = this.node;
            cb && cb(node);
        });
    }

    /**
     * 提示
     * @param msg
     * @param show_close_btn
     * @param yes_cb
     */
    public static showTip(msg: string, show_close_btn: boolean, yes_cb ?: () => void) {
        this.showPanel(UiPanelEnum.tipPanel, (node) => {
            node.getComponent(TipPanel).init(msg, show_close_btn, yes_cb);
        });
    }
}

export const enum UiPanelEnum {
    tipPanel = "tipPanel",         // 提示面板
}
