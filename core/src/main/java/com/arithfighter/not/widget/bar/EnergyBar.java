package com.arithfighter.not.widget.bar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnergyBar {
    private final EmptyBar emptyBar;
    private final BarGrid barGrid;
    private static final int maxEnergy = 30;
    private int energy;

    public EnergyBar(Texture[] textures){
        int barX = 40;
        int barY = 20;

        emptyBar = new EmptyBar(textures[4]);
        emptyBar.setPosition(barX, barY);

        barGrid = new BarGrid(textures[5]);
        barGrid.setPosition(barX+barGrid.getWidth()+9, barY+barGrid.getHeight()*3);
    }

    public int getMax(){
        return maxEnergy;
    }

    public void setEnergy(int value){
        energy = Math.min(value, maxEnergy);
    }

    public void draw(SpriteBatch batch){
        barGrid.draw(batch);
        emptyBar.draw(batch);

        barGrid.updateWidth((emptyBar.getWidth()-71)* energy / maxEnergy);
    }

    public boolean isNotFull(){
        return energy<maxEnergy;
    }
}
