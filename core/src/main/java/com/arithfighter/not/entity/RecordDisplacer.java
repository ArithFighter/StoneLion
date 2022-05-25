package com.arithfighter.not.entity;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.GameRecorder;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RecordDisplacer {
    private final Font font;
    private final VisibleWidget background;
    private GameRecorder gameRecorder;
    private final int posX;

    public RecordDisplacer(Texture[] textures) {
        posX = 0;

        font= new Font(20);
        font.setColor(Color.BLACK);

        background = new SpriteWidget(textures[1], 6f);
        background.setPosition(posX, 150);
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        int x = posX + 50;
        font.draw(batch, "stage: " + gameRecorder.getStagesRecorder().getRecord(), x, 700);
        font.draw(batch, "win: " + gameRecorder.getWinRecorder().getRecord(), x, 600);
        font.draw(batch, "lose: " + gameRecorder.getLoseRecorder().getRecord(), x, 500);
        font.draw(batch, "tokens: " + gameRecorder.getTokenRecorder().getRecord(), x, 400);
    }

    public void dispose() {
        font.dispose();
    }
}
