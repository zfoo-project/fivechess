import LoginResponse from "../tsProtocol/login/LoginResponse";
import ServerData from "../game/login/ServerData";


/**
 * 这个类负责全局消息处理，只处理数据存储,不处理UI，保持客户端数据可服务器数据同步
 */

export default class ResponseManager {
    private static _inst: ResponseManager = null;

    private commandFuncMap = new Map<number, any>();

    public static inst(): ResponseManager {
        if (this._inst == null) {
            this._inst = new ResponseManager();
            this._inst.init();
        }
        return this._inst;
    }

    public processResponse(protocolId, packet): void {
        if (this.commandFuncMap[protocolId] == null) {
            return;
        }
        this.commandFuncMap[protocolId](packet);
    }

    /**
     * 下面主要是全局消息处理，收到服务器消息后，无论在哪个界面，都是先更新数据
     * @private
     */
    private init(): void {
        this.commandFuncMap[LoginResponse.prototype.protocolId()] = function (packet: LoginResponse) {
            ServerData.account = packet.account;
            ServerData.uid = packet.uid;
            ServerData.coin = packet.coin;
        }
    }
}

