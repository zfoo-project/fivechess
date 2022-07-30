import {NetManager} from "../common/NetManager";
import CreateRoomRequest from "../tsProtocol/protocol/CreateRoomRequest";
import CreateRoomResponse from "../tsProtocol/protocol/CreateRoomResponse";

const {ccclass, property} = cc._decorator;

@ccclass
export default class HallPanel extends cc.Component {

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

    processResponse(protocolId, response) {
        if (protocolId == CreateRoomResponse.prototype.protocolId()) {

        }
    }
}
