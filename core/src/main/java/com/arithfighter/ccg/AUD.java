package com.arithfighter.ccg;

import com.arithfighter.ccg.widget.GameDataDisplacer;
import com.arithfighter.ccg.widget.ScoreBoard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AUD {
    GameDataDisplacer dataDisplacer;
    ScoreBoard scoreBoard;

    public AUD(){
        dataDisplacer = new GameDataDisplacer();
        scoreBoard = new ScoreBoard();
    }

    public void showData(int mouseX, int mouseY, SpriteBatch batch){
        dataDisplacer.draw(mouseX, mouseY, batch);
    }

    public void showScore(String score, SpriteBatch batch){
        scoreBoard.draw("ScoreBoard: "+score, batch);
    }

    public void dispose(){
        dataDisplacer.dispose();
        scoreBoard.dispose();
    }
}
