

class Error {

    errorCode: number = 0;
    errorMessage: string = '';
    module: number = 0;

    protocolId(): number {
        return 101;
    }

    static write(buffer: any, packet: Error | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.errorCode);
        buffer.writeString(packet.errorMessage);
        buffer.writeInt(packet.module);
    }

    static read(buffer: any): Error | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new Error();
        const result0 = buffer.readInt();
        packet.errorCode = result0;
        const result1 = buffer.readString();
        packet.errorMessage = result1;
        const result2 = buffer.readInt();
        packet.module = result2;
        return packet;
    }
}

export default Error;
