

class CreateRoomResponse {

    status: number = 0;

    protocolId(): number {
        return 214;
    }

    static write(buffer: any, packet: CreateRoomResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.status);
    }

    static read(buffer: any): CreateRoomResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new CreateRoomResponse();
        const result0 = buffer.readInt();
        packet.status = result0;
        return packet;
    }
}

export default CreateRoomResponse;
