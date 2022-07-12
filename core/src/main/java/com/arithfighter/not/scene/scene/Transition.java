package com.arithfighter.not.scene.scene;

import com.arithfighter.not.file.texture.TextureGetter;
import com.arithfighter.not.file.texture.TextureService;
import com.arithfighter.not.entity.Background;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.time.Timer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Transition extends SceneComponent implements SceneEvent{
    private final Font font;
    private final Timer timer;
    private boolean isGameStart = false;
    private final Rectangle grid;
    private final Background background;

    public Transition(TextureService textureService, FontService fontService) {
        TextureGetter tg = new TextureGetter(textureService);
        font = fontService.getFont45();
        font.setColor(Color.WHITE);

        timer = new Timer();
        timer.setTime(1.5f);

        LayoutSetter layout = new LayoutSetter();
        layout.setGrid(2, 2);
        grid = layout.getGrid();

        background = new Background(tg.getObjectMap().get("object/bamboo-forest.png"));
    }

    public boolean isGameStart() {
        return isGameStart;
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

        background.draw(batch);

        timer.update();

        if (timer.isTimesOut())
            isGameStart = true;

        font.draw(
                batch,
                ready,
                grid.getWidth() - ready.length() * font.getSize() / 2f,
                grid.getHeight() + font.getSize() * 2
        );
    }
}
