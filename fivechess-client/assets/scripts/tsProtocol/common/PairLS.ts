

class PairLS {

    key: number = 0;
    value: string = '';

    protocolId(): number {
        return 113;
    }

    static write(buffer: any, packet: PairLS | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeLong(packet.key);
        buffer.writeString(packet.value);
    }

    static read(buffer: any): PairLS | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new PairLS();
        const result0 = buffer.readLong();
        packet.key = result0;
        const result1 = buffer.readString();
        packet.value = result1;
        return packet;
    }
}

export default PairLS;
