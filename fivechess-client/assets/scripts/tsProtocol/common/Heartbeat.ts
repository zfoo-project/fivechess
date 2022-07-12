

class Heartbeat {

    

    protocolId(): number {
        return 102;
    }

    static write(buffer: any, packet: Heartbeat | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        
    }

    static read(buffer: any): Heartbeat | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new Heartbeat();
        
        return packet;
    }
}

export default Heartbeat;
