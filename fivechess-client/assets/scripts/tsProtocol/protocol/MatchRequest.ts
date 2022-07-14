

class MatchRequest {

    

    protocolId(): number {
        return 311;
    }

    static write(buffer: any, packet: MatchRequest | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        
    }

    static read(buffer: any): MatchRequest | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new MatchRequest();
        
        return packet;
    }
}

export default MatchRequest;
