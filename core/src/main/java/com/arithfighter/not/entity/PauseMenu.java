package com.arithfighter.not.entity;

import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.widget.*;
import com.arithfighter.not.widget.button.Button;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.widget.dialog.OptionDialog;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private final ButtonProducer buttons;
    private final OptionDialog dialog;
    private final VisibleWidget background;
    private final SoundManager soundManager;
    private final TextProvider textProvider;
    private final Font font;

    public PauseMenu(Texture[] textures, SoundManager soundManager) {
        this.soundManager = soundManager;

        textProvider = new TextProvider();

        font = new Font(20);
        font.setColor(Color.BLACK);

        buttons = new ButtonProducer(textures, font);

        dialog = new OptionDialog(textures);
        dialog.setFont(font);
        dialog.setButtonFont(font);
        dialog.setOriginString(textProvider.getPauseMenuTexts()[3]+textProvider.getPauseMenuTexts()[4]);

        int x = 500;
        int y = 300;
        int margin = 100;

        background = new SpriteWidget(textures[1], 5f);
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

    public void dispose() {
        font.dispose();
    }
}

class ButtonProducer {
    private final SceneControlButton[] buttons;

    public ButtonProducer(Texture[] textures, Font font) {
        buttons = new SceneControlButton[3];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new SceneControlButton(textures[6], 1.8f);
            buttons[i].getButton().setPosition(540, 250 + 150 * i);
            buttons[i].getButton().setFont(font);
        }
    }

    public SceneControlButton[] getButtons() {
        return buttons;
    }

    public SceneControlButton getResume() {
        return buttons[2];
    }

    public SceneControlButton getOption() {
        return buttons[1];
    }

    public SceneControlButton getQuit() {
        return buttons[0];
    }

    public void draw(SpriteBatch batch, String[] texts) {
        for (int i = 0; i < buttons.length; i++) {
            Button b = buttons[i].getButton();
            b.draw(batch, texts[i]);
        }
    }
}