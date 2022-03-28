package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.entity.CharacterList;
import com.arithfighter.ccg.font.Font;
import com.arithfighter.ccg.widget.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CharacterMenu {
    private final Font selectionFont;
    private final Button[] buttons;
    private final Button startButton;
    private int selectIndex = 0;
    private enum GameReady {NEUTRAL, READY, START}
    private GameReady gameReady = GameReady.NEUTRAL;
    private SpriteBatch batch;

    public CharacterMenu(Texture[] textures) {
        int length = CharacterList.values().length;
        buttons = new Button[length];

        for (int i = 0; i < length; i++) {
            buttons[i] = new Button(textures[6]);
            buttons[i].setPosition(getButtonX(i), getButtonY(i));
        }

        startButton = new Button(textures[6]);
        startButton.setPosition(900, 120);

        selectionFont = new Font(36);
        selectionFont.setColor(Color.WHITE);
    }

    public boolean isCharButtonActive() {
        return buttons[selectIndex].isActive();
    }

    public boolean isStartButtonActive(){
        return startButton.isActive();
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    private int getButtonX(int i) {
        return i < 3 ? 200 : 500;
    }

    private int getButtonY(int i) {
        int initY = 480;
        int margin = 150;
        int row = 3;

        return i < row ? initY - i * margin : initY - (i - row) * margin;
    }

    public void init() {
        gameReady = GameReady.NEUTRAL;
    }

    public void draw() {
        handleStartButton();

        CharacterList[] characters = CharacterList.values();

        for (int i = 0; i < buttons.length; i++)
            buttons[i].draw(batch, characters[i].name());

        startButton.draw(batch, "Start");

        selectionFont.draw(batch, characters[getSelectIndex()].name(), 900, 500);
    }

    private void handleStartButton(){
        if (startButton.isActive())
            gameReady = GameReady.READY;
        else{
            if (gameReady == GameReady.READY)
                gameReady = GameReady.START;
        }
    }

    public int getSelectIndex() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isActive())
                selectIndex = i;
        }
        return selectIndex;
    }

    public boolean isGameStart() {
        return gameReady == GameReady.START;
    }

    public void activateButton(int mouseX, int mouseY) {
        for (Button button : buttons)
            button.activate(mouseX, mouseY);

        startButton.activate(mouseX, mouseY);
    }

    public void deactivateButton() {
        for (Button button : buttons)
            button.deactivate();

        startButton.deactivate();
    }

    public void dispose() {
        for (Button button : buttons)
            button.dispose();

        startButton.dispose();
        selectionFont.dispose();
    }
}
