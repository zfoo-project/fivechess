import {PlayerInfo} from "./PlayerInfo";
import LoginResponse from "../tsProtocol/protocol/LoginResponse";
import MatchResponse from "../tsProtocol/protocol/MatchResponse";

/**
 * 这个类负责全局消息处理，只处理数据存储,不处理UI，保持客户端数据可服务器数据同步
 */
export class ResponseManager {
    public static commandFuncMap = new Map<number, any>();

    public static processResponse(protocolId, packet): void {
        if (ResponseManager.commandFuncMap[protocolId] == null) {
            return;
        }
        ResponseManager.commandFuncMap[protocolId](packet);
    }
}

ResponseManager.commandFuncMap[LoginResponse.prototype.protocolId()] = function (packet: LoginResponse) {
    PlayerInfo.account = packet.account;
    PlayerInfo.uid = packet.uid;
    PlayerInfo.coin = packet.coin;
}

ResponseManager.commandFuncMap[MatchResponse.prototype.protocolId()] = function (packet: MatchResponse) {
    PlayerInfo.matching = true;
}
