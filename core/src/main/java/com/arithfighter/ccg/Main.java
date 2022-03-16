package com.arithfighter.ccg;

import com.arithfighter.ccg.component.*;
import com.arithfighter.ccg.file.CounterAssetProcessor;
import com.arithfighter.ccg.system.CursorPositionAccessor;
import com.arithfighter.ccg.system.MouseAdapter;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private CounterAssetProcessor assetProcessor;
    private Texture[] textures;
    private GameDataAccessor dataAccessor;
    private CardTable cardTable;
    private NumberBoxDisplacer numberBoxDisplacer;
    private CursorPositionAccessor cursorPos;
    private SpriteBatch batch;
    private MouseAdapter mouseAdapter;
    private Player player;

    @Override
    public void create() {
        assetProcessor = new CounterAssetProcessor();

        assetProcessor.load();

        textures = assetProcessor.getTextures();

        batch = new SpriteBatch();

        dataAccessor = new GameDataAccessor();

        player = new Player(textures, assetProcessor.getCards(), CharacterList.ROGUE) {
            @Override
            public void doWhenCardPlayed() {
                dataAccessor.updatePlayTimes();
            }

            @Override
            public void castSkill(CharacterList character) {
                castCharacterSkill(character);
            }
        };

        cardTable = new CardTable(textures) {
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

    private void castCharacterSkill(CharacterList character) {
        switch (character) {
            case KNIGHT:
                //change one value of numberBox
                numberBoxDisplacer.set(0, 33);
                break;
            case ROGUE:
                //reduce all values by 1
                for(int i = 0; i<= numberBoxDisplacer.getNumberBoxQuantity();i++)
                    if (numberBoxDisplacer.getNumberList().get(i)>0){
                        numberBoxDisplacer.set(i, numberBoxDisplacer.getNumberList().get(i)-1);
                    }
                break;
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        dataAccessor.draw(cursorPos.getX(), cursorPos.getY(), player.getEnergy(), batch);//for dev

        cardTable.draw(batch, player.getSum(), player.getCondition());

        numberBoxDisplacer.draw(batch);

        player.draw(batch, cursorPos.getX(), cursorPos.getY());

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetProcessor.dispose();
        dataAccessor.dispose();
        cardTable.dispose();
        numberBoxDisplacer.dispose();
        player.dispose();
    }
}