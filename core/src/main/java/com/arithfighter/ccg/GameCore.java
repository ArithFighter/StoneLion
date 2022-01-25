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
    int mouseX, mouseY;
    SpriteBatch batch;
    Texture[] textures;
    int record = 0;
    SumAccessor sumAccessor = new SumAccessor();
    int score = 0;
    LinkedList<Integer> numberList = new LinkedList<>();
    HashSet<Integer> numberSet = new HashSet<>();
    RandomNumListGenerator randomNumListGenerator = new RandomNumListGenerator();
    NumberListInspector numberListInspector = new NumberListInspector();

    InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            gameComponent.getHand().checkActive(mouseX, mouseY);
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            gameComponent.getHand().updateWhenDrag(mouseX, mouseY);
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            gameComponent.whenPlayCardOnTable(mouseX, mouseY);
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
                record++;
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

        updateMousePosition();

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

    private void updateMousePosition() {
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    private void setNumToZeroWhenMatchedSum() {
        for (int i = 0; i < numberSet.size(); i++) {
            if (sumAccessor.getSum() == numberList.get(i)) {
                if (numberList.get(i) > 0) {
                    score++;
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
        record -= record;
        sumAccessor.resetSum();
        score -= score;
    }

    private void workSpriteBatch() {
        batch.begin();
        drawComponent();
        batch.end();
    }

    private void drawComponent() {
        dataDisplacer.drawMousePos(mouseX, mouseY, batch);
        dataDisplacer.drawRecord(record, batch);
        dataDisplacer.drawScoreBoard(score, batch);

        gameComponent.drawTableAndNumbers(batch, sumAccessor.getSum());
        gameComponent.drawHand(batch, mouseX, mouseY);
    }

    public void dispose() {
        batch.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
    }
}
