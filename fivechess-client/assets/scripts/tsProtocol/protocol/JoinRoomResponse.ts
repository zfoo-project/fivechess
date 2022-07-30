

class JoinRoomResponse {

    status: number = 0;

    protocolId(): number {
        return 216;
    }

    static write(buffer: any, packet: JoinRoomResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.status);
    }

    static read(buffer: any): JoinRoomResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new JoinRoomResponse();
        const result0 = buffer.readInt();
        packet.status = result0;
        return packet;
    }
}

export default JoinRoomResponse;
