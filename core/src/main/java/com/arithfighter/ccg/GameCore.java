package com.arithfighter.ccg;

import com.arithfighter.ccg.system.*;
import com.arithfighter.ccg.component.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCore {
    CounterAssetProcessor assetProcessor;
    Texture[] textures;
    GameDataDisplacer dataDisplacer;
    GameComponent gameComponent;
    CursorPositionAccessor cursorPos;
    SpriteBatch batch;
    SumAccessor sumAccessor = new SumAccessor();
    Recorder playRecorder = new Recorder();
    Recorder scoreRecorder = new Recorder();
    MouseAdapter mouseAdapter;
    int initResetCondition = 6;
    int autoResetCondition = initResetCondition;

    public void create() {
        assetProcessor = new CounterAssetProcessor();

        assetProcessor.loadAssets();

        assetProcessor.finishLoading();

        textures = assetProcessor.getTextures();

        batch = new SpriteBatch();

        dataDisplacer = new GameDataDisplacer();

        gameComponent = new GameComponent(textures) {
            @Override
            public void doWhenCardPlayed() {
                updateWhenPlayCard();

                if (gameComponent.getHand().isResetCard())
                    doWhenResetCardPlay();
            }

            @Override
            public void updateScore() {
                scoreRecorder.update();
            }
        };
        cursorPos = new CursorPositionAccessor();

        mouseAdapter = new MouseAdapter(gameComponent);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    private void updateWhenPlayCard() {
        playRecorder.update();
        sumAccessor.updateSum(gameComponent.getHand().getCardNumber());
        autoResetCondition--;
    }

    private void doWhenResetCardPlay() {
        sumAccessor.resetSum();
        autoResetCondition = initResetCondition;
    }

    public void render() {
        assetProcessor.update(17);

        cursorPos.updateCursorPosition();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        gameComponent.updateNumbers();

        gameComponent.handleWhenNumMatchedSum(sumAccessor.getSum());

        gameComponent.checkEveryNumMatched();

        resetAnyThingsManually();//for test

        workSpriteBatch();

        checkAutoResetCondition();
    }

    private void checkAutoResetCondition() {
        if (autoResetCondition == 0) {
            sumAccessor.resetSum();
            autoResetCondition = initResetCondition;
        }
    }

    private void resetAnyThingsManually() {
        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            resetVariable();
        }
    }

    private void resetVariable() {
        playRecorder.reset();
        scoreRecorder.reset();
        sumAccessor.resetSum();
    }

    private void workSpriteBatch() {
        batch.begin();
        drawComponent();
        batch.end();
    }

    private void drawComponent() {
        drawData();

        drawGame();
    }

    private void drawData() {
        dataDisplacer.drawMousePos(cursorPos.getX(), cursorPos.getY(), batch);
        dataDisplacer.drawRecord(playRecorder.getRecord(), batch);
        dataDisplacer.drawScore(scoreRecorder.getRecord(), batch);
    }

    private void drawGame() {
        gameComponent.drawTableAndNumbers(batch, sumAccessor.getSum());
        gameComponent.drawHand(batch, cursorPos.getX(), cursorPos.getY());
    }

    public void dispose() {
        batch.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
    }
}
