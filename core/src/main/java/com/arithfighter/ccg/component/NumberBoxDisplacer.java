package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;

import static com.arithfighter.ccg.WindowSetting.GRID_X;
import static com.arithfighter.ccg.WindowSetting.GRID_Y;

public class NumberBoxDisplacer{
    NumberBox numberBox;
    NumberBox[] numberBoxes;
    NumberBoxPlacer numberBoxPlacer;
    private final int numberBoxQuantity = 9;

    public NumberBoxDisplacer(Texture[] textures){
        numberBoxPlacer = new NumberBoxPlacer(GRID_X*9.5f,GRID_Y*5, GRID_X);

        numberBox = new NumberBox(textures[3], 300, 350);

        numberBoxes = new NumberBox[numberBoxQuantity];

        for (int i = 0; i< numberBoxQuantity; i++){
            numberBoxes[i] = new NumberBox(textures[3],
                    numberBoxPlacer.getNumberBoxX(i, numberBox.getWidth()),
                    numberBoxPlacer.getNumberBoxY(i, numberBox.getHeight()));
        }
    }

    public int getNumberBoxQuantity(){
        return numberBoxQuantity;
    }

    public NumberBox[] getNumberBoxes(){
        return numberBoxes;
    }

    public void dispose(){
        numberBox.dispose();

        for (NumberBox numberBox:numberBoxes)
            numberBox.dispose();
    }
}
