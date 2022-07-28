

class MatchResponse {

    status: boolean = false;

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

        buffer.writeBoolean(packet.status);
    }

    static read(buffer: any): MatchResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new MatchResponse();
        const result0 = buffer.readBoolean(); 
        packet.status = result0;
        return packet;
    }
}

export default MatchResponse;
