package com.zfoo.fivechess.logic;

import com.zfoo.fivechess.protocol.CheckoutResponse;
import com.zfoo.fivechess.protocol.GameStartedResponse;
import com.zfoo.fivechess.protocol.TurnToPlayerResponse;
import com.zfoo.fivechess.protocol.UserArrivedResponse;
import com.zfoo.protocol.IPacket;
import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

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

    public int getSeatIdByUid(long uid) {
        for (int i = 0; i < Constant.eDesk_PlayerNum; i++) {
            if (this.players[i] != null && this.players[i].getUid() == uid) {
                return i;
            }
        }

        return -1;
    }

    public int getEmptySeatId() {
        for (int i = 0; i < Constant.eDesk_PlayerNum; i++) {
            if (this.players[i] == null) {
                return i;
            }
        }

        return -1;
    }

    public boolean checkGameStarted() {
        for (int i = 0; i < Constant.eDesk_PlayerNum; i++) {
            if (this.players[i] == null) {
                return false;
            }
        }

        return true;
    }

    public void startGame() {
        this.status = Constant.eTableState_Start;
        this.button_id = Math.abs(ThreadLocalRandom.current().nextInt()) % 2;
        this.initChessDiskData();

        GameStartedResponse response = new GameStartedResponse();
        response.setButtonId(this.button_id);
        this.broadcastMsgInTable(response, -1);

        this.status = Constant.eTableState_Playing;
        this.turnToPlayer(this.button_id);
    }

    public void turnToPlayer(int seatId) {
        this.cur_turn = seatId;

        TurnToPlayerResponse response = new TurnToPlayerResponse();
        response.setSeatId(seatId);

        this.broadcastMsgInTable(response, -1);
    }

    public void sendCheckout(int winner) {
        this.status = Constant.eTableState_Checkout;

        CheckoutResponse response = new CheckoutResponse();
        response.setWinner(winner);
        this.broadcastMsgInTable(response, -1);

        for (int i = 0; i < this.players.length; i++) {
            PlayerManager.removePlayerByUid(players[i].getUid());
            this.players[i] = null;
        }

        this.status = Constant.eTableState_Ready;

        TableManager.removeTable(tableId);
    }

    public int checkGameOver(int seatid) {
        int value = (this.button_id == seatid) ? Constant.eChessColor_Black : Constant.eChessColor_White;

        // 横向扫描，看是否有五个棋子连接起来；
        for (int line = 0; line < Constant.eDisk_Size; line++) { // 遍历行
            for (int col = 0; col < Constant.eDisk_Size - 4; col++) { // 遍历列
                if (this.chess_disk[line][col + 0] == value &&
                        this.chess_disk[line][col + 1] == value &&
                        this.chess_disk[line][col + 2] == value &&
                        this.chess_disk[line][col + 3] == value &&
                        this.chess_disk[line][col + 4] == value) {
                    return 1;
                }
            }
        }

        // 竖向扫描
        for (int col = 0; col < Constant.eDisk_Size; col++) {
            for (int line = 0; line < Constant.eDisk_Size - 4; line++) {
                if (this.chess_disk[line + 0][col] == value &&
                        this.chess_disk[line + 1][col] == value &&
                        this.chess_disk[line + 2][col] == value &&
                        this.chess_disk[line + 3][col] == value &&
                        this.chess_disk[line + 4][col] == value) {
                    return 1;
                }
            }
        }

        // 反斜杠一半的方向开始
        // 下半部
        for (int y = 0; y < Constant.eDisk_Size; y++) {
            int start_x = 0;
            int start_y = y;
            while (true) {
                if (start_y < 4 || start_x + 4 >= Constant.eDisk_Size) { // 开始的对角线不足五个
                    break;
                }

                if (this.chess_disk[start_y - 0][start_x + 0] == value &&
                        this.chess_disk[start_y - 1][start_x + 1] == value &&
                        this.chess_disk[start_y - 2][start_x + 2] == value &&
                        this.chess_disk[start_y - 3][start_x + 3] == value &&
                        this.chess_disk[start_y - 4][start_x + 4] == value) {
                    return 1;
                }
                // 对角线的下一个起点
                start_y--;
                start_x++;
            }
        }

        // 上半部
        for (int x = 1; x < Constant.eDisk_Size - 4; x++) {
            int start_x = x;
            int start_y = Constant.eDisk_Size - 1;

            while (true) {
                if (start_y < 4 || start_x + 4 >= Constant.eDisk_Size) { // 开始的对角线不足五个
                    break;
                }
                if (this.chess_disk[start_y - 0][start_x + 0] == value &&
                        this.chess_disk[start_y - 1][start_x + 1] == value &&
                        this.chess_disk[start_y - 2][start_x + 2] == value &&
                        this.chess_disk[start_y - 3][start_x + 3] == value &&
                        this.chess_disk[start_y - 4][start_x + 4] == value) {
                    return 1;
                }
                // 对角线的下一个起点
                start_y--;
                start_x++;
            }
        }

        // 斜杠部分
        // 右半部分
        for (int y = 0; y < Constant.eDisk_Size; y++) {
            int start_x = Constant.eDisk_Size - 1;
            int start_y = y;
            while (true) {
                if (start_y < 4 || start_x < 4) { // 开始的对角线不足五个
                    break;
                }

                if (this.chess_disk[start_y - 0][start_x - 0] == value &&
                        this.chess_disk[start_y - 1][start_x - 1] == value &&
                        this.chess_disk[start_y - 2][start_x - 2] == value &&
                        this.chess_disk[start_y - 3][start_x - 3] == value &&
                        this.chess_disk[start_y - 4][start_x - 4] == value) {
                    return 1;
                }
                // 对角线的下一个起点
                start_y--;
                start_x--;

            }
        }

        // 左半部分
        for (int x = Constant.eDisk_Size - 1; x >= 0; x--) {
            int start_x = x;
            int start_y = Constant.eDisk_Size - 1;

            while (true) {
                if (start_y < 4 || start_x < 4) { // 开始的对角线不足五个
                    break;
                }

                if (this.chess_disk[start_y - 0][start_x - 0] == value &&
                        this.chess_disk[start_y - 1][start_x - 1] == value &&
                        this.chess_disk[start_y - 2][start_x - 2] == value &&
                        this.chess_disk[start_y - 3][start_x - 3] == value &&
                        this.chess_disk[start_y - 4][start_x - 4] == value) {
                    return 1;
                }
                // 对角线的下一个起点
                start_y--;
                start_x--;
            }
        }

        return 0;
    }
}
























