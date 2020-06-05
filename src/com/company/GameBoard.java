package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JPanel {

    ArrayList<int[]> obstacles = new ArrayList<int[]>();
    int minObstacleLength = 3;
    int maxObstacleLength = Constants.SECTOR_GRID_AMOUNT_V - minObstacleLength;
    int obstacleAmount = 10;

    public GameBoard(ArrayList<int[]> snakeBody)
    {
        System.out.println("H:"+Constants.GRID_AMOUNT_H+" V:"+Constants.GRID_AMOUNT_V);
        for(int i = 0; i < Constants.SECTOR_AMOUNT_H; i+=2)
        {
            for(int j = 0; j < Constants.SECTOR_AMOUNT_V; j++)
            {
                int [] sector = {i, j};
                obstacles.add(initObstacle(snakeBody, sector));
            }
        }

    }

    public void drawGrid(Graphics g){
        g.setColor(Color.black);

        for (int i = 0; i <= Constants.GRID_AMOUNT_V; i++)
        {
            g.drawLine(Constants.BIG_BORDER_X + i*Constants.GRID_SIZE,
                    Constants.BIG_BORDER_Y,
                    Constants.BIG_BORDER_X + i*Constants.GRID_SIZE,
                    Constants.BIG_BORDER_Y + Constants.BIG_BORDER_HEIGHT);
        }
        for (int i = 0; i <= Constants.GRID_AMOUNT_H; i++)
            g.drawLine(Constants.BIG_BORDER_X,
                    Constants.BIG_BORDER_Y  + i*Constants.GRID_SIZE,
                    Constants.BIG_BORDER_X + Constants.BIG_BORDER_WIDTH,
                    Constants.BIG_BORDER_Y + i*Constants.GRID_SIZE);
    }

    public void drawGridSectors(Graphics g){
        g.setColor(Color.WHITE);
        for (int i = 0; i < Constants.SECTOR_AMOUNT_V; i++)
        {
            g.drawLine(Constants.BIG_BORDER_X + i*Constants.SECTOR_WIDTH,
                        Constants.BIG_BORDER_Y,
                    Constants.BIG_BORDER_X + i*Constants.SECTOR_WIDTH,
                    Constants.BIG_BORDER_Y + Constants.BIG_BORDER_HEIGHT);
        }
        for (int i = 0; i < Constants.SECTOR_AMOUNT_H; i++)
            g.drawLine(Constants.BIG_BORDER_X,
                    Constants.BIG_BORDER_Y  + i*Constants.SECTOR_HEIGHT,
                    Constants.BIG_BORDER_X + Constants.BIG_BORDER_WIDTH,
                    Constants.BIG_BORDER_Y + i*Constants.SECTOR_HEIGHT);
    }

    public void drawBorders(Graphics g)
    {
        g.setColor(Color.white);
        g.drawRect(Constants.SMALL_BORDER_X,
                Constants.SMALL_BORDER_Y,
                Constants.SMALL_BORDER_WIDTH,
                Constants.SMALL_BORDER_HEIGHT);

        g.drawRect(Constants.BIG_BORDER_X,
                Constants.BIG_BORDER_Y,
                Constants.BIG_BORDER_WIDTH,
                Constants.BIG_BORDER_HEIGHT);
    }

    public void drawObstacles(Graphics g)
    {
        g.setColor(Color.DARK_GRAY);
        for(int [] obstacle: obstacles)
            g.fillRect(obstacle[0], obstacle[1], obstacle[2], obstacle[3]);
    }

    public void drawPoints(Graphics g, int points)
    {

        g.setColor(Color.white);
        g.setFont(new Font("Impact", Font.PLAIN, 28));
        g.drawString("POINTS",
                Constants.SMALL_BORDER_X + Constants.GRID_SIZE,
                (int)(Constants.SMALL_BORDER_Y+(Constants.SMALL_BORDER_HEIGHT/2.5)));
        g.drawString(String.format("%06d", points),
                Constants.SMALL_BORDER_X + Constants.GRID_SIZE,
                (int)(Constants.SMALL_BORDER_Y+(Constants.SMALL_BORDER_HEIGHT/1.25)));
    }

    private int [] generateRandPos(int [] sector, int [] orientation, int obstacleLength)
    {
        int [] randPos = new int[2];
        Random rand = new Random();

        int stepX =  Constants.SECTOR_GRID_AMOUNT_V - orientation[0] * obstacleLength;
        int minX =  Constants.BIG_BORDER_X + sector[0] * Constants.SECTOR_WIDTH;
        int randX = minX + rand.nextInt(stepX)*Constants.GRID_SIZE;
        randPos[0] = randX;

        int stepY =  Constants.SECTOR_GRID_AMOUNT_H -  orientation[1] * obstacleLength;
        int minY =  Constants.BIG_BORDER_Y + sector[1] * Constants.SECTOR_HEIGHT;
        int randY = minY + rand.nextInt(stepY)*Constants.GRID_SIZE;
        randPos[1] = randY;

        return randPos;
    }

    private int [] generateRandOrient()
    {
        Random rand = new Random();
        int [] randOrientation = new int[2];

        randOrientation[0] = rand.nextInt(2);
        randOrientation[1] = randOrientation[0] ^ 1;

        return randOrientation;
    }

    private int [] initObstacle(ArrayList<int[]> snakeBody, int [] sector)
    {
        while(true)
        {
            Random rand = new Random();
            int length = minObstacleLength + rand.nextInt(maxObstacleLength);
            int [] orientation = generateRandOrient();
            int [] position = generateRandPos(sector, orientation, length);
            int width = Constants.GRID_SIZE * (orientation[0] * (length-1) + 1);
            int height = Constants.GRID_SIZE * (orientation[1] * (length-1) + 1);
            int [] obstacleParams = {position[0], position[1], width, height};

            boolean noCollision = true;
            for (int [] partPos: snakeBody)
            {
                if ( (position[0] <= (partPos[0] + Constants.GRID_SIZE)
                        && (partPos[0] + Constants.GRID_SIZE) <= position[0] + width)
                    && (position[1] <= (partPos[1] + Constants.GRID_SIZE)
                        && (partPos[1] + Constants.GRID_SIZE) <= position[1] + height)) {
                    noCollision = false;
                    break;
                }
            }

            if (!noCollision)
                continue;

            for (int [] obstacle: obstacles)
            {
                if ( (position[0] <= (obstacle[0] + obstacle[2]/2)
                        && (obstacle[0] + obstacle[2]/2) <= position[0] + width)
                        && (position[1] <= (obstacle[1] + obstacle[3]/2)
                        && (obstacle[1] + obstacle[3]/2) <= position[1] + height)) {
                    noCollision = false;
                    break;
                }
            }

            if (noCollision)
                return obstacleParams;
        }
    }

    public ArrayList<int[]> getObstacles() {
        return obstacles;
    }
}