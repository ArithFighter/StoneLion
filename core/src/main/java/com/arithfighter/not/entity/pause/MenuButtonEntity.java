package com.arithfighter.not.entity.pause;

import com.arithfighter.not.file.audio.SoundManager;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.button.Button;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;

class MenuButtonEntity {
    private final SceneControlButtonProducer buttons;

    public MenuButtonEntity(Texture texture, Font font) {
        buttons = new SceneControlButtonProducer(3);
        buttons.setButtons(texture, font);
    }

    public SceneControlButtonProducer getButtons() {
        return buttons;
    }

    public void init() {
        for (SceneControlButton button : buttons.getSceneControlButtons())
            button.init();
    }

    public void offButtons() {
        for (SceneControlButton button : buttons.getSceneControlButtons())
            button.getButton().off();
    }

    public void onButton(float x, float y) {
        for (SceneControlButton button : buttons.getSceneControlButtons())
            button.getButton().on(x, y);
    }

    public void update() {
        for (SceneControlButton button : buttons.getSceneControlButtons())
            button.update();
    }

    public SceneControlButton getResume() {
        return buttons.getSceneControlButtons()[0];
    }

    public SceneControlButton getOption() {
        return buttons.getSceneControlButtons()[1];
    }

    public SceneControlButton getQuit() {
        return buttons.getSceneControlButtons()[2];
    }

    public void playSound(SoundManager soundManager) {
        for (int i = 0; i < buttons.getSceneControlButtons().length; i++) {
            Button b = buttons.getSceneControlButtons()[i].getButton();
            if (b.isOn())
                soundManager.playAcceptSound();
        }
    }
}
