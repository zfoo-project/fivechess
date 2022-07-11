import ProtocolManager from "./tsProtocol/ProtocolManager";
import ByteBuffer from "./tsProtocol/buffer/ByteBuffer";
import Ping from "./tsProtocol/common/Ping";

const {ccclass, property} = cc._decorator;

@ccclass
export default class NetManager extends cc.Component {

    // onLoad () {}

    start () {
        const ws = new WebSocket("ws://192.168.3.2:18000/websocket");
        ws.binaryType = 'arraybuffer';

        ws.onopen = function() {
            console.log('websocket open success');

            const packet = new Ping();
			cc.log(packet);

            const byteBuffer = new ByteBuffer();
            byteBuffer.setWriteOffset(4);
            ProtocolManager.write(byteBuffer, packet);
            byteBuffer.writeBoolean(false);
         
            const writeOffset = byteBuffer.writeOffset;
            byteBuffer.setWriteOffset(0);
            byteBuffer.writeRawInt(writeOffset - 4);
            byteBuffer.setWriteOffset(writeOffset);
            ws.send(byteBuffer.buffer);
        };
		
		ws.onmessage = function(event) {
			const data = event.data;

			const byteBuffer = new ByteBuffer();
			byteBuffer.writeBytes(data);
			byteBuffer.setReadOffset(4);
			const packet = ProtocolManager.read(byteBuffer);
			byteBuffer.readBoolean();
			console.log('Websocket收到:', packet);
		};
    }
}
