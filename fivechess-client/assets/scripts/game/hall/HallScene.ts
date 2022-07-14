import ServerData from "../login/ServerData";
import NetManager from "../../net/NetManager";
import MatchRequest from "../../tsProtocol/protocol/MatchRequest";
import MatchResponse from "../../tsProtocol/protocol/MatchResponse";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallScene extends cc.Component {
    @property({type: cc.Label})
    private lblAccount: cc.Label = null;

    @property({type: cc.Label})
    private lblUid: cc.Label = null;

    @property({type: cc.Label})
    private lblCoin: cc.Label = null;

    @property({type: cc.Label})
    private lblMatching: cc.Label = null;

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

    public btnMatchClick() {
        let req = new MatchRequest();
        NetManager.inst().sendMessage(req);
    }

    processResponse(protocolId, packet) {
        if (protocolId == MatchResponse.prototype.protocolId()) {
            this.lblMatching.string = ServerData.matching ? "匹配中" : "匹配";
        }
    }
}
