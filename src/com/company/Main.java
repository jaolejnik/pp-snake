package com.company;

import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	    JFrame obj = new JFrame();
	    Gameplay gameplay = new Gameplay();
	    Scoreboard scoreboard = new Scoreboard("scores.txt");

		obj.setBounds(10, 10, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
	    obj.setResizable(false);
	    obj.setVisible(true);
	    obj.setFocusable(true);
	    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    obj.add(gameplay);

	    gameplay.countdown();
		gameplay.loop();
		Thread.sleep(3000);
		obj.remove(gameplay);

		scoreboard.addNewScore(gameplay.getPoints());
		scoreboard.sortScores();
		obj.add(scoreboard);

		obj.revalidate();
		scoreboard.saveScores();
//		obj.dispatchEvent(new WindowEvent(obj, WindowEvent.WINDOW_CLOSING));
    }
}
