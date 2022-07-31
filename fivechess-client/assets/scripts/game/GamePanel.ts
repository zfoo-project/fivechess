import MySeatInfoResponse from "../tsProtocol/protocol/MySeatInfoResponse";
import {NetManager} from "../common/NetManager";
import GameStartedResponse from "../tsProtocol/protocol/GameStartedResponse";
import TurnToPlayerResponse from "../tsProtocol/protocol/TurnToPlayerResponse";
import UserArrivedResponse from "../tsProtocol/protocol/UserArrivedResponse";
import GiveChessResponse from "../tsProtocol/protocol/GiveChessResponse";
import CheckoutResponse from "../tsProtocol/protocol/CheckoutResponse";
import UserQuitResponse from "../tsProtocol/protocol/UserQuitResponse";
import ReconnectInfoResponse from "../tsProtocol/protocol/ReconnectInfoResponse";
import PlayerLostConnectResponse from "../tsProtocol/protocol/PlayerLostConnectResponse";

const {ccclass, property} = cc._decorator;

@ccclass
export default class GamePanel extends cc.Component {
    @property({type: cc.SpriteFrame})
    private black_spriteframe: cc.SpriteFrame = null;

    @property({type: cc.SpriteFrame})
    private white_spriteframe: cc.SpriteFrame = null;

    @property({type: cc.Node})
    private node_chess_disk: cc.Node = null;

    @property({type: cc.Label})
    private lbl_roomId: cc.Label = null;

    // 服务器上自己的座位
    private sv_seatId: number = -1;
    // 谁是先手
    private button_id: number = -1;
    // 轮到谁了
    private cur_sv_id: number = -1;

    private CENTER_X: number = 7;
    private CENTER_Y: number = 7;
    private BLOCK_W: number = 41;
    private BLOCK_H: number = 41;

    onLoad() {
        NetManager.registerNetHandler(this);
    }

    onDestroy() {
        NetManager.unregisterNetHandler(this);
    }

    public turn_to_player(turnToSeatId) {
        this.cur_sv_id = turnToSeatId;
    }

    processResponse(protocolId, packet) {
        if (protocolId == MySeatInfoResponse.prototype.protocolId()) {
            let response: MySeatInfoResponse = packet;
            this.lbl_roomId.string = response.tableId + "";
            this.sv_seatId = response.seatId;
            this.node_chess_disk.destroyAllChildren();
        } else if (protocolId == UserArrivedResponse.prototype.protocolId()) {

        } else if (protocolId == GameStartedResponse.prototype.protocolId()) {
            let response: GameStartedResponse = packet;

            this.button_id = response.buttonId;
            this.cur_sv_id = -1;
            this.node_chess_disk.destroyAllChildren();
        } else if (protocolId == TurnToPlayerResponse.prototype.protocolId()) {
            let response: TurnToPlayerResponse = packet;

            this.turn_to_player(response.seatId);
        } else if (protocolId == GiveChessResponse.prototype.protocolId()) {

        } else if (protocolId == CheckoutResponse.prototype.protocolId()) {

        } else if (protocolId == UserQuitResponse.prototype.protocolId()) {

        } else if (protocolId == ReconnectInfoResponse.prototype.protocolId()) {

        } else if (protocolId == PlayerLostConnectResponse.prototype.protocolId()) {

        }
    }
}
