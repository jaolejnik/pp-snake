package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Scoreboard extends JPanel {

    BufferedReader input;
    BufferedWriter output;
    String fileName;
    ArrayList<Integer> scores;
    int newScore;

    public Scoreboard(String _fileName)
    {
        fileName = _fileName;
        scores = new ArrayList<Integer>();
        loadScores();
    }

    public void loadScores()
    {
        try {
             input = new BufferedReader(new FileReader(fileName));
            int score;
            String tmp;
            while ((tmp = input.readLine()) != null) {
                score = Integer.parseInt(tmp);
                scores.add(score);
            }
            input.close();

        }
        catch (FileNotFoundException e){
            System.out.println("file " + fileName + " not found!");
            System.exit(1);
        }
        catch(IOException e) {
            System.out.println("failed to read from file " + fileName);
            System.exit(2);
        };
    }

    public void addNewScore(int score)
    {
        setFocusable(true);
        grabFocus();
        System.out.println(score);
        scores.add(score);
        newScore = score;
    }

    public void sortScores()
    {
        Collections.sort(scores);
        Collections.reverse(scores);
    }

    public void saveScores()
    {
        try {
            output = new BufferedWriter(new FileWriter(fileName));
            for(int score: scores)
            {
                System.out.println(score);
                output.write(score + "\n");
            }
            output.close();

        }
        catch (FileNotFoundException e){
            System.out.println("file " + fileName + " not found!");
            System.exit(1);
        }
        catch(IOException e) {
            System.out.println("failed to write to file " + fileName);
            System.exit(2);
        };
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        display(g);
    }
    public void display(Graphics g)
    {
        g.setColor(Color.white);
        g.setFont(new Font("Impact", Font.PLAIN, 42));
        g.drawString(" YOUR SCORE",
                Constants.WINDOW_WIDTH/4,
                Constants.WINDOW_HEIGHT/10);
        g.drawString(String.format("%06d", newScore),
                Constants.WINDOW_WIDTH/3,
                Constants.WINDOW_HEIGHT/10 + 45);
        g.drawString(" HIGH  SCORES",
                Constants.WINDOW_WIDTH/4,
                Constants.WINDOW_HEIGHT/4);

        g.setFont(new Font("Impact", Font.PLAIN, 36));
        for(int i = 1; i <= 10; i++)
        {
            g.drawString(String.format("%02d", i) +". " + String.format("%06d", scores.get(i-1)),
                    Constants.WINDOW_WIDTH/3,
                    Constants.WINDOW_HEIGHT/4 + 45*i);
        }
        repaint();
    }
}
