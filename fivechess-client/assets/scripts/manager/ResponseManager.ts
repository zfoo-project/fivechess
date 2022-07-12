import Pong from "../tsProtocol/common/Pong";

/**
 * 这个类负责全局消息处理，只处理数据存储,不处理UI，保持客户端数据可服务器数据同步
 */
const commandFuncMap = new Map<number, any>();

export default class ResponseManager {
    static processResponse(protocolId, packet) {
        if (commandFuncMap[protocolId] == null) {
            return;
        }
        commandFuncMap[protocolId](packet);
    }
}

// 下面主要是全局消息处理，收到服务器消息后，无论在哪个界面，都是先更新数据

commandFuncMap[Pong.prototype.protocolId()] = function (packet) {
    cc.log("global pong!!!");
}