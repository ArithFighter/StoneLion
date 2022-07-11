package com.arithfighter.not.entity.pause;

import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.widget.*;
import com.arithfighter.not.widget.button.Button;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.widget.dialog.OptionDialog;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private final ButtonProducer buttons;
    private final OptionDialog dialog;
    private final VisibleWidget background;
    private final SoundManager soundManager;
    private final TextProvider textProvider;

    public PauseMenu(Texture[] textures, SoundManager soundManager, Font font) {
        this.soundManager = soundManager;

        textProvider = new TextProvider();

        buttons = new ButtonProducer(textures[1], font);

        dialog = new OptionDialog(textures[2], textures[1]);
        dialog.setFont(font);
        dialog.setButtonFont(font);
        dialog.setOriginString(textProvider.getPauseMenuTexts()[3]);

        int x = 500;
        int y = 300;
        int margin = 100;

        background = new SpriteWidget(textures[0], 5f);
        background.setPosition(x, y - margin);
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);

        buttons.draw(batch, textProvider.getPauseMenuTexts());

        if (buttons.getQuit().isStart()){
            dialog.draw(batch);
        }
    }

    public void update() {
        if (buttons.getQuit().isStart()) {
            dialog.update();

            if (dialog.getNoButton().isStart()) {
                buttons.getQuit().init();
                dialog.init();
            }
        } else {
            for (SceneControlButton button : buttons.getButtons())
                button.update();
        }
    }

    public void init() {
        for (SceneControlButton button : buttons.getButtons())
            button.init();
        dialog.init();
    }

    public boolean isReturnToMainMenu() {
        return dialog.getYesButton().isStart();
    }

    public boolean isResume() {
        return buttons.getResume().isStart();
    }

    public boolean isOpenOption() {
        return buttons.getOption().isStart();
    }

    public void touchDown(float x, float y) {
        if (isQuit())
            dialog.activate(x, y);
        else {
            for (SceneControlButton button : buttons.getButtons())
                button.getButton().on(x, y);
        }
    }

    public void touchDragged() {
        if (isQuit())
            dialog.deactivate();
        else {
            deactivateButtons();
        }
    }

    public void touchUp() {
        if (isQuit())
            dialog.deactivate();
        else {
            playSound();
            deactivateButtons();
        }
    }

    private boolean isQuit() {
        return buttons.getQuit().isStart();
    }

    private void deactivateButtons() {
        for (SceneControlButton button : buttons.getButtons())
            button.getButton().off();
    }

    private void playSound() {
        for (int i = 0; i < buttons.getButtons().length; i++) {
            Button b = buttons.getButtons()[i].getButton();
            if (b.isOn())
                soundManager.playAcceptSound();
        }
    }
}