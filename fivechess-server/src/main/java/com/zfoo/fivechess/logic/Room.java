package com.zfoo.fivechess.logic;

import lombok.Data;

@Data
public class Room {
    private int roomId;
    private int status;
    private int button_id;
    private Player[] players;
    private int[][] chess_disk;
}
