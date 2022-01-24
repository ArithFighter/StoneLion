package com.arithfighter.ccg;

import com.arithfighter.ccg.widget.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCore {
    AssetManager assetManager = new AssetManager();
    FileLibrary fileLibrary = new FileLibrary();
    GameDataDisplacer dataDisplacer;
    GameComponent gameComponent;
    int mouseX, mouseY;
    SpriteBatch batch;
    Texture[] textures;
    int cardOnDesk = 0;
    int sum = 0;
    int number = 16;

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
                cardOnDesk++;
                sum += hand.getCardNumber();

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

        batch.begin();
        drawComponent();
        batch.end();
    }

    private void updateMousePosition() {
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    private void drawComponent() {
        dataDisplacer.drawMousePos(mouseX, mouseY, batch);
        dataDisplacer.drawRecord(cardOnDesk, batch);

        gameComponent.draw(batch, sum, number, mouseX, mouseY);
    }

    public void dispose() {
        batch.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
    }
}
