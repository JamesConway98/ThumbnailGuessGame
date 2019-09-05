package com.company;

import javax.swing.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Thumbnail {

    private boolean correct;
    private Image image;
    private int x, y, height = GameSetup.TN_HEIGHT, width = GameSetup.TN_WIDTH;
    private boolean beenClicked = false;
    private List<ButtonPressedListener> listeners = new ArrayList<>();

    public Thumbnail(Image img, boolean crct){
        image = img;
        correct = crct;
    }

    public boolean getCorrect(){
        return correct;
    }

    public Image getImage(){
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean beenClicked(){
        return beenClicked;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeigth(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void moveRight(){
        x = x - 3;
        y = y - 2;
        width = width + 6;
        height = height + 4;
    }

    public void onClicked(){
        if(!beenClicked) {
            if(correct) {
                Timer timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        moveRight();
                    }
                });
                timer.start();
            }
            notifyListener();
        }
        beenClicked = true;
    }

    public void checkClicked(int clickX, int clickY){
        if(clickX > x && clickX < x + GameSetup.TN_WIDTH){
            if(clickY > y && clickY < y +GameSetup.TN_HEIGHT){
                onClicked();
            }
        }
    }

    public void registerListener(ButtonPressedListener listener){
        this.listeners.add(listener);
    }

    public void notifyListener(){
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onButtonPressed(this));
    }
}
