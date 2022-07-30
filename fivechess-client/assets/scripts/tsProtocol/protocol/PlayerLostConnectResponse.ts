

class PlayerLostConnectResponse {

    seatId: number = 0;

    protocolId(): number {
        return 225;
    }

    static write(buffer: any, packet: PlayerLostConnectResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.seatId);
    }

    static read(buffer: any): PlayerLostConnectResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new PlayerLostConnectResponse();
        const result0 = buffer.readInt();
        packet.seatId = result0;
        return packet;
    }
}

export default PlayerLostConnectResponse;
