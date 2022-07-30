

class UserArrivedResponse {

    seatId: number = 0;

    protocolId(): number {
        return 217;
    }

    static write(buffer: any, packet: UserArrivedResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.seatId);
    }

    static read(buffer: any): UserArrivedResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new UserArrivedResponse();
        const result0 = buffer.readInt();
        packet.seatId = result0;
        return packet;
    }
}

export default UserArrivedResponse;
