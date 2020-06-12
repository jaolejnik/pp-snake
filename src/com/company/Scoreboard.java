package com.company;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Scoreboard extends JPanel {

    BufferedReader input;
    BufferedWriter output;
    String fileName;
    ArrayList<Integer> scores;

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
        System.out.println(score);
        scores.add(score);
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

}
