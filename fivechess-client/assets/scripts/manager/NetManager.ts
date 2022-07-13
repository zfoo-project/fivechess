import ProtocolManager from "./../tsProtocol/ProtocolManager";
import ByteBuffer from "./../tsProtocol/buffer/ByteBuffer";
import ResponseManager from "./ResponseManager";
import EventManager from "./EventManager";
import {EventConfig} from "../config/EventConfig";

const {ccclass, property} = cc._decorator;

enum State {
    DISCONNECT = 1,  // 未连接
    CONNECTING = 2,  // 连接中
    CONNECTED = 3,   // 已连接
}

/**
 * 负责网络连接处理
 */
@ccclass
export default class NetManager extends cc.Component {
    private state: State = State.DISCONNECT;
    private socket: WebSocket = null;
    private msgQueue = [];
    private _isLock: boolean = false;
    private static _inst: NetManager = null;
    private responseHandlerList = [];

    public static inst(): NetManager {
        if (NetManager._inst == null) {
            let node = new cc.Node();
            NetManager._inst = node.addComponent(NetManager);
            cc.game.addPersistRootNode(node);
        }
        return NetManager._inst;
    }
    
    /**
     * 连接服务器
     */
    public connect(url: string) {
        if (this.state != State.DISCONNECT) {
            return;
        }

        // 网络处理
        this.state = State.CONNECTING;
        this.socket = new WebSocket(url);
        this.socket.binaryType = 'arraybuffer';

        this.socket.onopen = this.onOpen.bind(this);
        this.socket.onmessage = this.onMessage.bind(this);
        this.socket.onclose = this.onClose.bind(this);
        this.socket.onerror = this.onClose.bind(this);
    }

    /**
     * 发送消息
     */
    public sendMessage(packet) {
        if (this.state != State.CONNECTED) {
            cc.error("only can send msg on CONNECTED status!!!");
            return;
        }

        if (this.socket == null) {
            cc.error("socket null, can not send msg");
            return;
        }

        const byteBuffer = new ByteBuffer();
        byteBuffer.setWriteOffset(4);
        ProtocolManager.write(byteBuffer, packet);
        byteBuffer.writeBoolean(false);

        const writeOffset = byteBuffer.writeOffset;
        byteBuffer.setWriteOffset(0);
        byteBuffer.writeRawInt(writeOffset - 4);
        byteBuffer.setWriteOffset(writeOffset);
        this.socket.send(byteBuffer.buffer);

        cc.log("send:", packet);
    }

    public registerNet(handler) {
        for (let i = 0; i < this.responseHandlerList.length; i++) {
            if (this.responseHandlerList[i] == handler) {
                return;
            }
        }

        this.responseHandlerList.push(handler);
    }

    public unregisterNet(handler) {
        for (let i = 0; i < this.responseHandlerList.length; i++) {
            if (this.responseHandlerList[i] == handler) {
                this.responseHandlerList.splice(i, 1);
                return;
            }
        }
    }

    /**
     * 这个不可在业务中调用，仅仅是SceneManager内部封装切场景使用，避免切场景时收到消息而没处理
     * @param value
     */
    public setLock(value: boolean) {
        this._isLock = value;
    }

    /**
     * 连上服务器
     * @param event
     * @private
     */
    private onOpen(event) {
        console.log('connect to server success!!!');
        this.state = State.CONNECTED;

        EventManager.inst().sendEvent(EventConfig.CONNECTED_EVENT, null);
    }

    /**
     * 收到服务器消息
     * @param event
     * @private
     */
    private onMessage(event) {
        const data = event.data;

        const byteBuffer = new ByteBuffer();
        byteBuffer.writeBytes(data);
        byteBuffer.setReadOffset(4);
        const packet = ProtocolManager.read(byteBuffer);
        byteBuffer.readBoolean();

        console.log('recv:', packet);
        this.msgQueue.push(packet);
    }

    /**
     * 连接断开
     * @private
     */
    private onClose() {
        if (this.state == State.CONNECTED && this.socket != null) {
            this.socket.close();
            this.socket = null;

            console.log('disconnect to server!!!');
        }
        this.state = State.DISCONNECT;
    }

    update(dt: number) {
        if (this._isLock) {
            return;
        }

        if (this.msgQueue.length == 0) {
            return;
        }

        let packet = this.msgQueue.shift();

        // 先是全局消息进行处理同步好服务器数据
        ResponseManager.processResponse(packet.protocolId(), packet);

        // 处理各个handler
        for (let i = 0; i < this.responseHandlerList.length; i++) {
            if (this.responseHandlerList[i].processResponse == null) {
                cc.error("handler must implement processResponse interface!!!");
                continue;
            }
            this.responseHandlerList[i].processResponse(packet.protocolId(), packet);
        }
    }
}
