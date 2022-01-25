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
    int sum = 0;
    LinkedList<Integer> numberList = new LinkedList<>();
    HashSet<Integer> numberSet = new HashSet<>();
    RandomNumListGenerator randomNumListGenerator = new RandomNumListGenerator();

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
                sum += gameComponent.getHand().getCardNumber();

                if (gameComponent.getHand().isResetCard())
                    sum -= sum;
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

        randomNumListGenerator.generateNumbers(numberList,numberSet);

        gameComponent.getNumbers(numberList);

        setNumToZeroWhenMatchedSum();

        checkClearNumbers();

        workSpriteBatch();
    }

    private void updateMousePosition() {
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    private void setNumToZeroWhenMatchedSum(){
        for (int i = 0; i< numberSet.size();i++){
            if (sum == numberList.get(i))
                numberList.set(i,0);
        }
    }

    private void checkClearNumbers(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)){
            numberSet.clear();
            numberList.clear();
            record-=record;
        }
    }

    private void workSpriteBatch(){
        batch.begin();
        drawComponent();
        batch.end();
    }

    private void drawComponent() {
        dataDisplacer.drawMousePos(mouseX, mouseY, batch);
        dataDisplacer.drawRecord(record, batch);

        gameComponent.drawTableAndNumbers(batch, sum);
        gameComponent.drawHand(batch, mouseX, mouseY);
    }

    public void dispose() {
        batch.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
    }
}
