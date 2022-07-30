package com.zfoo.fivechess.logic;

import com.zfoo.fivechess.protocol.UserArrivedResponse;
import com.zfoo.protocol.IPacket;
import lombok.Data;

@Data
public class Table {
    private int tableId;
    private int status;
    private int button_id;
    private int cur_turn;
    private Player[] players;
    private int[][] chess_disk;

    public Table() {
        this.tableId = -1;
        this.status = Constant.eTableState_Ready;
        this.button_id = -1;
        this.cur_turn = -1;

        this.initSeatData();
        this.initChessDiskData();
    }

    public void bindSeatIdWithPlayer(int seatId, Player player) {
        player.setSeatId(seatId);
        player.setTableId(tableId);
        this.players[seatId] = player;
    }

    private void initSeatData() {
        this.players = new Player[Constant.eDesk_PlayerNum];
    }

    private void initChessDiskData() {
        this.chess_disk = new int[Constant.eDisk_Size][Constant.eDisk_Size];

        for (int i = 0; i < this.chess_disk.length; i++) {
            for (int j = 0; j < this.chess_disk[i].length; j++) {
                this.chess_disk[i][j] = 0;
            }
        }
    }

    public void otherUsersArrivedToMyself(Player player, int seatId) {
        for (int i = 0; i < Constant.eDesk_PlayerNum; i++) {
            if (i != seatId && this.players[i] != null) {
                UserArrivedResponse response = new UserArrivedResponse();
                response.setSeatId(seatId);

                player.sendMsg(response);
            }
        }
    }

    public void broadcastMsgInTable(IPacket packet, int not_to_seatid) {
        for (int i = 0; i < Constant.eDesk_PlayerNum; i++) {
            if (this.players[i] != null && i != not_to_seatid) {
                this.players[i].sendMsg(packet);
            }
        }
    }

    public boolean isInTable(Player player) {
        for (int i = 0; i < Constant.eDesk_PlayerNum; i++) {
            if (this.players[i] != null && this.players[i] == player) {
                return true;
            }
        }
        return false;
    }
}
























