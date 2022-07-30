import ChessItem from './common/ChessItem';


class ReconnectInfoResponse {

    buttonId: number = 0;
    chessItems: Array<ChessItem> | null = null;
    seatId: number = 0;

    protocolId(): number {
        return 224;
    }

    static write(buffer: any, packet: ReconnectInfoResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.buttonId);
        buffer.writePacketList(packet.chessItems, 203);
        buffer.writeInt(packet.seatId);
    }

    static read(buffer: any): ReconnectInfoResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new ReconnectInfoResponse();
        const result0 = buffer.readInt();
        packet.buttonId = result0;
        const list1 = buffer.readPacketList(203);
        packet.chessItems = list1;
        const result2 = buffer.readInt();
        packet.seatId = result2;
        return packet;
    }
}

export default ReconnectInfoResponse;
