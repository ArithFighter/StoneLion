package com.arithfighter.not.entity;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.GameRecorder;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RecordDisplacer {
    private final Font[] texts;
    private final VisibleWidget background;
    private GameRecorder gameRecorder;
    private final int posX;

    public RecordDisplacer(Texture[] textures) {
        texts = new Font[4];
        posX = 0;

        for (int i = 0; i < texts.length; i++) {
            texts[i] = new Font(20);
            texts[i].setColor(Color.BLACK);
        }

        background = new SpriteWidget(textures[1], 6f);
        background.setPosition(posX, 150);
    }

    public void setGameRecorder(GameRecorder gameRecorder) {
        this.gameRecorder = gameRecorder;
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        int x = posX + 50;
        texts[0].draw(batch, "stage: " + gameRecorder.getStagesRecorder().getRecord(), x, 700);
        texts[1].draw(batch, "win: " + gameRecorder.getWinRecorder().getRecord(), x, 600);
        texts[2].draw(batch, "lose: " + gameRecorder.getLoseRecorder().getRecord(), x, 500);
        texts[3].draw(batch, "tokens: " + gameRecorder.getTokenRecorder().getRecord(), x, 400);
    }

    public void dispose() {
        for (Font font : texts)
            font.dispose();
    }
}
