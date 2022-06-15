package com.arithfighter.not.scene.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneComponent;
import com.arithfighter.not.scene.SceneEvent;
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
    enum ResultState {WIN, LOOSE}
    private ResultState state = ResultState.WIN;
    private final TextProvider textProvider;

    public ResultScreen(TextureService textureService, FontService fontService) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        textProvider = new TextProvider();

        Font buttonFont = fontService.getFont22();

        continueButton = new SceneControlButton(textures[6], 2);
        continueButton.getButton().setPosition(600, 150);
        continueButton.getButton().setFont(buttonFont);

        quitButton = new SceneControlButton(textures[6], 2);
        quitButton.getButton().setPosition(600, 150);
        quitButton.getButton().setFont(buttonFont);

        winOrLost = fontService.getFont36();

        tokenMessage = fontService.getFont32();
    }

    public void setWin() {
        state = ResultState.WIN;
    }

    public void setLose(){
        state = ResultState.LOOSE;
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
                quitButton.getButton().on(x, y);
            else
                continueButton.getButton().on(x, y);
        }

    }

    @Override
    public void touchDragged() {
        if (state == ResultState.WIN || state == ResultState.LOOSE){
            if (isNoTokens())
                quitButton.getButton().off();
            else
                continueButton.getButton().off();
        }
    }

    @Override
    public void touchUp() {
        if (state == ResultState.WIN || state == ResultState.LOOSE){
            if (isNoTokens())
                quitButton.getButton().off();
            else
                continueButton.getButton().off();
        }
    }

    @Override
    public void init() {
        continueButton.init();
        quitButton.init();
    }

    private void update() {
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
    public void render() {
        update();

        SpriteBatch batch = getBatch();
        String message = "";
        String[] texts = textProvider.getResultScreenTexts();

        if (state == ResultState.WIN)
            message = texts[0];
        if (state == ResultState.LOOSE)
            message = texts[1];

        winOrLost.setColor(Color.WHITE);
        winOrLost.draw(batch, message, 600, 500);

        tokenMessage.draw(batch, remainingTokens + texts[2], 600, 400);

        if (isNoTokens())
            quitButton.getButton().draw(batch, texts[5]);
        else
            continueButton.getButton().draw(batch, texts[3]);
    }
}
