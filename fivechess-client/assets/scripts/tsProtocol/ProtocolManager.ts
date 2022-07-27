import Message from './common/Message';
import Error from './common/Error';
import Heartbeat from './common/Heartbeat';
import Ping from './common/Ping';
import Pong from './common/Pong';
import PairLong from './common/PairLong';
import PairString from './common/PairString';
import PairLS from './common/PairLS';
import TripleLong from './common/TripleLong';
import TripleString from './common/TripleString';
import TripleLSS from './common/TripleLSS';
import GameInfoVo from './protocol/common/GameInfoVo';
import ErrorResponse from './protocol/common/ErrorResponse';
import LoginRequest from './protocol/LoginRequest';
import LoginResponse from './protocol/LoginResponse';
import MatchRequest from './protocol/MatchRequest';
import MatchResponse from './protocol/MatchResponse';

const protocols = new Map<number, any>();

// initProtocol
protocols.set(100, Message);
protocols.set(101, Error);
protocols.set(102, Heartbeat);
protocols.set(103, Ping);
protocols.set(104, Pong);
protocols.set(111, PairLong);
protocols.set(112, PairString);
protocols.set(113, PairLS);
protocols.set(114, TripleLong);
protocols.set(115, TripleString);
protocols.set(116, TripleLSS);
protocols.set(120, GameInfoVo);
protocols.set(201, ErrorResponse);
protocols.set(211, LoginRequest);
protocols.set(212, LoginResponse);
protocols.set(311, MatchRequest);
protocols.set(312, MatchResponse);

class ProtocolManager {
    static getProtocol(protocolId: number): any {
        const protocol = protocols.get(protocolId);
        if (protocol === null) {
            throw new Error('[protocolId:' + protocolId + ']协议不存在');
        }
        return protocol;
    }

    static write(buffer: any, packet: any): void {
        const protocolId = packet.protocolId();
        buffer.writeShort(protocolId);
        const protocol = ProtocolManager.getProtocol(protocolId);
        protocol.write(buffer, packet);
    }

    static read(buffer: any): any {
        const protocolId = buffer.readShort();
        const protocol = ProtocolManager.getProtocol(protocolId);
        const packet = protocol.read(buffer);
        return packet;
    }
}

export default ProtocolManager;
