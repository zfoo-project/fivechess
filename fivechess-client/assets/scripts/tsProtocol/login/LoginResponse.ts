

class LoginResponse {

    uid: number = 0;
    unick: string = '';

    protocolId(): number {
        return 202;
    }

    static write(buffer: any, packet: LoginResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeLong(packet.uid);
        buffer.writeString(packet.unick);
    }

    static read(buffer: any): LoginResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new LoginResponse();
        const result0 = buffer.readLong();
        packet.uid = result0;
        const result1 = buffer.readString();
        packet.unick = result1;
        return packet;
    }
}

export default LoginResponse;
