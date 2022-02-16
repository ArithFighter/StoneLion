package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnergyBar {
    RawBar rawBar;
    BarGrid barGrid;

    public EnergyBar(Texture[] textures){
        rawBar = new RawBar(textures[4], 50, 30);

        barGrid = new BarGrid(textures[5], 50, 30);
    }

    public void draw(SpriteBatch batch){
        barGrid.draw(batch);
        rawBar.draw(batch);
    }

    public void dispose(){
        barGrid.dispose();
    }
}
