package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class Gameplay extends JPanel{

    Snake snake = new Snake();
    Fruit fruit = new Fruit();
    GameBoard board = new GameBoard();
    boolean gameRunning = true;


    public Gameplay()
    {
        addKeyListener(snake.getEventAdapter());
        setFocusable(true);
        grabFocus();
    }

    class Loop extends TimerTask {
        @Override public void run() {
            if (gameRunning)
                update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        draw(g);
    }

    public void draw(Graphics g)
    {
        board.drawGrid(g);
        board.drawBorders(g);
        snake.draw(g);
        fruit.draw(g);
        repaint();

    }

    private void update()
    {
        if (snake.checkCollision())
            gameRunning = false;
        else
            snake.move();
            if (snake.collect(fruit))
            {
                snake.grow(1);
                fruit = new Fruit();
            }
    }

    public void loop()
    {
        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0;
        int fps = 0;
        final int TARGET_FPS = 10;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (gameRunning)
        {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000)
            {
                System.out.println("(FPS: "+fps+")");
                lastFpsTime = 0;
                fps = 0;
            }
            grabFocus();
            update();

            try{Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );}
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
