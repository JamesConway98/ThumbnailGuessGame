package com.company;

public class Player {

    private int score, inARow;
    private String name;

    public Player(String name){
        this.name = name;
    }

    public int getScore(){
        return score;
    }

    public int getConsecutive(){
        return inARow;
    }

    public void addScore(){
        score = score + (5 * inARow) + 10;
        inARow = inARow + 1;
    }

    public void subtractScore(){
        if(score > 0) {
            score = score - 5;
        }
        inARow = 0;
    }


}
