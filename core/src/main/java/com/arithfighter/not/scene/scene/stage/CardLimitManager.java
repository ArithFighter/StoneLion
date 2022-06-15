package com.arithfighter.not.scene.scene.stage;

import com.arithfighter.not.WindowSetting;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardLimitManager {
    private final Recorder playRecord;
    private final Font cardLimitText;
    private int cardLimit;

    public CardLimitManager(Font font) {
        cardLimitText = font;

        playRecord = new Recorder();
    }

    public void setCardLimit(int cardLimit) {
        this.cardLimit = cardLimit;
    }

    public Recorder getPlayRecord() {
        return playRecord;
    }

    public void draw(SpriteBatch batch) {
        cardLimitText.setColor(Color.WHITE);
        cardLimitText.draw(
                batch,
                "cards: " + (cardLimit - playRecord.getRecord()),
                WindowSetting.GRID_X * 8,
                WindowSetting.GRID_Y * 8 + WindowSetting.CENTER_Y);
    }

    public boolean isExceedCardLimit() {
        return playRecord.getRecord() >= cardLimit;
    }
}
