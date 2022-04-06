package com.arithfighter.ccg.entity;

import com.arithfighter.ccg.widget.button.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;

public class PauseMenu {
    private final Pool<Button> buttonPool;
    private final Button returnButton;
    private boolean isReturnToMainMenu = false;

    public PauseMenu(Texture[] textures) {
        buttonPool = new Pool<Button>() {
            @Override
            protected Button newObject() {
                return new Button(textures[6], 1.8f);
            }
        };
        returnButton = buttonPool.obtain();
    }

    public void draw(SpriteBatch batch) {
        returnButton.setPosition(1000, 600);
        returnButton.draw(batch, "Return");
    }

    public void update() {
        isReturnToMainMenu = returnButton.isActive();
    }

    public void init() {
        isReturnToMainMenu = false;
        buttonPool.free(returnButton);
    }

    public boolean isReturnToMainMenu() {
        return isReturnToMainMenu;
    }

    public void touchDown(float x, float y) {
        returnButton.activate(x, y);
    }

    public void touchUp() {
        returnButton.deactivate();
    }

    public void dispose() {
        returnButton.dispose();
    }
}
