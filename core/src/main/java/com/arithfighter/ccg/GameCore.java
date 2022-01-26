package com.arithfighter.ccg;

import com.arithfighter.ccg.cardgame.GameComponent;
import com.arithfighter.ccg.cardgame.RandomNumArrayGenerator;
import com.arithfighter.ccg.widget.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCore {
    AssetManager assetManager = new AssetManager();
    FileLibrary fileLibrary = new FileLibrary();
    GameDataDisplacer dataDisplacer;
    GameComponent gameComponent;
    CursorPositionAccessor cursorPos = new CursorPositionAccessor();
    SpriteBatch batch;
    Texture[] textures;
    SumAccessor sumAccessor = new SumAccessor();
    Recorder playRecorder = new Recorder();
    Recorder scoreRecorder = new Recorder();
    RandomNumArrayGenerator randomNumArrayGenerator = new RandomNumArrayGenerator();
    int[] numberList = new int[randomNumArrayGenerator.getMaxQuantity()];
    NumberListInspector numberListInspector = new NumberListInspector();
    MouseAdapter mouseAdapter;

    public void create() {
        for (String textureFile : fileLibrary.getTextureFile())
            assetManager.load(textureFile, Texture.class);

        assetManager.finishLoading();

        storeTextures();

        batch = new SpriteBatch();

        dataDisplacer = new GameDataDisplacer();

        gameComponent = new GameComponent(textures) {
            @Override
            public void doWhenCardPlayed() {
                playRecorder.update();
                sumAccessor.updateSum(gameComponent.getHand().getCardNumber());

                if (gameComponent.getHand().isResetCard())
                    sumAccessor.resetSum();
            }
        };
        mouseAdapter = new MouseAdapter(gameComponent);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    private void storeTextures() {
        textures = new Texture[fileLibrary.getTextureFile().length];

        for (int i = 0; i < fileLibrary.getTextureFile().length; i++)
            textures[i] = assetManager.get(fileLibrary.getTextureFile()[i]);
    }

    public void render() {
        assetManager.update(17);

        cursorPos.updateCursorPosition();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        numberList = randomNumArrayGenerator.generateNumbers();

        gameComponent.getNumbers(numberList);

        handleWhenNumMatchedSum();

        checkEveryNumMatched();

        resetAnyThingsManually();

        workSpriteBatch();
    }

    private void checkEveryNumMatched(){
        numberListInspector.inspectNumberList(numberList);

        if (numberListInspector.isAllNumberAreZero()){
            randomNumArrayGenerator.clear();
        }
    }

    private void handleWhenNumMatchedSum() {
        for (int i = 0; i < randomNumArrayGenerator.getMaxQuantity(); i++) {
            if (isNumMatched(i)) {
                if (numberList[i] > 0) {
                    scoreRecorder.update();
                    randomNumArrayGenerator.setNumberInListToZero(i);
                }
            }
        }
    }

    private boolean isNumMatched(int index){
        return sumAccessor.getSum() == numberList[index];
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
        dataDisplacer.drawMousePos(cursorPos.getX(), cursorPos.getY(), batch);
        dataDisplacer.drawRecord(playRecorder.getRecord(), batch);
        dataDisplacer.drawScoreBoard(scoreRecorder.getRecord(), batch);

        gameComponent.drawTableAndNumbers(batch, sumAccessor.getSum());
        gameComponent.drawHand(batch, cursorPos.getX(), cursorPos.getY());
    }

    public void dispose() {
        batch.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
    }
}
