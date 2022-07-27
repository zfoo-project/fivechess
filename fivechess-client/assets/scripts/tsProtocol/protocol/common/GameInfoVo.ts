

class GameInfoVo {

    gold: number = 0;

    protocolId(): number {
        return 120;
    }

    static write(buffer: any, packet: GameInfoVo | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.gold);
    }

    static read(buffer: any): GameInfoVo | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new GameInfoVo();
        const result0 = buffer.readInt();
        packet.gold = result0;
        return packet;
    }
}

export default GameInfoVo;
