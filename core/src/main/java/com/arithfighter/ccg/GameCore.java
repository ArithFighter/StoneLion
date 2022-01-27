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
    RandomNumArrayGenerator randomNumArrayGenerator;
    NumberListInspector numberListInspector;
    MouseAdapter mouseAdapter;

    public void create() {
        assetProcessor = new CounterAssetProcessor();

        assetProcessor.loadAssets();

        assetProcessor.finishLoading();

        textures = assetProcessor.getTextures();

        batch = new SpriteBatch();

        dataDisplacer = new GameDataDisplacer();

        randomNumArrayGenerator = new RandomNumArrayGenerator();

        gameComponent = new GameComponent(textures) {
            @Override
            public void doWhenCardPlayed() {
                playRecorder.update();
                sumAccessor.updateSum(gameComponent.getHand().getCardNumber());

                if (gameComponent.getHand().isResetCard())
                    sumAccessor.resetSum();
            }
        };
        cursorPos = new CursorPositionAccessor();

        mouseAdapter = new MouseAdapter(gameComponent);

        Gdx.input.setInputProcessor(mouseAdapter);

        numberListInspector = new NumberListInspector();

    }

    public void render() {
        assetProcessor.update(17);

        cursorPos.updateCursorPosition();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        gameComponent.getNumbers(randomNumArrayGenerator.getNumbers());

        handleWhenNumMatchedSum();

        checkEveryNumMatched();

        resetAnyThingsManually();

        workSpriteBatch();
    }

    private void checkEveryNumMatched(){
        numberListInspector.inspectNumberList(randomNumArrayGenerator.getNumbers());

        if (numberListInspector.isAllNumberAreZero()){
            randomNumArrayGenerator.clear();
        }
    }

    private void handleWhenNumMatchedSum() {
        for (int i = 0; i < randomNumArrayGenerator.getMaxQuantity(); i++) {
            if (isNumAndSumMatched(i)) {
                if (randomNumArrayGenerator.getNumbers()[i] > 0) {
                    scoreRecorder.update();
                    randomNumArrayGenerator.setNumberInListToZero(i);
                }
            }
        }
    }

    private boolean isNumAndSumMatched(int index){
        return sumAccessor.getSum() == randomNumArrayGenerator.getNumbers()[index];
    }

    private void resetAnyThingsManually() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            randomNumArrayGenerator.clear();
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

    private void drawData(){
        dataDisplacer.drawMousePos(cursorPos.getX(), cursorPos.getY(), batch);
        dataDisplacer.drawRecord(playRecorder.getRecord(), batch);
        dataDisplacer.drawScoreBoard(scoreRecorder.getRecord(), batch);
    }

    private void drawGame(){
        gameComponent.drawTableAndNumbers(batch, sumAccessor.getSum());
        gameComponent.drawHand(batch, cursorPos.getX(), cursorPos.getY());
    }

    public void dispose() {
        batch.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
    }
}
