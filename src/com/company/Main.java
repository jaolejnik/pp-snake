package com.company;

import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
	    JFrame obj = new JFrame();
	    Gameplay gameplay = new Gameplay();

	    obj.setBounds(10, 10, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
	    obj.setResizable(false);
	    obj.setVisible(true);
	    obj.setFocusable(true);
	    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    obj.add(gameplay);

		gameplay.loop();
		obj.dispatchEvent(new WindowEvent(obj, WindowEvent.WINDOW_CLOSING));
    }
}
