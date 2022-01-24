package com.arithfighter.ccg;

import com.arithfighter.ccg.widget.Hand;
import com.arithfighter.ccg.widget.NumberBox;
import com.arithfighter.ccg.widget.SumDisplacer;
import com.arithfighter.ccg.widget.Table;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameComponent {
    Hand hand;
    Table table;
    SumDisplacer sumDisplacer;
    NumberBox numberBox;

    public GameComponent(Texture[] textures) {
        hand = new Hand(textures[0]);

        table = new Table(textures[1], WindowSetting.GRID_X * 6, WindowSetting.GRID_Y * 6);

        sumDisplacer = new SumDisplacer(textures[2],
                WindowSetting.CENTER_X + WindowSetting.GRID_X * 6, WindowSetting.CENTER_Y);

        numberBox = new NumberBox(textures[3], 300, 350);
    }

    public Hand getHand() {
        return hand;
    }

    public final void whenPlayCardOnTable(int mouseX, int mouseY){
        if (table.isOnDesk(mouseX, mouseY)){
            if (hand.isCardActive())
                doWhenCardPlayed();
        }
        hand.resetHand();
    }

    public void doWhenCardPlayed() {}

    public void draw(SpriteBatch batch, int sum, int number, int mouseX, int mouseY) {
        table.draw(batch);

        sumDisplacer.draw(sum, batch);

        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);

        numberBox.draw(number, batch);
    }

    public void dispose() {
        hand.dispose();
        sumDisplacer.dispose();
        numberBox.dispose();
    }
}
