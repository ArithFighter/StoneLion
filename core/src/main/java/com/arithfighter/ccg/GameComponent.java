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

    public void create(Texture[] textures) {
        hand = new Hand(textures[0]);

        table = new Table(textures[1], WindowSetting.GRID_X * 6, WindowSetting.GRID_Y * 6);

        sumDisplacer = new SumDisplacer(textures[2],
                WindowSetting.CENTER_X + WindowSetting.GRID_X * 6, WindowSetting.CENTER_Y);

        numberBox = new NumberBox(textures[3], 300, 350);
    }

    public Hand getHand(){
        return hand;
    }

    public void draw(SpriteBatch batch, String sum, int mouseX, int mouseY) {
        table.draw(batch);

        sumDisplacer.draw(sum, batch);

        hand.draw(batch);
        hand.checkTouchingCard(mouseX, mouseY);

        numberBox.draw(16, batch);
    }

    public void dispose() {
        hand.dispose();
        sumDisplacer.dispose();
        numberBox.dispose();
    }
}
