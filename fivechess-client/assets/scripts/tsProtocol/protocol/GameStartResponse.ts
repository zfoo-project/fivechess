

class GameStartResponse {

    

    protocolId(): number {
        return 313;
    }

    static write(buffer: any, packet: GameStartResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        
    }

    static read(buffer: any): GameStartResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new GameStartResponse();
        
        return packet;
    }
}

export default GameStartResponse;
