

class ChessItem {

    color: number = 0;
    xBlock: number = 0;
    yBlock: number = 0;

    protocolId(): number {
        return 203;
    }

    static write(buffer: any, packet: ChessItem | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.color);
        buffer.writeInt(packet.xBlock);
        buffer.writeInt(packet.yBlock);
    }

    static read(buffer: any): ChessItem | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new ChessItem();
        const result0 = buffer.readInt();
        packet.color = result0;
        const result1 = buffer.readInt();
        packet.xBlock = result1;
        const result2 = buffer.readInt();
        packet.yBlock = result2;
        return packet;
    }
}

export default ChessItem;
