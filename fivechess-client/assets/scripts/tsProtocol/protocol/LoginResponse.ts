import RoleInfoVo from './common/RoleInfoVo';


class LoginResponse {

    account: string = '';
    roleInfoVo: RoleInfoVo | null = null;
    uid: number = 0;

    protocolId(): number {
        return 212;
    }

    static write(buffer: any, packet: LoginResponse | null) {
        if (buffer.writePacketFlag(packet)) {
            return;
        }
        if (packet === null) {
            return;
        }

        buffer.writeString(packet.account);
        buffer.writePacket(packet.roleInfoVo, 120);
        buffer.writeLong(packet.uid);
    }

    static read(buffer: any): LoginResponse | null {
        if (!buffer.readBoolean()) {
            return null;
        }
        const packet = new LoginResponse();
        const result0 = buffer.readString();
        packet.account = result0;
        const result1 = buffer.readPacket(120);
        packet.roleInfoVo = result1;
        const result2 = buffer.readLong();
        packet.uid = result2;
        return packet;
    }
}

export default LoginResponse;
