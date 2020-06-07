package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Fruit extends JFrame {

    int [] position;
    int size = Constants.GRID_SIZE;

    public Fruit(ArrayList<int[]> snakeBody,ArrayList<int[]> obstacles)
    {
        boolean possibleCollision = true;
        while(possibleCollision)
        {

            position = generateRandPos();
            boolean noCollision = true;
            for (int [] part: snakeBody)
            {
                if (part[0] == position[0] && part[1] == position[1]) {
                    noCollision = false;
                    break;
                }
            }
            for (int [] obstacle: obstacles)
            {
                if ( (obstacle[0] <= position[0]
                        && position[0] <= obstacle[0] + obstacle[2])
                    && (obstacle[1] <= position[1]
                        && position[1] <= obstacle[1] + obstacle[3])) {
                    noCollision = false;
                    break;
                }
            }
            if (noCollision)
                possibleCollision = false;
        }
    }

    private int [] generateRandPos()
    {
        int [] randPos = new int[2];
        Random rand = new Random();

        int stepX =  Constants.GRID_AMOUNT_V - 2;
        int minX =  Constants.BIG_BORDER_X + Constants.GRID_SIZE;
        int randX = minX + rand.nextInt(stepX)*Constants.GRID_SIZE;
        randPos[0] = randX;

        int stepY =  Constants.GRID_AMOUNT_H - 2;
        int minY =  Constants.BIG_BORDER_Y + Constants.GRID_SIZE;
        int randY = minY + rand.nextInt(stepY)*Constants.GRID_SIZE;
        randPos[1] = randY;

        return randPos;
    }

    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(position[0], position[1], size, size);
    }

    public int[] getPosition() { return position; }
}
