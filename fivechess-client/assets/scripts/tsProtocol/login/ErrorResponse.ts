

class ErrorResponse {

    errorCode: number = 0;
    message: string = '';

    protocolId(): number {
        return 201;
    }

    static write(buffer: any, packet: ErrorResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.errorCode);
        buffer.writeString(packet.message);
    }

    static read(buffer: any): ErrorResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new ErrorResponse();
        const result0 = buffer.readInt();
        packet.errorCode = result0;
        const result1 = buffer.readString();
        packet.message = result1;
        return packet;
    }
}

export default ErrorResponse;
