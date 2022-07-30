

class MySeatInfoResponse {

    seatId: number = 0;
    tableId: number = 0;

    protocolId(): number {
        return 226;
    }

    static write(buffer: any, packet: MySeatInfoResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.seatId);
        buffer.writeInt(packet.tableId);
    }

    static read(buffer: any): MySeatInfoResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new MySeatInfoResponse();
        const result0 = buffer.readInt();
        packet.seatId = result0;
        const result1 = buffer.readInt();
        packet.tableId = result1;
        return packet;
    }
}

export default MySeatInfoResponse;
