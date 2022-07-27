import {PlayerInfo} from "./PlayerInfo";
import LoginResponse from "../tsProtocol/protocol/LoginResponse";
import MatchResponse from "../tsProtocol/protocol/MatchResponse";

/**
 * 这个类负责全局消息处理，只处理数据存储,不处理UI，保持客户端数据可服务器数据同步
 */
export class GlobalMessageProcessManager {
    public static commandFuncMap = new Map<number, any>();

    public static processResponse(protocolId, packet): void {
        if (GlobalMessageProcessManager.commandFuncMap[protocolId] == null) {
            return;
        }
        GlobalMessageProcessManager.commandFuncMap[protocolId](packet);
    }
}

GlobalMessageProcessManager.commandFuncMap[LoginResponse.prototype.protocolId()] = function (packet: LoginResponse) {
    PlayerInfo.uid = packet.uid;
    PlayerInfo.account = packet.account;
    PlayerInfo.roleInfoVo = packet.roleInfoVo;
}

GlobalMessageProcessManager.commandFuncMap[MatchResponse.prototype.protocolId()] = function (packet: MatchResponse) {

}
