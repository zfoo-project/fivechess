

class RoleInfoVo {

    gold: number = 0;

    protocolId(): number {
        return 120;
    }

    static write(buffer: any, packet: RoleInfoVo | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeInt(packet.gold);
    }

    static read(buffer: any): RoleInfoVo | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new RoleInfoVo();
        const result0 = buffer.readInt();
        packet.gold = result0;
        return packet;
    }
}

export default RoleInfoVo;
