import MatchRequest from "../tsProtocol/protocol/MatchRequest";
import MatchResponse from "../tsProtocol/protocol/MatchResponse";
import {NetManager} from "../common/NetManager";
import {PlayerInfo} from "../common/PlayerInfo";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallMain extends cc.Component {
    @property({type: cc.Label})
    private lblAccount: cc.Label = null;

    @property({type: cc.Label})
    private lblUid: cc.Label = null;

    @property({type: cc.Label})
    private lblCoin: cc.Label = null;

    @property({type: cc.Label})
    private lblMatching: cc.Label = null;

    onLoad() {
        NetManager.registerNetHandler(this);
    }

    start() {
        this.initView();
    }

    onDestroy() {
        NetManager.unregisterNetHandler(this);
    }

    private initView() {
        this.lblAccount.string = PlayerInfo.account;
        this.lblUid.string = PlayerInfo.uid.toString();
        this.lblCoin.string = PlayerInfo.coin.toString();
    }

    public btnMatchClick() {
        let req = new MatchRequest();
        NetManager.sendMessage(req);
    }

    processResponse(protocolId, packet) {
        if (protocolId == MatchResponse.prototype.protocolId()) {

        }
    }
}
