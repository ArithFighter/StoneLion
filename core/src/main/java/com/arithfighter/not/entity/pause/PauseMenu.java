package com.arithfighter.not.entity.pause;

import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.widget.*;
import com.arithfighter.not.widget.button.Button;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.widget.dialog.OptionDialog;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private final ButtonEntity buttons;
    private final OptionDialog dialog;
    private final VisibleWidget background;
    private final SoundManager soundManager;
    private final TextProvider textProvider;

    public PauseMenu(Texture[] textures, SoundManager soundManager, Font font) {
        this.soundManager = soundManager;

        textProvider = new TextProvider();

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(2, 7);
        Rectangle grid = layoutSetter.getGrid();

        background = new SpriteWidget(textures[0], 5f);
        Point menuPoint = new Point(grid.getWidth() - background.getWidget().getWidth() / 2, grid.getHeight());

        background.setPosition(menuPoint.getX(), menuPoint.getY());

        buttons = new ButtonEntity(textures[1], font);
        buttons.getButtons().setPoint(menuPoint);
        buttons.getButtons().setLayout(background.getWidget().getWidth(), background.getWidget().getHeight());

        dialog = new OptionDialog(textures[2], textures[1]);
        dialog.setFont(font);
        dialog.setButtonFont(font);
        dialog.setOriginString(textProvider.getPauseMenuTexts()[3]);

    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);

        buttons.getButtons().draw(batch, textProvider.getPauseMenuTexts());

        if (buttons.getQuit().isStart()) {
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
            buttons.update();
        }
    }

    public void init() {
        buttons.init();
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
        if (buttons.getQuit().isStart())
            dialog.activate(x, y);
        else {
            buttons.onButton(x, y);
        }
    }

    public void touchDragged() {
        if (buttons.getQuit().isStart())
            dialog.deactivate();
        else {
            buttons.offButtons();
        }
    }

    public void touchUp() {
        if (buttons.getQuit().isStart())
            dialog.deactivate();
        else {
            buttons.playSound(soundManager);
            buttons.offButtons();
        }
    }
}

class ButtonEntity {
    private final SceneControlButtonProducer buttons;

    public ButtonEntity(Texture texture, Font font) {
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