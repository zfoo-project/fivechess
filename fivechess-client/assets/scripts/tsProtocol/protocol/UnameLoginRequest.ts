

class UnameLoginRequest {

    uname: string = '';
    upwd: string = '';

    protocolId(): number {
        return 211;
    }

    static write(buffer: any, packet: UnameLoginRequest | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeString(packet.uname);
        buffer.writeString(packet.upwd);
    }

    static read(buffer: any): UnameLoginRequest | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new UnameLoginRequest();
        const result0 = buffer.readString();
        packet.uname = result0;
        const result1 = buffer.readString();
        packet.upwd = result1;
        return packet;
    }
}

export default UnameLoginRequest;
