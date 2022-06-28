package com.arithfighter.not.scene.scene;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.scene.SceneLayout;
import com.arithfighter.not.time.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Transition extends SceneComponent implements SceneEvent, MouseEvent {
    private final Font font;
    private final Timer timer;
    private boolean isGameStart = false;
    private final Rectangle grid;

    public Transition(FontService fontService){
        font = fontService.getFont45();
        font.setColor(Color.WHITE);

        timer = new Timer();
        timer.setTime(1.5f);

        SceneLayout layout = new SceneLayout();
        layout.setMaxLayout(new Rectangle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        layout.setGrid(2,2);
        grid = layout.getGrid();
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
        timer.init();
        isGameStart = false;
    }

    @Override
    public void render() {
        SpriteBatch batch = getBatch();
        String ready = "Ready";

        timer.update();

        if (timer.isTimesOut())
            isGameStart = true;

        font.draw(
                batch,
                ready,
                grid.getWidth() -ready.length()* font.getSize()/2f,
                grid.getHeight()+ font.getSize()*2
        );
    }
}
