import {NetManager} from "../common/NetManager";
import CreateRoomRequest from "../tsProtocol/protocol/CreateRoomRequest";
import CreateRoomResponse from "../tsProtocol/protocol/CreateRoomResponse";
import JoinRoomRequest from "../tsProtocol/protocol/JoinRoomRequest";
import JoinRoomResponse from "../tsProtocol/protocol/JoinRoomResponse";
import SceneManager from "../common/SceneManager";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallPanel extends cc.Component {
    @property({type: cc.EditBox})
    private edit_joinRoomId: cc.EditBox = null;

    onLoad() {
        NetManager.registerNetHandler(this);
    }

    onDestroy() {
        NetManager.unregisterNetHandler(this);
    }

    public btn_createRoom() {
        let request = new CreateRoomRequest();
        NetManager.sendMessage(request);
    }

    public btn_joinRoom() {
        let request = new JoinRoomRequest();
        request.tableId = Number(this.edit_joinRoomId.string);
        NetManager.sendMessage(request);
    }

    processResponse(protocolId, packet) {
        if (protocolId == CreateRoomResponse.prototype.protocolId()) {
            let response: CreateRoomResponse = packet;
            if (response.status == 1) {
                SceneManager.loadScene("game");
            }
        } else if (protocolId == JoinRoomResponse.prototype.protocolId()) {
            let response: JoinRoomResponse = packet;
            if (response.status == 1) {
                SceneManager.loadScene("game");
            }
        }
    }
}
