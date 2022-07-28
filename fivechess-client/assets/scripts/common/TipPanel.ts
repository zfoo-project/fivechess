const {ccclass, property} = cc._decorator;

@ccclass
export default class TipPanel extends cc.Component {

    @property({type: cc.Label})
    private lbl_msg: cc.Label = null;
    private show_close_btn: boolean = true;
    private yes_cb: Function = null;

    public init(msg: string, show_close_btn: boolean = true, yes_cb ?: () => void) {
        this.lbl_msg.string = msg;
        this.show_close_btn = show_close_btn;
        this.yes_cb = yes_cb;

        if (!show_close_btn) {
            cc.find("btn_close", this.node).active = false;
        }
    }

    private btn_close() {
        this.node.destroy();
    }

    private btn_yes() {
        this.yes_cb && this.yes_cb();
        this.node.destroy();
    }
}
