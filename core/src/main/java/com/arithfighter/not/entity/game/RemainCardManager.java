package com.arithfighter.not.entity.game;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RemainCardManager {
    private final Font remainCardFont;
    private final Recorder remainCardRecorder;

    public RemainCardManager(Recorder recorder, Font font) {
        remainCardFont = font;
        remainCardFont.setColor(Color.WHITE);

        remainCardRecorder = recorder;
    }

    public Recorder getRemainCardRecorder() {
        return remainCardRecorder;
    }

    public void init() {
        remainCardRecorder.reset();
    }

    public void draw(SpriteBatch batch, int x, int y) {
        remainCardFont.draw(
                batch, "Remain cards:" + remainCardRecorder.getRecord(), x, y);
    }

    public boolean isNoRemainCard() {
        return remainCardRecorder.getRecord() == 0;
    }
}
