package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnergyBar {
    EmptyBar emptyBar;
    BarGrid barGrid;

    public EnergyBar(Texture[] textures){
        int barX = 40;
        int barY = 20;

        emptyBar = new EmptyBar(textures[4]);
        emptyBar.setPosition(barX, barY);

        barGrid = new BarGrid(textures[5]);
        barGrid.setPosition(barX+barGrid.getWidth()+9, barY+barGrid.getHeight()*3);
    }

    public int getMax(){
        return emptyBar.getMax();
    }

    private void update(int energy){
        barGrid.updateWidth((emptyBar.getWidth()-71)* energy / emptyBar.getMax());
    }

    public void draw(SpriteBatch batch, int energy){
        barGrid.draw(batch);
        emptyBar.draw(batch, energy);
        update(energy);
    }

    public void dispose(){
        emptyBar.dispose();
    }
}
