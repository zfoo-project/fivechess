

class UserQuitResponse {

    reason: number = 0;
    seatId: number = 0;

    protocolId(): number {
        return 223;
    }

    static write(buffer: any, packet: UserQuitResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.reason);
        buffer.writeInt(packet.seatId);
    }

    static read(buffer: any): UserQuitResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new UserQuitResponse();
        const result0 = buffer.readInt();
        packet.reason = result0;
        const result1 = buffer.readInt();
        packet.seatId = result1;
        return packet;
    }
}

export default UserQuitResponse;
