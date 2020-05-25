package com.company;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {

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

    public void drawBorders(Graphics g)
    {
        g.setColor(Color.white);
        g.drawRect(Constants.SMALL_BORDER_X,
                Constants.SMALL_BORDER_Y,
                Constants.SMALL_BORDER_WIDTH,
                Constants.SMALL_BORDER_HEIGHT);

        g.setColor(Color.white);
        g.drawRect(Constants.BIG_BORDER_X,
                Constants.BIG_BORDER_Y,
                Constants.BIG_BORDER_WIDTH,
                Constants.BIG_BORDER_HEIGHT);
    }

}
