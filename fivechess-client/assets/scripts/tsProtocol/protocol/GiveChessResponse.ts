

class GiveChessResponse {

    seatId: number = 0;
    status: number = 0;
    xBlock: number = 0;
    yBlock: number = 0;

    protocolId(): number {
        return 220;
    }

    static write(buffer: any, packet: GiveChessResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.seatId);
        buffer.writeInt(packet.status);
        buffer.writeInt(packet.xBlock);
        buffer.writeInt(packet.yBlock);
    }

    static read(buffer: any): GiveChessResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new GiveChessResponse();
        const result0 = buffer.readInt();
        packet.seatId = result0;
        const result1 = buffer.readInt();
        packet.status = result1;
        const result2 = buffer.readInt();
        packet.xBlock = result2;
        const result3 = buffer.readInt();
        packet.yBlock = result3;
        return packet;
    }
}

export default GiveChessResponse;
