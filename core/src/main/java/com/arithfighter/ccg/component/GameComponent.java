package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameComponent {
    private final CardTable cardTable;
    private final NumberBoxDisplacer numberBoxDisplacer;

    public GameComponent(Texture[] textures) {
        cardTable = new CardTable(textures);

        numberBoxDisplacer = new NumberBoxDisplacer(textures[3]) {
            @Override
            public void doWhenSumAndNumMatched() {
                getScore();
            }
        };
    }

    public void getScore(){
    }

    public void draw(SpriteBatch batch, int sum, int condition) {
        cardTable.draw(batch, sum, condition);

        numberBoxDisplacer.draw(batch);
    }

    public void update(int sum) {
        numberBoxDisplacer.update(sum);//this always on top of stack
    }

    public final void playCardOnTable(int mouseX, int mouseY) {
        if (cardTable.isCardOnBoard(mouseX, mouseY)) {
            checkCardPlayed();
        }
        initComponent();
    }

    public void initComponent() {

    }

    public void checkCardPlayed() {

    }

    public void dispose() {
        cardTable.dispose();
        numberBoxDisplacer.dispose();
    }
}
