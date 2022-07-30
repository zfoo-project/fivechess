

class TurnToPlayerResponse {

    seatId: number = 0;

    protocolId(): number {
        return 221;
    }

    static write(buffer: any, packet: TurnToPlayerResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.seatId);
    }

    static read(buffer: any): TurnToPlayerResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new TurnToPlayerResponse();
        const result0 = buffer.readInt();
        packet.seatId = result0;
        return packet;
    }
}

export default TurnToPlayerResponse;
