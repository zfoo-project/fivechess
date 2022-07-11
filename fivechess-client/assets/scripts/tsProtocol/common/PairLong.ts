

class PairLong {

    key: number = 0;
    value: number = 0;

    protocolId(): number {
        return 111;
    }

    static write(buffer: any, packet: PairLong | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeLong(packet.key);
        buffer.writeLong(packet.value);
    }

    static read(buffer: any): PairLong | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new PairLong();
        const result0 = buffer.readLong();
        packet.key = result0;
        const result1 = buffer.readLong();
        packet.value = result1;
        return packet;
    }
}

export default PairLong;
