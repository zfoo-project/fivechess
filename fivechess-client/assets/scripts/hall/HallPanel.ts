import {NetManager} from "../common/NetManager";
import {PlayerInfo} from "../common/PlayerInfo";
import MatchRequest from "../tsProtocol/protocol/MatchRequest";
import MatchResponse from "../tsProtocol/protocol/MatchResponse";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallPanel extends cc.Component {
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
        this.init_view();
    }

    onDestroy() {
        NetManager.unregisterNetHandler(this);
    }

    private init_view() {
        this.lblAccount.string = PlayerInfo.account;
        this.lblUid.string = PlayerInfo.uid.toString();
        this.lblCoin.string = PlayerInfo.roleInfoVo.gold.toString();
    }

    public btn_match() {
        let request = new MatchRequest();
        NetManager.sendMessage(request);
    }

    processResponse(protocolId, packet) {
        if (protocolId == MatchResponse.prototype.protocolId()) {
            this.lblMatching.string = "匹配成功";
        }
    }
}
