package com.arithfighter.ccg;

import com.arithfighter.ccg.accessor.CursorPositionAccessor;
import com.arithfighter.ccg.file.CounterAssetProcessor;
import com.arithfighter.ccg.system.*;
import com.arithfighter.ccg.component.*;
import com.arithfighter.ccg.widget.EnergyBar;
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
    EnergyBar energyBar;

    public void create() {
        assetProcessor = new CounterAssetProcessor();

        assetProcessor.load();

        textures = assetProcessor.getTextures();

        batch = new SpriteBatch();

        dataDisplacer = new GameDataDisplacer();

        gameComponent = new GameComponent(textures, assetProcessor.getCards(), CharacterList.KNIGHT) {
            @Override
            public void doWhenCardPlayed() {
                dataDisplacer.updatePlayTimes();
                dataDisplacer.updateEnergy(3);
            }

            @Override
            public void activeSkill() {
                if (dataDisplacer.getEnergy() == energyBar.getMax()){
                    dataDisplacer.consumeEnergy();
                }
            }
        };
        cursorPos = new CursorPositionAccessor();

        mouseAdapter = new MouseAdapter(gameComponent);

        Gdx.input.setInputProcessor(mouseAdapter);

        energyBar = new EnergyBar(textures);
    }

    public void render() {
        updateThings();

        resetRecordManually();//for test

        workSpriteBatch();

        if (dataDisplacer.getEnergy()>energyBar.getMax())
            dataDisplacer.setMaxEnergy(energyBar.getMax());
    }

    private void updateThings() {
        assetProcessor.update(17);

        cursorPos.updateCursorPosition();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        gameComponent.update();
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

    private void workSpriteBatch() {
        batch.begin();
        drawComponent();
        batch.end();
    }

    private void drawComponent() {
        dataDisplacer.draw(cursorPos.getX(), cursorPos.getY(), batch);//for dev

        gameComponent.draw(batch, cursorPos.getX(), cursorPos.getY());

        energyBar.draw(batch, dataDisplacer.getEnergy());
    }

    public void dispose() {
        batch.dispose();
        assetProcessor.dispose();
        dataDisplacer.dispose();
        gameComponent.dispose();
        energyBar.dispose();
    }
}
