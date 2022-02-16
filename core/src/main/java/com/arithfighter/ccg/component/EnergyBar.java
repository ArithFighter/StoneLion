package com.arithfighter.ccg.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnergyBar {
    RawBar rawBar;
    BarGrid barGrid;
    private static final int maxEnergy = 30;

    public EnergyBar(Texture[] textures){
        int barX = 40;
        int barY = 20;
        rawBar = new RawBar(textures[4], barX, barY);

        barGrid = new BarGrid(textures[5], barX, barY);
        barGrid.setPos(barX+barGrid.getWidth()+9, barY+barGrid.getHeight()*3);
    }

    public int getMax(){
        return maxEnergy;
    }

    public void draw(SpriteBatch batch, int energy){
        boolean isMax = false;
        if (energy>=maxEnergy){
            energy = maxEnergy;
            isMax = true;
        }
        barGrid.draw(batch, (rawBar.getWidth()-71)* energy /maxEnergy);
        rawBar.draw(batch, isMax);
    }

    public void dispose(){
        rawBar.dispose();
        barGrid.dispose();
    }
}
