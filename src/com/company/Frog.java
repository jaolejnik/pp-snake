package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frog extends Fruit {

    Direction [] direction = new Direction[2];
    int sizeOffset = 4;
    int size = Constants.GRID_SIZE + sizeOffset;
    int [] step = new int[2];

    public Frog(ArrayList<int[]> snakeBody, ArrayList<int[]> obstacles)
    {
        super(snakeBody, obstacles);
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(position[0] - sizeOffset/2, position[1] - sizeOffset/2, size, size);
    }

    private void setStepValue()
    {
        direction[0] = Direction.generateRandLeftRight();
        direction[1] = Direction.generateRandUpDown();

        switch (direction[0])
        {
            case LEFT:
                step[0] = -Constants.GRID_SIZE*2;
                break;

            case RIGHT:
                step[0] = Constants.GRID_SIZE*2;
                break;
        }

        switch (direction[1])
        {
            case UP:
                step[1] = -Constants.GRID_SIZE*2;
                break;

            case DOWN:
                step[1] = Constants.GRID_SIZE*2;
                break;
        }
    }

    private void handleOutOfBounds()
    {
        int maxX = Constants.BIG_BORDER_X + Constants.BIG_BORDER_WIDTH;
        int maxY = Constants.BIG_BORDER_Y + Constants.BIG_BORDER_HEIGHT;

        if (position[0] > maxX - size/2)
            position[0] = Constants.BIG_BORDER_X;
        if (position[0] < Constants.BIG_BORDER_X)
            position[0] = maxX;

        if (position[1] > maxY - size/2)
            position[1] = Constants.BIG_BORDER_Y;
        if (position[1] < Constants.BIG_BORDER_Y)
            position[1] = maxY;
    }

    public void move(long elapsedTime)
    {
        setStepValue();
//        System.out.println(elapsedTime);
        if (elapsedTime % 15 == 0)
            position[0] += step[0];
        if (elapsedTime % 25 == 0)
            position[1] += step[1];
        handleOutOfBounds();
    }

    public int getSizeOffset() { return sizeOffset;}
    public int getFrogSize() { return size;}
}
