

class TripleString {

    left: string = '';
    middle: string = '';
    right: string = '';

    protocolId(): number {
        return 115;
    }

    static write(buffer: any, packet: TripleString | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeString(packet.left);
        buffer.writeString(packet.middle);
        buffer.writeString(packet.right);
    }

    static read(buffer: any): TripleString | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new TripleString();
        const result0 = buffer.readString();
        packet.left = result0;
        const result1 = buffer.readString();
        packet.middle = result1;
        const result2 = buffer.readString();
        packet.right = result2;
        return packet;
    }
}

export default TripleString;
