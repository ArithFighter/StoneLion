package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnergyBar {
    RawBar rawBar;
    BarGrid barGrid;

    public EnergyBar(Texture[] textures){
        int barX = 50;
        rawBar = new RawBar(textures[4], barX, 30);

        barGrid = new BarGrid(textures[5], barX, 150);
        barGrid.setPosX(barX+barGrid.getWidth()+9);
    }

    public void draw(SpriteBatch batch){
        barGrid.draw(batch, rawBar.getWidth()-71);
        rawBar.draw(batch);
    }

    public void dispose(){
        barGrid.dispose();
    }
}
