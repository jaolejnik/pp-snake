package com.company;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class Gameplay extends JPanel{

    int points = 0;
    boolean gameRunning = true;
    long startTime;
    Timer timer;
    GameBoard board;
    Snake snake;
    Fruit fruit;
    Frog frog;



    public Gameplay(){
        snake = new Snake();
        board = new GameBoard(snake.getBody());
        fruit = new Fruit(snake.getBody(), board.getObstacles());
        frog = new Frog(snake.getBody(), board.getObstacles());
        addKeyListener(snake.getEventAdapter());
        setFocusable(true);
        grabFocus();
        startTime = System.currentTimeMillis();
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
//        board.drawGridSectors(g);
        board.drawObstacles(g);
        board.drawBorders(g);
        board.drawPoints(g, points);
        snake.draw(g);
        fruit.draw(g);
        frog.draw(g);
        repaint();
    }

    private void update()
    {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        if (snake.checkCollision(board.getObstacles()))
            gameRunning = false;
        else
            snake.move();
            frog.move(elapsedTime);
            if (snake.collectFruit(fruit))
            {
                snake.grow(1);
                points += 10;
                fruit = new Fruit(snake.getBody(), board.getObstacles());
            }
            if (snake.collectFrog(frog))
            {
                snake.grow(2);
                points += 25;
                frog = new Frog(snake.getBody(), board.getObstacles());
            }
    }

    public void loop()
    {
        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0;
        int fps = 0;
        final int TARGET_FPS = 10;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        grabFocus();
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

            update();

            try{Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );}
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
