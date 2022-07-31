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
import GiveChessRequest from "../tsProtocol/protocol/GiveChessRequest";
import {UiManager} from "../common/UiManager";
import SceneManager from "../common/SceneManager";

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

    start() {
        this.node_chess_disk.on(cc.Node.EventType.TOUCH_START, this.on_chessboard_click, this);
    }

    onDestroy() {
        NetManager.unregisterNetHandler(this);
    }

    on_chessboard_click(event) {
        event.stopPropagation();

        if (this.sv_seatId == -1) {
            cc.log("服务器未分配座位");
            return;
        }

        if (this.cur_sv_id != this.sv_seatId) {
            cc.log("不轮到你下棋");
            return;
        }

        let w_pos = event.getLocation();
        let pos = this.node_chess_disk.parent.convertToNodeSpaceAR(w_pos);

        let xblock = Math.floor((pos.x + 0.5 * this.BLOCK_W) / this.BLOCK_W) + 7;
        let yblock = Math.floor((pos.y + 0.5 * this.BLOCK_H) / this.BLOCK_H) + 7;

        if (xblock < 0 || xblock > 14 || yblock < 0 || yblock > 14) {
            return;
        }

        let request = new GiveChessRequest();
        request.xBlock = xblock;
        request.yBlock = yblock;
        NetManager.sendMessage(request);
    }

    public turn_to_player(turnToSeatId) {
        this.cur_sv_id = turnToSeatId;
    }

    private reset_chess_at(xblock: number, yblock: number, color: number) {
        let sp = null;
        if (color == 1) {
            sp = this.black_spriteframe;
        } else {
            sp = this.white_spriteframe;
        }

        let node = new cc.Node();
        let s_comp = node.addComponent(cc.Sprite);
        s_comp.spriteFrame = sp;

        let xpos = (xblock - this.CENTER_X) * this.BLOCK_W;
        let ypos = (yblock - this.CENTER_Y) * this.BLOCK_H;
        this.node_chess_disk.addChild(node);
        node.x = xpos;
        node.y = ypos;
        node.scale = 2;
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
            let response: GiveChessResponse = packet;
            if (response.status != 1) {
                cc.error("下棋错误", response)
                return;
            }

            let sp = null;
            if (response.seatId == this.button_id) {
                sp = this.black_spriteframe;
            } else {
                sp = this.white_spriteframe;
            }

            let node = new cc.Node();
            let s_comp = node.addComponent(cc.Sprite);
            s_comp.spriteFrame = sp;

            let xpos = (response.xBlock - this.CENTER_X) * this.BLOCK_W;
            let ypos = (response.yBlock - this.CENTER_Y) * this.BLOCK_H;
            this.node_chess_disk.addChild(node);
            node.x = xpos;
            node.y = ypos;
            node.scale = 2;

        } else if (protocolId == CheckoutResponse.prototype.protocolId()) {
            UiManager.showTip("游戏结束", false, () => {
                SceneManager.loadScene("main");
            });
        } else if (protocolId == UserQuitResponse.prototype.protocolId()) {

        } else if (protocolId == ReconnectInfoResponse.prototype.protocolId()) {
            this.node_chess_disk.destroyAllChildren();

            let response: ReconnectInfoResponse = packet;
            this.button_id = response.buttonId;
            this.turn_to_player(response.seatId);

            for (let i = 0; i < response.chessItems.length; i++) {
                let xblock = response.chessItems[i].xBlock;
                let yblock = response.chessItems[i].yBlock;
                let color = response.chessItems[i].color;

                this.reset_chess_at(xblock, yblock, color);
            }
        } else if (protocolId == PlayerLostConnectResponse.prototype.protocolId()) {

        }
    }


}
