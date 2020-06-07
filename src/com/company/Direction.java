package com.company;

import java.util.Random;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Direction generateRandDirection() {
        Random rand = new Random();
        return values()[rand.nextInt(Direction.values().length)];
    }

    public static Direction generateRandLeftRight()
    {
        Random rand = new Random();
        return values()[rand.nextInt(2)];
    }

    public static Direction generateRandUpDown()
    {
        Random rand = new Random();
        return values()[2 + rand.nextInt(2)];
    }
}
