package com.zfoo.fivechess.logic;

public class Constant {
    /**
     * 棋盘相关
     */
    public static final int eDesk_PlayerNum = 2;
    public static final int eDisk_Size = 15;

    /**
     * 棋子颜色
     */
    public static final int eChessColor_Black = 1;
    public static final int eChessColor_White = 2;

    int eResponse_Ok = 1;

    /**
     * 游戏状态
     */
    public static final int eTableState_Ready = 0;
    public static final int eTableState_Start = 1;
    public static final int eTableState_Playing = 2;
    public static final int eTableState_Checkout = 3;
}
