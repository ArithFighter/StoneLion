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
    private CounterAssetProcessor assetProcessor;
    private Texture[] textures;
    private GameDataAccessor dataAccessor;
    private CardTable cardTable;
    private NumberBoxDisplacer numberBoxDisplacer;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private MouseAdapter mouseAdapter;
    private Player player;

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
            public void activeSkill() {
                if (getCharacter() == CharacterList.KNIGHT)
                    numberBoxDisplacer.set(0,33);
            }
        };

        cardTable = new CardTable(textures){
            @Override
            public void initCardPosition() {
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard();
            }
        };

        numberBoxDisplacer = new NumberBoxDisplacer(textures[3]) {
            @Override
            public void doWhenSumAndNumMatched() {
                dataAccessor.updateScore(1);
            }
        };

        cursorPos = new CursorPositionAccessor();

        mouseAdapter = new MouseAdapter(cardTable, player);

        Gdx.input.setInputProcessor(mouseAdapter);
    }

    public void render() {
        assetProcessor.update(17);

        cursorPos.update();

        mouseAdapter.updateMousePos(cursorPos.getX(), cursorPos.getY());

        numberBoxDisplacer.update(player.getSum());

        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            dataAccessor.resetRecorder();
        }

        drawComponent();
    }

    private void drawComponent() {
        batch.begin();
        dataAccessor.draw(cursorPos.getX(), cursorPos.getY(), player.getEnergy(),batch);//for dev
        cardTable.draw(batch, player.getSum(), player.getCondition());
        numberBoxDisplacer.draw(batch);
        player.draw(batch, cursorPos.getX(), cursorPos.getY());
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        assetProcessor.dispose();
        dataAccessor.dispose();
        cardTable.dispose();
        numberBoxDisplacer.dispose();
        player.dispose();
    }
}
