package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameDisplay extends JPanel {

    private ArrayList<Thumbnail> tnails;
    private String vidTitle;
    private int currentScore = 0, inARow = 0;
    public static final int COLUMNS = 3;

    public void setTnails(ArrayList<Thumbnail> thumbnails){
        tnails = thumbnails;
        ArrayList<Thumbnail> tnailsCopy = new ArrayList<>(tnails);
        int row = 0;

        for(int i = 0; i <= GameSetup.NO_OF_THUMBNAILS; i++) {
            //stops adding row on first pass
            if (i > 1 && i % COLUMNS == 0)
                row++;
            //Random number between 0 and however many is left in tnailsCopy
            int random = (int) (Math.random() * tnailsCopy.size());
            //This is the index of the randomly picked thumbnail
            int index = tnails.indexOf(tnailsCopy.get(random));

            tnails.get(index).setX((i % COLUMNS) * (1600/COLUMNS) + 100);
            tnails.get(index).setY(row * (tnails.get(0).getHeight() + 25) + 50);

            tnailsCopy.remove(random);
        }

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    public void setVidTitle(String title){
        vidTitle = title;
    }

    public void setCurrentScore(int score){
        currentScore = score;
    }

    public void setConsecutive(int cons){
        inARow = cons;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Thumbnail tn: tnails) {

            g.drawImage(tn.getImage(), tn.getX(), tn.getY(), tn.getWidth(), tn.getHeight(),null);

            if(tn.beenClicked()){
                if(tn.getCorrect()){
                    int alpha = 127; // 50% transparent
                    Color myColour = new Color(0, 255, 0, alpha);
                    g.setColor(myColour);
                    g.fillRect(tn.getX(), tn.getY(), tn.getWidth(), tn.getHeight());
                }else {
                    int alpha = 127; // 50% transparent
                    Color myColour = new Color(255, 0, 0, alpha);
                    g.setColor(myColour);
                    g.fillRect(tn.getX(), tn.getY(), tn.getWidth(), tn.getHeight());
                }
            }
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 28));
        g.drawString(vidTitle, 250, 750);
        g.drawString("Score = " + currentScore + " In a row " + inARow, 250, 850);
    }
}
