package com.arithfighter.not.scene;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.*;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stage extends SceneComponent implements SceneEvent, MouseEvent {
    private final GamePlayComponent gamePlayComponent;
    private final SceneControlButton pauseButton;
    private final PauseMenu pauseMenu;

    public Stage(TextureService textureService, SoundManager soundManager, FontService fontService) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);

        gamePlayComponent = new GamePlayComponent(textureService, soundManager, fontService.getFont32());
        int numberBoxQuantity = 9;
        gamePlayComponent.setNumberQuantity(numberBoxQuantity);

        pauseMenu = new PauseMenu(textures, soundManager, fontService.getFont20());

        pauseButton = new SceneControlButton(textures[6], 1.8f);
        pauseButton.getButton().setPosition(1000, 600);
        pauseButton.getButton().setFont(fontService.getFont22());
    }

    public void init() {
        gamePlayComponent.init();
        pauseMenu.init();
        pauseButton.init();
    }

    private void update() {
        if (pauseButton.isStart()) {
            pauseMenu.update();

            if (pauseMenu.isResume()) {
                pauseButton.init();
                pauseMenu.init();
            }
        } else {
            pauseButton.update();

            gamePlayComponent.update(getCursorPos().getX(), getCursorPos().getY());
        }
    }

    public void render() {
        update();

        SpriteBatch batch = getBatch();

        gamePlayComponent.setBatch(batch);
        gamePlayComponent.draw();

        if (pauseButton.isStart()) {
            pauseMenu.draw(batch);
        } else
            pauseButton.getButton().draw(batch, "Pause");

    }

    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        if (pauseButton.isStart())
            pauseMenu.touchDown(x, y);
        else {
            pauseButton.getButton().on(x, y);
            gamePlayComponent.touchDown(x, y);
        }
    }

    public void touchDragged() {
        if (pauseButton.isStart())
            pauseMenu.touchDragged();
        else {
            pauseButton.getButton().off();
            gamePlayComponent.touchDragged(getCursorPos().getX(), getCursorPos().getY());
        }
    }

    public void touchUp() {
        if (pauseButton.isStart())
            pauseMenu.touchUp();
        else {
            pauseButton.getButton().off();
            gamePlayComponent.touchUp(getCursorPos().getX(), getCursorPos().getY());
        }
    }
}

