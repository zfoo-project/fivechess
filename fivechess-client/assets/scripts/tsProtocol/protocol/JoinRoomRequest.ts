

class JoinRoomRequest {

    tableId: number = 0;

    protocolId(): number {
        return 215;
    }

    static write(buffer: any, packet: JoinRoomRequest | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.tableId);
    }

    static read(buffer: any): JoinRoomRequest | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new JoinRoomRequest();
        const result0 = buffer.readInt();
        packet.tableId = result0;
        return packet;
    }
}

export default JoinRoomRequest;
