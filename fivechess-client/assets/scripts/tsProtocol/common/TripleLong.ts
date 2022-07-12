

class TripleLong {

    left: number = 0;
    middle: number = 0;
    right: number = 0;

    protocolId(): number {
        return 114;
    }

    static write(buffer: any, packet: TripleLong | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeLong(packet.left);
        buffer.writeLong(packet.middle);
        buffer.writeLong(packet.right);
    }

    static read(buffer: any): TripleLong | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new TripleLong();
        const result0 = buffer.readLong();
        packet.left = result0;
        const result1 = buffer.readLong();
        packet.middle = result1;
        const result2 = buffer.readLong();
        packet.right = result2;
        return packet;
    }
}

export default TripleLong;
