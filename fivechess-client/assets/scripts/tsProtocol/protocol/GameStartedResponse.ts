

class GameStartedResponse {

    buttonId: number = 0;

    protocolId(): number {
        return 218;
    }

    static write(buffer: any, packet: GameStartedResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.buttonId);
    }

    static read(buffer: any): GameStartedResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new GameStartedResponse();
        const result0 = buffer.readInt();
        packet.buttonId = result0;
        return packet;
    }
}

export default GameStartedResponse;
