import UnameLoginResponse from "../tsProtocol/protocol/UnameLoginResponse";

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

GlobalMessageProcessManager.commandFuncMap[UnameLoginResponse.prototype.protocolId()] = function (packet: UnameLoginResponse) {

}


