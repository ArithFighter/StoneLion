package com.arithfighter.ccg;

import com.arithfighter.ccg.system.CursorPositionAccessor;
import com.arithfighter.ccg.file.CounterAssetProcessor;
import com.arithfighter.ccg.system.*;
import com.arithfighter.ccg.component.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCore {
    CounterAssetProcessor assetProcessor;
    Texture[] textures;
    GameDataAccessor dataAccessor;
    GameComponent gameComponent;
    CursorPositionAccessor cursorPos;
    SpriteBatch batch;
    MouseAdapter mouseAdapter;
    Player player;

    public void create() {
        assetProcessor = new CounterAssetProcessor();

        assetProcessor.load();

        textures = assetProcessor.getTextures();

        batch = new SpriteBatch();

        dataAccessor = new GameDataAccessor();

        player = new Player(textures, assetProcessor.getCards(), CharacterList.KNIGHT){
            @Override
            public void doWhenCardPlayed() {
                dataAccessor.updatePlayTimes();
            }

            @Override
            public void updateEnergy() {
                dataAccessor.updateEnergy(3);
            }

            @Override
            public void activeSkill() {
                dataAccessor.consumeEnergy();
                if (getCharacter() == CharacterList.KNIGHT)
                    gameComponent.set(0,33);
            }
        };

        gameComponent = new GameComponent(textures) {
            @Override
            public void initComponent() {
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard(dataAccessor.getEnergy());
            }

            @Override
            public void getScore() {
                dataAccessor.updateScore(1);
            }
        };

        cursorPos = new CursorPositionAccessor();

        mouseAdapter = new MouseAdapter(gameComponent, player);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    public void render() {
        assetProcessor.update(17);

        cursorPos.update();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        gameComponent.update(player.getSum());

        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            dataAccessor.resetRecorder();
        }

        drawComponent();
    }

    private void drawComponent() {
        batch.begin();
        dataAccessor.draw(cursorPos.getX(), cursorPos.getY(), batch);//for dev
        gameComponent.draw(batch, player.getSum(), player.getCondition());
        player.draw(batch, cursorPos.getX(), cursorPos.getY(), dataAccessor.getEnergy());
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        assetProcessor.dispose();
        dataAccessor.dispose();
        gameComponent.dispose();
        player.dispose();
    }
}
