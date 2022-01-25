package com.arithfighter.ccg;

public class DataAccessor {
    int record = 0;
    int score = 0;

    public int getRecord(){
        return record;
    }

    public void updateRecord(){
        record++;
    }

    public void resetRecord(){
        record-=record;
    }

    public int getScore(){
        return score;
    }

    public void updateScore(){
        score++;
    }

    public void resetScore(){
        score-=score;
    }

    public void resetData(){
        resetRecord();
        resetScore();
    }
}
