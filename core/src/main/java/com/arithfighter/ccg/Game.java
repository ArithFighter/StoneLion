package com.arithfighter.ccg;

import com.arithfighter.ccg.component.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
    private final GameDataAccessor dataAccessor;
    private final CardTable cardTable;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private final Player player;

    public Game(Texture[] textures, Texture[] cards){
        dataAccessor = new GameDataAccessor();

        player = new Player(textures, cards, CharacterList.ROGUE) {
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
    }

    public Player getPlayer() {
        return player;
    }

    public CardTable getCardTable(){
        return cardTable;
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
                        numberBoxDisplacer.set(
                                i,
                                numberBoxDisplacer.getNumberList().get(i)-1);
                    }
                break;
        }
    }

    public void update(){
        numberBoxDisplacer.update(player.getSum());

        //This is for test, will remove in future version
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            dataAccessor.resetRecorder();
        }
    }

    public void draw(SpriteBatch batch, int x, int y) {
        dataAccessor.draw(x, y, player.getEnergy(), batch);//for dev

        cardTable.draw(batch, player.getSum(), player.getCondition());

        numberBoxDisplacer.draw(batch);

        player.draw(batch, x, y);
    }

    public void dispose(){
        dataAccessor.dispose();
        cardTable.dispose();
        numberBoxDisplacer.dispose();
        player.dispose();
    }
}
