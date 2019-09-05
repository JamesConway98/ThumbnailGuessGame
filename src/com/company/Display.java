package com.company;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

    public class Display{

        private GameDisplay gamePanel;
        private LoadDisplay loadPanel;
        private JFrame frame;

        public Display(GameSetup gs) throws IOException
        {
            frame = new JFrame();
            gamePanel=new GameDisplay();
            loadPanel = new LoadDisplay(gs);
            gamePanel.addMouseListener(gs);
            frame.setLayout(new BorderLayout());
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int height = screenSize.height - 35;
            int width = screenSize.width;
            frame.setSize(width, height);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(loadPanel);
        }

        public void update(String title, ArrayList<Thumbnail> suggestedThumbnails){
            frame.remove(loadPanel);
            gamePanel.setTnails(suggestedThumbnails);
            gamePanel.setVidTitle(title);
            frame.add(gamePanel, BorderLayout.CENTER);
            gamePanel.setVisible(true);
            frame.repaint();
            frame.setVisible(true);
        }

        public void updateScore(int score){
            gamePanel.setCurrentScore(score);
        }

        public void updateConsecutive(int inARow){
            gamePanel.setConsecutive(inARow);
        }

    }

