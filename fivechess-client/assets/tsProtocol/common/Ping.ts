

class Ping {

    

    protocolId(): number {
        return 103;
    }

    static write(buffer: any, packet: Ping | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        
    }

    static read(buffer: any): Ping | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new Ping();
        
        return packet;
    }
}

export default Ping;
