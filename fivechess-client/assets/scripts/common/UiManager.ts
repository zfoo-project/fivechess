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
}

export const enum UiPanelEnum {
    loginPanel = "loginPanel",
    hallPanel = "hallPanel",
}
