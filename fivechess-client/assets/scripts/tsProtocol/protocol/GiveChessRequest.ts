

class GiveChessRequest {

    xBlock: number = 0;
    yBlock: number = 0;

    protocolId(): number {
        return 219;
    }

    static write(buffer: any, packet: GiveChessRequest | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.xBlock);
        buffer.writeInt(packet.yBlock);
    }

    static read(buffer: any): GiveChessRequest | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new GiveChessRequest();
        const result0 = buffer.readInt();
        packet.xBlock = result0;
        const result1 = buffer.readInt();
        packet.yBlock = result1;
        return packet;
    }
}

export default GiveChessRequest;
