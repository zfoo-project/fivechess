

class CheckoutResponse {

    winner: number = 0;

    protocolId(): number {
        return 222;
    }

    static write(buffer: any, packet: CheckoutResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.winner);
    }

    static read(buffer: any): CheckoutResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new CheckoutResponse();
        const result0 = buffer.readInt();
        packet.winner = result0;
        return packet;
    }
}

export default CheckoutResponse;
