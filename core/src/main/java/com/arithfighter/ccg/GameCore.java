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
    Hand hand;
    Desk desk;
    GameDataDisplacer dataDisplacer;
    SumDisplacer sumDisplacer;
    NumberBox numberBox;
    int mouseX, mouseY;
    SpriteBatch batch;
    Texture[] textures;
    int cardOnDesk = 0;
    int sum = 0;

    InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            hand.checkActive(mouseX, mouseY);
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            hand.updateWhenDrag(mouseX, mouseY);
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (desk.isOnDesk(mouseX, mouseY))
                if (hand.isCardActive())
                    doWhenCardPlayed();

            hand.resetHand();
            return true;
        }
    };

    private void doWhenCardPlayed(){
        cardOnDesk++;
        sum+=hand.getCardNumber();

        checkResetCardIsPlayed();
    }

    private void checkResetCardIsPlayed(){
        if (hand.isResetCard())
            sum-=sum;
    }

    public void create() {
        for (String textureFile : fileLibrary.getTextureFile())
            assetManager.load(textureFile, Texture.class);

        assetManager.finishLoading();

        storeTextures();

        hand = new Hand(textures[0]);

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(mouseAdapter);

        desk = new Desk(textures[1], 18);

        dataDisplacer = new GameDataDisplacer();

        sumDisplacer = new SumDisplacer(textures[2],
                WindowSetting.CENTER_X+WindowSetting.GRID_X*6, WindowSetting.CENTER_Y);

        numberBox = new NumberBox(textures[3], 300, 350);
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
        desk.draw(batch);

        sumDisplacer.draw(String.valueOf(sum), batch);

        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);

        dataDisplacer.drawMousePos(mouseX, mouseY, batch);
        dataDisplacer.drawRecord(cardOnDesk, batch);

        numberBox.draw(16, batch);
    }

    public void dispose() {
        hand.dispose();
        batch.dispose();
        dataDisplacer.dispose();
        sumDisplacer.dispose();
        numberBox.dispose();
    }
}
