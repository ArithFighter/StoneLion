package com.arithfighter.not.entity;

import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private final SceneControlButton quitButton;
    private final SceneControlButton resumeButton;
    private final VisibleWidget background;
    private final SoundManager soundManager;

    public PauseMenu(Texture[] textures, SoundManager soundManager) {
        this.soundManager = soundManager;

        resumeButton = new SceneControlButton(textures[6], 1.8f);
        resumeButton.getButton().setPosition(540,450);

        quitButton = new SceneControlButton(textures[6], 1.8f);
        quitButton.getButton().setPosition(540, 250);

        background = new SpriteWidget(textures[1], 5f);
        background.setPosition(500,200);
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);

        resumeButton.getButton().draw(batch, "Resume");

        quitButton.getButton().draw(batch, "Quit");
    }

    public void update() {
        resumeButton.update();
        quitButton.update();
    }

    public void init() {
        resumeButton.init();
        quitButton.init();
    }

    public boolean isReturnToMainMenu() {
        return quitButton.isStart();
    }

    public boolean isResume(){
        return resumeButton.isStart();
    }

    public void touchDown(float x, float y) {
        resumeButton.getButton().activate(x, y);
        quitButton.getButton().activate(x, y);
    }

    public void touchDragged(){
        resumeButton.getButton().deactivate();
        quitButton.getButton().deactivate();
    }

    public void touchUp() {
        if (resumeButton.getButton().isActive())
            soundManager.playReturnSound();

        resumeButton.getButton().deactivate();

        if (quitButton.getButton().isActive())
            soundManager.playAcceptSound();

        quitButton.getButton().deactivate();
    }

    public void dispose() {
        quitButton.dispose();
    }
}
