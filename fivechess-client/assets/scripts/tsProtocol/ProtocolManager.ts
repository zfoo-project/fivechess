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
import ErrorResponse from './protocol/common/ErrorResponse';
import ChessItem from './protocol/common/ChessItem';
import UnameLoginRequest from './protocol/UnameLoginRequest';
import UnameLoginResponse from './protocol/UnameLoginResponse';
import CreateRoomRequest from './protocol/CreateRoomRequest';
import CreateRoomResponse from './protocol/CreateRoomResponse';
import JoinRoomRequest from './protocol/JoinRoomRequest';
import JoinRoomResponse from './protocol/JoinRoomResponse';
import UserArrivedResponse from './protocol/UserArrivedResponse';
import GameStartedResponse from './protocol/GameStartedResponse';
import GiveChessRequest from './protocol/GiveChessRequest';
import GiveChessResponse from './protocol/GiveChessResponse';
import TurnToPlayerResponse from './protocol/TurnToPlayerResponse';
import CheckoutResponse from './protocol/CheckoutResponse';
import UserQuitResponse from './protocol/UserQuitResponse';
import ReconnectInfoResponse from './protocol/ReconnectInfoResponse';
import PlayerLostConnectResponse from './protocol/PlayerLostConnectResponse';
import MySeatInfoResponse from './protocol/MySeatInfoResponse';

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
protocols.set(201, ErrorResponse);
protocols.set(203, ChessItem);
protocols.set(211, UnameLoginRequest);
protocols.set(212, UnameLoginResponse);
protocols.set(213, CreateRoomRequest);
protocols.set(214, CreateRoomResponse);
protocols.set(215, JoinRoomRequest);
protocols.set(216, JoinRoomResponse);
protocols.set(217, UserArrivedResponse);
protocols.set(218, GameStartedResponse);
protocols.set(219, GiveChessRequest);
protocols.set(220, GiveChessResponse);
protocols.set(221, TurnToPlayerResponse);
protocols.set(222, CheckoutResponse);
protocols.set(223, UserQuitResponse);
protocols.set(224, ReconnectInfoResponse);
protocols.set(225, PlayerLostConnectResponse);
protocols.set(226, MySeatInfoResponse);

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
