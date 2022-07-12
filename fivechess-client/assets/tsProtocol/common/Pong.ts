

class Pong {

    time: number = 0;

    protocolId(): number {
        return 104;
    }

    static write(buffer: any, packet: Pong | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeLong(packet.time);
    }

    static read(buffer: any): Pong | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new Pong();
        const result0 = buffer.readLong();
        packet.time = result0;
        return packet;
    }
}

export default Pong;
