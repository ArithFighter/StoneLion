package com.arithfighter.not.scene.scene;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.WindowSetting;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneComponent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Transition extends SceneComponent implements SceneEvent, MouseEvent {
    private final Font font;
    private final TimeHandler timeHandler;
    private boolean isGameStart = false;

    public Transition(FontService fontService){
        font = fontService.getFont45();
        font.setColor(Color.WHITE);

        timeHandler = new TimeHandler();
    }

    public boolean isGameStart(){
        return isGameStart;
    }

    @Override
    public void touchDown() {

    }

    @Override
    public void touchDragged() {

    }

    @Override
    public void touchUp() {

    }

    @Override
    public void init() {
        timeHandler.resetPastedTime();
        isGameStart = false;
    }

    @Override
    public void render() {
        SpriteBatch batch = getBatch();
        String ready = "Ready";

        timeHandler.updatePastedTime();

        if (timeHandler.getPastedTime()>1.5f)
            isGameStart = true;

        font.draw(
                batch,
                ready,
                WindowSetting.CENTER_X-ready.length()* font.getSize()/2f,
                WindowSetting.CENTER_Y+ font.getSize()
        );
    }
}