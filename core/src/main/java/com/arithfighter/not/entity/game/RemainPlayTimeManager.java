package com.arithfighter.not.entity.game;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Recorder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RemainPlayTimeManager {
    private final Font remainCardFont;
    private final Recorder remainPlayTimeRecorder;

    public RemainPlayTimeManager(Recorder recorder, Font font) {
        remainCardFont = font;
        remainCardFont.setColor(Color.WHITE);

        remainPlayTimeRecorder = recorder;
    }

    public Recorder getRemainPlayTimeRecorder() {
        return remainPlayTimeRecorder;
    }

    public void init() {
        remainPlayTimeRecorder.reset();
    }

    public void draw(SpriteBatch batch, int x, int y) {
        remainCardFont.draw(
                batch, "Remain cards:" + remainPlayTimeRecorder.getRecord(), x, y);
    }

    public boolean isNoRemainCard() {
        return remainPlayTimeRecorder.getRecord() == 0;
    }
}
