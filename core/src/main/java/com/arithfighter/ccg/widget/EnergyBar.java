package com.arithfighter.ccg.widget;

import com.arithfighter.ccg.font.Font;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnergyBar {
    private final EmptyBar emptyBar;
    private final BarGrid barGrid;
    private final Font maxSign;
    private static final int maxEnergy = 30;

    public EnergyBar(Texture[] textures){
        int barX = 40;
        int barY = 20;

        emptyBar = new EmptyBar(textures[4]);
        emptyBar.setPosition(barX, barY);

        barGrid = new BarGrid(textures[5]);
        barGrid.setPosition(barX+barGrid.getWidth()+9, barY+barGrid.getHeight()*3);

        maxSign = new Font(32);
    }

    public int getMax(){
        return maxEnergy;
    }

    private void update(int energy){
        barGrid.updateWidth((emptyBar.getWidth()-71)* energy / maxEnergy);
    }

    public void draw(SpriteBatch batch, int energy){
        if (energy>=maxEnergy)
            energy = maxEnergy;
        barGrid.draw(batch);
        emptyBar.draw(batch);
        update(energy);
        drawMaxSign(energy, batch);
    }

    private void drawMaxSign(int energy, SpriteBatch batch) {
        String content = "MAX";
        if (energy >= maxEnergy){
            maxSign.draw(
                    batch,content,
                    emptyBar.getWidth()/2,
                    emptyBar.getHeight()/2
            );
        }
    }

    public void dispose(){
        maxSign.dispose();
    }
}
