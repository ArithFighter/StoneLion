package com.arithfighter.ccg;

import com.arithfighter.ccg.accessor.CardTexturesAccessor;
import com.arithfighter.ccg.accessor.CursorPositionAccessor;
import com.arithfighter.ccg.accessor.SumAccessor;
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
    MouseAdapter mouseAdapter;
    AutoResetHandler autoResetHandler;
    CardTexturesAccessor cardTexturesAccessor;

    public void create() {
        assetProcessor = new CounterAssetProcessor();

        assetProcessor.loadTextures();

        assetProcessor.loadCards();

        assetProcessor.finishLoading();

        textures = assetProcessor.getTextures();

        cardTexturesAccessor = new CardTexturesAccessor(assetProcessor.getCards());

        batch = new SpriteBatch();

        dataDisplacer = new GameDataDisplacer();

        gameComponent = new GameComponent(textures, cardTexturesAccessor.getKnightCardSet()) {
            @Override
            public void doWhenCardPlayed() {
                updateWhenPlayCard();
            }

            private void updateWhenPlayCard() {
                dataDisplacer.updatePlayTimes();
                sumAccessor.updateSum(gameComponent.getHand().getCardNumber());
                autoResetHandler.update();
            }

            @Override
            public void doWhenResetCardPlay() {
                sumAccessor.resetSum();
                sumAccessor.updateSum(gameComponent.getHand().getCardNumber());
            }

            @Override
            public void updateScore() {
                dataDisplacer.updateScore();
            }
        };
        cursorPos = new CursorPositionAccessor();

        mouseAdapter = new MouseAdapter(gameComponent);

        Gdx.input.setInputProcessor(mouseAdapter);

        autoResetHandler = new AutoResetHandler();
    }

    public void render() {
        updateThings();

        resetAnyThingsManually();//for test

        workSpriteBatch();

        checkAutoResetCondition();
    }

    private void updateThings() {
        assetProcessor.update(17);

        cursorPos.updateCursorPosition();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        gameComponent.update(sumAccessor.getSum());
    }

    private void checkAutoResetCondition() {
        if (autoResetHandler.isTimeToReset()) {
            sumAccessor.resetSum();
            autoResetHandler.initialize();
        }
    }

    private void resetAnyThingsManually() {
        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            resetVariable();
        }
    }

    private void resetVariable() {
        dataDisplacer.resetRecorder();
        sumAccessor.resetSum();
    }

    private void workSpriteBatch() {
        batch.begin();
        drawComponent();
        batch.end();
    }

    private void drawComponent() {
        dataDisplacer.draw(cursorPos.getX(), cursorPos.getY(), batch);

        gameComponent.draw(batch, sumAccessor.getSum(),
                autoResetHandler.getCondition(), cursorPos.getX(), cursorPos.getY());
    }

    public void dispose() {
        batch.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
    }
}
