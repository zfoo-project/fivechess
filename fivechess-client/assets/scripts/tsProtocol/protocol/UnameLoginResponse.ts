

class UnameLoginResponse {

    inRoom: boolean = false;
    status: number = 0;

    protocolId(): number {
        return 212;
    }

    static write(buffer: any, packet: UnameLoginResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeBoolean(packet.inRoom);
        buffer.writeInt(packet.status);
    }

    static read(buffer: any): UnameLoginResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new UnameLoginResponse();
        const result0 = buffer.readBoolean(); 
        packet.inRoom = result0;
        const result1 = buffer.readInt();
        packet.status = result1;
        return packet;
    }
}

export default UnameLoginResponse;
