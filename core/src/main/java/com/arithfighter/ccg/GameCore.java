package com.arithfighter.ccg;

import com.arithfighter.ccg.accessor.CursorPositionAccessor;
import com.arithfighter.ccg.character.CharacterList;
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
    GameDataDisplacer dataDisplacer;
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

        dataDisplacer = new GameDataDisplacer();

        player = new Player(textures, assetProcessor.getCards(), CharacterList.KNIGHT){
            @Override
            public void doWhenCardPlayed() {
                dataDisplacer.updatePlayTimes();
                if (player.isEnergyNotMax(dataDisplacer.getEnergy()))
                    dataDisplacer.updateEnergy(3);
            }

            @Override
            public void activeSkill() {
                dataDisplacer.consumeEnergy();
            }
        };

        gameComponent = new GameComponent(textures) {
            @Override
            public void initComponent() {
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard(dataDisplacer.getEnergy());
            }

            @Override
            public void getScore() {
                dataDisplacer.updateScore(1);
            }
        };

        cursorPos = new CursorPositionAccessor();

        mouseAdapter = new MouseAdapter(gameComponent, player);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    public void render() {
        assetProcessor.update(17);

        cursorPos.updateCursorPosition();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        gameComponent.update(player.getSum());

        resetRecordManually();//for test

        drawComponent();
    }

    private void resetRecordManually() {
        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            resetVariable();
        }
    }

    private void resetVariable() {
        dataDisplacer.resetRecorder();
    }

    private void drawComponent() {
        batch.begin();
        dataDisplacer.draw(cursorPos.getX(), cursorPos.getY(), batch);//for dev
        gameComponent.draw(batch, player.getSum(), player.getCondition());
        player.draw(batch, cursorPos.getX(), cursorPos.getY(), dataDisplacer.getEnergy());
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        assetProcessor.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
        player.dispose();
    }
}
