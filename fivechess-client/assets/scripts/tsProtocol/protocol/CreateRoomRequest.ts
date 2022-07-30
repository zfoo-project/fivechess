

class CreateRoomRequest {

    

    protocolId(): number {
        return 213;
    }

    static write(buffer: any, packet: CreateRoomRequest | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        
    }

    static read(buffer: any): CreateRoomRequest | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new CreateRoomRequest();
        
        return packet;
    }
}

export default CreateRoomRequest;
