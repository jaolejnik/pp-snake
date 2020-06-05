package com.company;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

enum Direction {
    LEFT,
    UP,
    RIGHT,
    DOWN;

    public static Direction generateRandDirection() {
        Random rand = new Random();
        return values()[rand.nextInt(Direction.values().length)];
    }
}

public class Snake extends JPanel {

    private class EventAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (direction != Direction.RIGHT))
                direction = Direction.LEFT;

            if ((key == KeyEvent.VK_RIGHT) && (direction != Direction.LEFT))
                direction = Direction.RIGHT;

            if ((key == KeyEvent.VK_UP) && (direction != Direction.DOWN))
                direction = Direction.UP;

            if ((key == KeyEvent.VK_DOWN) && (direction != Direction.UP))
                direction = Direction.DOWN;
        }
    }

    int length = 3;
    int partSize = Constants.GRID_SIZE;
    ArrayList<int[]> body = new ArrayList<int[]>();
    int [] head;
    Direction direction;
    int [] step = new int[2];
    EventAdapter controls = new EventAdapter();

    public Snake()
    {
        direction = Direction.generateRandDirection();
        int[] randPos = generateRandPos();
        initSnake(randPos);

    }

    private int [] generateRandPos()
    {
        int [] randPos = new int[2];
        Random rand = new Random();

        int stepX =  Constants.GRID_AMOUNT_V - 4 * length;
        int minX =  Constants.BIG_BORDER_X + 4 * Constants.GRID_SIZE;
        int randX = minX + rand.nextInt(stepX)*Constants.GRID_SIZE;
        randPos[0] = randX;

        int stepY =  Constants.GRID_AMOUNT_H - 4 * length;
        int minY =  Constants.BIG_BORDER_Y + 4 * Constants.GRID_SIZE;
        int randY = minY + rand.nextInt(stepY)*Constants.GRID_SIZE;
        randPos[1] = randY;

        return randPos;
    }

    private void initSnake(int [] pos)
    {
        switch (direction){
            case LEFT:
                for(int i = 0; i < length; i++)
                {
                    int[] part = new int[] {pos[0] + i*partSize, pos[1]};
                    body.add(part);
                }
                break;

            case UP:
                for(int i = 0; i < length; i++)
                {
                    int[] part = new int[] {pos[0], pos[1] + i*partSize};
                    body.add(part);
                }
                break;

            case RIGHT:
                for(int i = 0; i < length; i++)
                {
                    int[] part = new int[] {pos[0] - i*partSize, pos[1]};
                    body.add(part);
                }
                break;

            case DOWN:
                for(int i = 0; i < length; i++)
                {
                    int[] part = new int[] {pos[0], pos[1] - i*partSize};
                    body.add(part);
                }
                break;
        }

        head = body.get(0);
    }

    private void setStepValue()
    {
        switch (direction)
        {
            case LEFT:
                step[0] = -partSize;
                step[1] = 0;
                break;

            case UP:
                step[0] = 0;
                step[1] = -partSize;
                break;

            case RIGHT:
                step[0] = partSize;
                step[1] = 0;
                break;

            case DOWN:
                step[0] = 0;
                step[1] = partSize;
                break;
        }
    }

    private boolean wallCollision(){
        boolean collisionHorizontal = ( (head[0] <= Constants.BIG_BORDER_X)
                || (head[0] >= (Constants.BIG_BORDER_X + Constants.BIG_BORDER_WIDTH) - partSize) );
        boolean collisionVertical = ( (head[1] <= Constants.BIG_BORDER_Y)
                || (head[1] >= (Constants.BIG_BORDER_Y + Constants.BIG_BORDER_HEIGHT - partSize)) );
        return (collisionHorizontal || collisionVertical);
    }

    private boolean tailCollision(){
        for(int i = 3; i < length; i++)
        {
            switch(direction)
            {
                case LEFT:
                    if (( head[0] == (body.get(i)[0] + partSize)) && ( head[1] == (body.get(i)[1])))
                        return true;
                    break;
                case UP:
                    if (( head[0] == (body.get(i)[0])) && ( head[1] == (body.get(i)[1] + partSize)))
                        return true;
                    break;
                case RIGHT:
                    if (( head[0] == (body.get(i)[0] - partSize)) && ( head[1] == (body.get(i)[1])))
                        return true;
                    break;
                case DOWN:
                    if (( head[0] == (body.get(i)[0])) && ( head[1] == (body.get(i)[1] - partSize)))
                        return true;
                    break;
            }

        }
        return false;
    }

    private boolean obstacleCollision(ArrayList<int[]> obstacles)
    {
        for (int [] obstacle: obstacles)
        {
            switch(direction)
            {
                case LEFT:
                    if ( obstacle[0] == head[0] - partSize
                            && (obstacle[1] <= head[1]
                            && head[1] <= obstacle[1] + obstacle[3] - partSize))
                        return true;
                    break;
                case UP:
                    if ( obstacle[1] == head[1] - partSize
                            && (obstacle[0] <= head[0]
                            && head[0] <= obstacle[0] + obstacle[2] - partSize))
                        return true;
                    break;
                case RIGHT:
                    if ( obstacle[0] == head[0] + partSize
                            && (obstacle[1] <= head[1]
                            && head[1] <= obstacle[1] + obstacle[3] - partSize))
                        return true;
                    break;
                case DOWN:
                    if ( obstacle[1] == head[1] + partSize
                            && (obstacle[0] <= head[0]
                            && head[0] <= obstacle[0] + obstacle[2] - partSize))
                        return true;
                    break;
            }
        }
        return false;
    }

    public void draw(Graphics g)
    {
//        System.out.println(partSize);
        g.setColor(Color.lightGray);
        for(int i = 0; i < length; i++)
            g.fillRect(body.get(i)[0], body.get(i)[1], partSize-1, partSize-1);
    }

    public void move()
    {
        setStepValue();
        for(int i = length-1; i > 0; i--)
            body.set(i, body.get(i - 1).clone());

        head[0] += step[0];
        head[1] += step[1];
    }

    public boolean checkCollision(ArrayList<int[]> obstacles){

        return wallCollision() || tailCollision() || obstacleCollision(obstacles);
    }

    public boolean collect(Fruit fruit)
    {
        return (( head[0] == fruit.getPosition()[0]) && ( head[1] == fruit.getPosition()[1]));
    }

    public void grow(int partAmount)
    {
        int [] twoLastPosDiff = new int [] {
                body.get(length-1)[0] - body.get(length-2)[0],
                body.get(length-1)[1] - body.get(length-2)[1]
        };

        int [] newPartPos = new int[] {
                body.get(length-1)[0] + twoLastPosDiff[0],
                body.get(length-1)[1] + twoLastPosDiff[1]
        };

        body.add(newPartPos);
        length += 1;
    }

    public final int getLength() { return length; }
    public final ArrayList<int[]> getBody() { return body; }
    public EventAdapter getEventAdapter(){ return controls; }
}
