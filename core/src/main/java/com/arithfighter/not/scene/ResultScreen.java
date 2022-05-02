package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureManager;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.TextProvider;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ResultScreen extends SceneComponent implements SceneEvent, MouseEvent {
    private final Font winOrLost;
    private final Font tokenMessage;
    private final SceneControlButton continueButton;
    private final SceneControlButton quitButton;
    private int remainingTokens = 0;
    private ResultState state = ResultState.WIN;
    private final TextProvider textProvider;

    public ResultScreen(TextureManager textureManager) {
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        textProvider = new TextProvider();

        continueButton = new SceneControlButton(textures[6], 2);
        continueButton.getButton().setPosition(600, 150);

        quitButton = new SceneControlButton(textures[6], 2);
        quitButton.getButton().setPosition(600, 150);

        winOrLost = new Font(40);
        winOrLost.setColor(Color.WHITE);

        tokenMessage = new Font(32);
        tokenMessage.setColor(Color.WHITE);
    }

    public void setState(ResultState state) {
        this.state = state;
    }

    public void setRemainingTokens(int tokens) {
        remainingTokens = tokens;
    }

    public boolean isNoTokens() {
        return remainingTokens <= 0;
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        float x = cursorPos.getX();
        float y = cursorPos.getY();

        if (state == ResultState.WIN || state == ResultState.LOOSE){
            if (isNoTokens())
                quitButton.getButton().activate(x, y);
            else
                continueButton.getButton().activate(x, y);
        }

    }

    @Override
    public void touchDragged() {
        if (state == ResultState.WIN || state == ResultState.LOOSE){
            if (isNoTokens())
                quitButton.getButton().deactivate();
            else
                continueButton.getButton().deactivate();
        }
    }

    @Override
    public void touchUp() {
        if (state == ResultState.WIN || state == ResultState.LOOSE){
            if (isNoTokens())
                quitButton.getButton().deactivate();
            else
                continueButton.getButton().deactivate();
        }
    }

    @Override
    public void init() {
        continueButton.init();
        quitButton.init();
    }

    @Override
    public void update() {
        if (isNoTokens())
            quitButton.update();
        else
            continueButton.update();
    }

    public boolean isContinue() {
        return continueButton.isStart();
    }

    public boolean isQuit() {
        return quitButton.isStart();
    }

    @Override
    public void draw() {
        SpriteBatch batch = getBatch();
        String message = "";
        String[] texts = textProvider.getResultScreenTexts();

        if (state == ResultState.WIN)
            message = texts[0];
        if (state == ResultState.LOOSE)
            message = texts[1];

        winOrLost.draw(batch, message, 600, 500);

        tokenMessage.draw(batch, remainingTokens + texts[2], 600, 400);

        if (isNoTokens())
            quitButton.getButton().draw(batch, texts[5]);
        else
            continueButton.getButton().draw(batch, texts[3]);
    }

    @Override
    public void dispose() {
        winOrLost.dispose();
        tokenMessage.dispose();
        continueButton.dispose();
        quitButton.dispose();
    }
}
