

class PairString {

    key: string = '';
    value: string = '';

    protocolId(): number {
        return 112;
    }

    static write(buffer: any, packet: PairString | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeString(packet.key);
        buffer.writeString(packet.value);
    }

    static read(buffer: any): PairString | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new PairString();
        const result0 = buffer.readString();
        packet.key = result0;
        const result1 = buffer.readString();
        packet.value = result1;
        return packet;
    }
}

export default PairString;
