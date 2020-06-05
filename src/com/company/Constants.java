package com.company;

public class Constants {
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 900;

    public static final int SMALL_BORDER_X = 20;
    public static final int SMALL_BORDER_Y = 20;
    public static final int SMALL_BORDER_WIDTH = 860;
    public static final int SMALL_BORDER_HEIGHT = 60;

    public static final int BIG_BORDER_X = 50;
    public static final int BIG_BORDER_Y = 100;
    public static final int BIG_BORDER_WIDTH = 750;
    public static final int BIG_BORDER_HEIGHT = 750;

    public static final int GRID_SIZE = 15;
    public static final int GRID_AMOUNT_V = BIG_BORDER_WIDTH/GRID_SIZE;
    public static final int GRID_AMOUNT_H = BIG_BORDER_HEIGHT/GRID_SIZE;
    public static final int SECTOR_AMOUNT_V = GRID_AMOUNT_V/10;
    public static final int SECTOR_AMOUNT_H = GRID_AMOUNT_H/10;
    public static final int SECTOR_HEIGHT = BIG_BORDER_HEIGHT/SECTOR_AMOUNT_V;
    public static final int SECTOR_WIDTH = BIG_BORDER_WIDTH/SECTOR_AMOUNT_H;
    public static final int SECTOR_GRID_AMOUNT_V = SECTOR_HEIGHT/GRID_SIZE;
    public static final int SECTOR_GRID_AMOUNT_H = SECTOR_WIDTH/GRID_SIZE;
}
