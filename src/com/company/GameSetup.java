package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

//TODO If youtube suggests playlist we will get 2 identical thumbnail images
//TODO Could make youtube go from first video to a trail of math.rand suggested ones. This way we wouldnt get same videos all the time from front page
//TODO Could remove video thumbnails if they are from the same account to stop loads of graham norton as example and make it easier
//TODO It shows hq default image urls for suggested videos in script, could try changing game to use hq default images to speed it up

public class GameSetup implements ButtonPressedListener, MouseListener {

    public static final int NO_OF_THUMBNAILS = 5, TN_HEIGHT = 220, TN_WIDTH = (int)(TN_HEIGHT*1.778); //This keeps the ratio of 1920/1080

    private boolean completionWait = false;

    Display display;
    YouTube yt;
    Player player;
    ArrayList<Thumbnail> thumbnails;
    int loaded;

    public GameSetup(){
        setUpGame();
    }

    private void setUpGame(){

        yt = new YouTube();

        player = new Player("James");

        try {
            display = new Display(this);
        }catch (IOException e){
            e.printStackTrace();
        }
        findVideo();
    }

    private void resetGame(){
        findVideo();
        System.out.println("Yeah.");
    }

    private void findVideo(){
        try {
            YouTubeVideo video = yt.getVideo();
            String title = video.getTitle();
            thumbnails = video.getSuggestedThumbnails(this);
            thumbnails.add(video.getThumbnail());
            for(Thumbnail thumb: thumbnails){
                thumb.registerListener(this);
            }
            loaded = 0;
            display.update(title, thumbnails);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completionWait = false;
            }
        });
        timer.start();
        timer.setRepeats(false);
    }

    public int getLoaded(){
        return loaded;
    }

    public void addLoaded(){
        loaded++;
    }

    @Override
    public void onButtonPressed(Thumbnail thumbnail) {
        if(thumbnail.getCorrect()) {
            player.addScore();

            completionWait = true;

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetGame();
                }
            });
            timer.start();
            timer.setRepeats(false);
        }
        else{
            player.subtractScore();
        }

        display.updateScore(player.getScore());
        display.updateConsecutive(player.getConsecutive());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getLocationOnScreen());
        if(!completionWait) {
            for (Thumbnail tn : thumbnails) {
                tn.checkClicked(e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
