

class MatchResponse {

    

    protocolId(): number {
        return 312;
    }

    static write(buffer: any, packet: MatchResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        
    }

    static read(buffer: any): MatchResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new MatchResponse();
        
        return packet;
    }
}

export default MatchResponse;
