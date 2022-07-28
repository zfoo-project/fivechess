import {NetManager} from "../common/NetManager";
import {PlayerInfo} from "../common/PlayerInfo";
import MatchRequest from "../tsProtocol/protocol/MatchRequest";
import MatchResponse from "../tsProtocol/protocol/MatchResponse";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallPanel extends cc.Component {
    @property({type: cc.Label})
    private lbl_account: cc.Label = null;

    @property({type: cc.Label})
    private lbl_coin: cc.Label = null;

    @property({type: cc.Label})
    private lbl_matchStatus: cc.Label = null;

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
        this.lbl_account.string = PlayerInfo.account;
        this.lbl_coin.string = PlayerInfo.roleInfoVo.gold.toString();
    }

    public btn_match() {
        let request = new MatchRequest();
        NetManager.sendMessage(request);
    }

    processResponse(protocolId, packet) {
        if (protocolId == MatchResponse.prototype.protocolId()) {
            this.lbl_matchStatus.string = "匹配成功";
        }
    }
}
