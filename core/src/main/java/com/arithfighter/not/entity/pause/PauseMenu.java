package com.arithfighter.not.entity.pause;

import com.arithfighter.not.file.audio.SoundManager;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.widget.*;
import com.arithfighter.not.widget.dialog.OptionDialog;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private final MenuButtonEntity buttons;
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

        buttons = new MenuButtonEntity(textures[1], font);
        buttons.getButtons().setPoint(menuPoint);
        RawWidget widget = background.getWidget();
        buttons.getButtons().setVerticalCenterLayout(widget.getWidth(), widget.getHeight());

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