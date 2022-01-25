package com.arithfighter.ccg;

import com.arithfighter.ccg.cardgame.GameComponent;
import com.arithfighter.ccg.cardgame.RandomNumListGenerator;
import com.arithfighter.ccg.widget.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashSet;
import java.util.LinkedList;

public class GameCore {
    AssetManager assetManager = new AssetManager();
    FileLibrary fileLibrary = new FileLibrary();
    GameDataDisplacer dataDisplacer;
    GameComponent gameComponent;
    CursorPositionAccessor cursorPos = new CursorPositionAccessor();
    SpriteBatch batch;
    Texture[] textures;
    SumAccessor sumAccessor = new SumAccessor();
    DataAccessor dataAccessor = new DataAccessor();
    LinkedList<Integer> numberList = new LinkedList<>();
    HashSet<Integer> numberSet = new HashSet<>();
    RandomNumListGenerator randomNumListGenerator = new RandomNumListGenerator();
    NumberListInspector numberListInspector = new NumberListInspector();

    InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            gameComponent.getHand().checkActive(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            gameComponent.getHand().updateWhenDrag(cursorPos.getX(), cursorPos.getY());
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            gameComponent.whenPlayCardOnTable(cursorPos.getX(), cursorPos.getY());
            return true;
        }
    };

    public void create() {
        for (String textureFile : fileLibrary.getTextureFile())
            assetManager.load(textureFile, Texture.class);

        assetManager.finishLoading();

        storeTextures();

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(mouseAdapter);

        dataDisplacer = new GameDataDisplacer();

        gameComponent = new GameComponent(textures) {
            @Override
            public void doWhenCardPlayed() {
                dataAccessor.updateRecord();
                sumAccessor.updateSum(gameComponent.getHand().getCardNumber());

                if (gameComponent.getHand().isResetCard())
                    sumAccessor.resetSum();
            }
        };
    }

    private void storeTextures() {
        textures = new Texture[fileLibrary.getTextureFile().length];
        for (int i = 0; i < fileLibrary.getTextureFile().length; i++)
            textures[i] = assetManager.get(fileLibrary.getTextureFile()[i]);
    }

    public void render() {
        assetManager.update(17);

        cursorPos.updateCursorPosition();

        randomNumListGenerator.generateNumbers(numberList, numberSet);

        gameComponent.getNumbers(numberList);

        setNumToZeroWhenMatchedSum();

        numberListInspector.inspectNumberList(numberList);

        if (numberListInspector.isAllNumberAreZero()){
            clearNumListAndSet();
        }

        resetAnyThingsManually();

        workSpriteBatch();
    }

    private void setNumToZeroWhenMatchedSum() {
        for (int i = 0; i < numberSet.size(); i++) {
            if (sumAccessor.getSum() == numberList.get(i)) {
                if (numberList.get(i) > 0) {
                    dataAccessor.updateScore();
                    numberList.set(i, 0);
                }
            }
        }
    }

    private void resetAnyThingsManually() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            clearNumListAndSet();
            resetVariable();
        }
    }

    private void clearNumListAndSet(){
        numberSet.clear();
        numberList.clear();
    }

    private void resetVariable() {
        dataAccessor.resetData();
        sumAccessor.resetSum();
    }

    private void workSpriteBatch() {
        batch.begin();
        drawComponent();
        batch.end();
    }

    private void drawComponent() {
        dataDisplacer.drawMousePos(cursorPos.getX(), cursorPos.getY(), batch);
        dataDisplacer.drawRecord(dataAccessor.getRecord(), batch);
        dataDisplacer.drawScoreBoard(dataAccessor.getScore(), batch);

        gameComponent.drawTableAndNumbers(batch, sumAccessor.getSum());
        gameComponent.drawHand(batch, cursorPos.getX(), cursorPos.getY());
    }

    public void dispose() {
        batch.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
    }
}
