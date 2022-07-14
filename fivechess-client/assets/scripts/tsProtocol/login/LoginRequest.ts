

class LoginRequest {

    account: string = '';
    password: string = '';

    protocolId(): number {
        return 202;
    }

    static write(buffer: any, packet: LoginRequest | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeString(packet.account);
        buffer.writeString(packet.password);
    }

    static read(buffer: any): LoginRequest | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new LoginRequest();
        const result0 = buffer.readString();
        packet.account = result0;
        const result1 = buffer.readString();
        packet.password = result1;
        return packet;
    }
}

export default LoginRequest;
