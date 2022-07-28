package com.zfoo.fivechess.logic;

import lombok.Data;

@Data
public class Room {
    private int roomId;
    private int status;
    private int button_id;
    private int cur_turn;
    private Player[] players;
    private int[][] chess_disk;

    public Room() {
        this.roomId = -1;
        this.status = RoomConst.READY;
        this.button_id = -1;
        this.cur_turn = -1;

        this.players = new Player[RoomConst.PLAYER_NUM];
    }

    public void bindSeatIdWithPlayer(int seatId, Player player) {
        player.setSeatId(seatId);
        player.setRoomId(roomId);
        this.players[seatId] = player;
    }
}
