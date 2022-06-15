package com.arithfighter.not.scene;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StageMessage {
    private final Font text;

    enum State {WIN, LOSE, NEUTRAL, READY}

    private State state = State.READY;
    private final TimeHandler transitionHandler;
    private final float x;
    private final float y;

    public StageMessage(float x, float y, Font font) {
        this.x = x;
        this.y = y;
        text = font;
        text.setColor(Color.WHITE);

        transitionHandler = new TimeHandler();
    }

    public final boolean isNeutral() {
        return state == State.NEUTRAL;
    }

    public final boolean isWin() {
        return state == State.WIN;
    }

    public final boolean isLose() {
        return state == State.LOSE;
    }

    public final void init() {
        state = State.READY;
        transitionHandler.resetPastedTime();
    }

    public final void draw(SpriteBatch batch) {
        if (state == State.READY)
            showReady(batch);

        if (isStageComplete() || isExceedCardLimitAndStageNotComplete())
            showEnd(batch);
    }

    private void showReady(SpriteBatch batch) {
        transitionHandler.updatePastedTime();

        float r = 1.5f;
        float a = 2.5f;

        if (transitionHandler.getPastedTime() < r)
            text.draw(batch, "Game Ready", x, y);
        if (transitionHandler.getPastedTime() > r && transitionHandler.getPastedTime() < a)
            text.draw(batch, "Action", x, y);
        if (transitionHandler.getPastedTime() > a)
            state = State.NEUTRAL;
    }

    private void showEnd(SpriteBatch batch) {
        transitionHandler.updatePastedTime();

        float time = 5f;
        if (transitionHandler.getPastedTime() < time)
            text.draw(batch, getMessage(), x, y);
        else {
            setFinalState();
        }
    }

    private String getMessage() {
        String message = "";
        if (isStageComplete())
            message = "Complete";
        if (isExceedCardLimitAndStageNotComplete())
            message = "Exceed limit";
        return message;
    }

    private void setFinalState() {
        if (isStageComplete())
            state = State.WIN;
        if (isExceedCardLimitAndStageNotComplete())
            state = State.LOSE;
    }

    public boolean isExceedCardLimitAndStageNotComplete() {
        return false;
    }

    public boolean isStageComplete() {
        return false;
    }
}
