package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadDisplay extends JPanel {

    private GameSetup gs;

    public LoadDisplay(GameSetup gs){
        this.gs = gs;

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 80));
        g.drawString("Loading...", 600,  350);
        g.fillRect(500, 500, (600/gs.NO_OF_THUMBNAILS) *(gs.getLoaded()+1), 30);

    }
}
