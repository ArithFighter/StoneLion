package com.arithfighter.ccg;

import com.arithfighter.ccg.component.NumberBoxDisplacer;
import com.arithfighter.ccg.widget.CardBoard;
import com.arithfighter.ccg.widget.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.arithfighter.ccg.WindowSetting.*;

public class CardTable {
    CardBoard cardBoard;
    SumBox sumBox;
    NumberBoxDisplacer numberBoxDisplacer;

    public CardTable(Texture[] textures){
        cardBoard = new CardBoard(textures[1], CENTER_X + GRID_X * 4, GRID_Y * 6);

        sumBox = new SumBox(textures[2], CENTER_X + GRID_X * 8, GRID_Y * 7);

        numberBoxDisplacer = new NumberBoxDisplacer(textures) {
            @Override
            public void checkNumberTier(int i) {
                int[] numbers = numberBoxDisplacer.getNumbers();
                int tier1 = 10;
                int tier2 = 26;

                if (numbers[i] < tier1)
                    updateScore1();
                if (numbers[i] >= tier1 && numbers[i] < tier2)
                    updateScore2();
                if (numbers[i] >= tier2)
                    updateScore3();
            }
        };
    }

    public void draw(SpriteBatch batch, int sum, int condition){
        cardBoard.draw(batch);

        sumBox.draw(sum, condition, batch);

        numberBoxDisplacer.draw(batch);
    }

    public void update(int sum){
        numberBoxDisplacer.update(sum);
    }

    public boolean isCardOnBoard(float mouseX, float mouseY){
        return cardBoard.isOnBoard(mouseX, mouseY);
    }

    public void updateScore1() {
    }

    public void updateScore2() {
    }

    public void updateScore3() {
    }

    public void dispose(){
        numberBoxDisplacer.dispose();

        sumBox.dispose();
    }
}
