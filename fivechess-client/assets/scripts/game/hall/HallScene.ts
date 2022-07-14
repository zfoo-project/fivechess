import ServerData from "../login/ServerData";
import NetManager from "../../net/NetManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallScene extends cc.Component {
    @property({type: cc.Label})
    private lblAccount: cc.Label = null;

    @property({type: cc.Label})
    private lblUid: cc.Label = null;

    @property({type: cc.Label})
    private lblCoin: cc.Label = null;

    onLoad() {
        NetManager.inst().registerNet(this);
    }

    start() {
        this.initView();
    }

    onDestroy() {
        NetManager.inst().unregisterNet(this);
    }

    private initView() {
        this.lblAccount.string = ServerData.account;
        this.lblUid.string = ServerData.uid;
        this.lblCoin.string = ServerData.coin;
    }

    public btnMatchClick(){

    }
}
