

class TripleLSS {

    left: number = 0;
    middle: string = '';
    right: string = '';

    protocolId(): number {
        return 116;
    }

    static write(buffer: any, packet: TripleLSS | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeLong(packet.left);
        buffer.writeString(packet.middle);
        buffer.writeString(packet.right);
    }

    static read(buffer: any): TripleLSS | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new TripleLSS();
        const result0 = buffer.readLong();
        packet.left = result0;
        const result1 = buffer.readString();
        packet.middle = result1;
        const result2 = buffer.readString();
        packet.right = result2;
        return packet;
    }
}

export default TripleLSS;
